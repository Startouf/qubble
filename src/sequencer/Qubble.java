package sequencer;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ScheduledFuture;

import org.lwjgl.Sys;
import org.lwjgl.util.Point;

import calibration.Calibrate;
import camera.CameraInterface;
import camera.FakeCamera;
import opengl.BaseRoutines;
import opengl.OutputImageInterface;
import opengl.ProjectorOutput;
import audio.Player;
import audio.PlayerInterface;
import audio.SampleController;
import audio.SampleControllerInterface;
import database.Data;
import database.InitialiseProject;
import qubject.MediaInterface;
import qubject.QRInterface;
import qubject.Qubject;
/**
 * 
 * @author duchon
 * Should share the thread of the GUI
 *
 */
public class Qubble implements QubbleInterface {
	/*
	 * Constantes de projection
	 * (Pour les variables de calibration, utiliser les variables decalibration.Calibrate)
	 */
	public static final int TABLE_LENGTH = 1200;
	public static final int TABLE_HEIGHT = 600;
	public static final int TABLE_OFFSET_X = 50; 
	public static final int TABLE_OFFSET_Y=50;
	public static final float GRID_COLUMNS_PER_SEC = 1f;
	public static final float GRID_ROWS_PER_SEC = 10f;
	public static final float TEST_PERIOD_SEC = 30f;
	public static final float SPACING_X = (float)TABLE_LENGTH/TEST_PERIOD_SEC/GRID_COLUMNS_PER_SEC;
	public static final float SPACING_Y = (float)TABLE_HEIGHT/GRID_ROWS_PER_SEC;
	
	//TODO : compute from above stuff
	public static final int NB_TILES_X = 30;
	public static final int NB_TILES_Y = 10;
	
	public static final float CURSOR_WIDTH =10f;
	
	/**
	 * Qubject size in millimeters
	 */
	public static final int QUBJECT_SIZE = 50;
	
	/*
	 * Qubjects
	 */
	private final ArrayList<Qubject> configuredQubjects
		= InitialiseProject.loadQubjectsForNewProject();
	private final ArrayList<Qubject> qubjectsOnTable
		= new ArrayList<Qubject> (configuredQubjects.size());
	
	/*
	 * Variables de temps
	 */
	/**
	 * Current time in float MS(0<= currentTime < period);
	 */
	private float currentTime =0f, totalPauseTime =0f;
	/**
	 * Starting time (Sys time)
	 */
	private long startTime;
	/**
	 * Sys time when click on pause
	 */
	private long startPauseTime = Long.MIN_VALUE;
	/**
	 * Period in float milliseconds
	 */
	private float period = 30000f; 
	
	/*
	 * Attributs coeur
	 */
	private final PlayerInterface player = new Player(this);
	private final OutputImageInterface projection = new ProjectorOutput();
	private final CameraInterface camera;
	/**
	 * Utilise un gestionnaire d'évènements /ordonnanceur 
	 * qui associera des threads à l'éxécution des tâches
	 */
	private final Sequencer sequencer;

	/**
	 * Use a Hashtable to make it faster when receiving
	 */
	private final Hashtable<Integer, Qubject> qrCodes
		= new Hashtable<Integer, Qubject>();
	/**
	 * NOTE : Utile car on avait dit qu'un qubjet pouvait jouer un son quand placé, etc...
	 * When a Qubject plays a sound, a sampleController reference must be kept to be able to tweak the sound
	 * For now, use a Hashtable with a LinkedList (which assumes that cubes may play long-enough sound that they "interlace") 
	 * ...so an unknown number of sampleControllers refs can be kept.
	 * When a soundEffect is applied on a Qubject, LinkedLists allow for a quick access to every sampleReference  
	 * ...and a quick access when deleting (ie: a Sample just finished playing)
	 * Useful information: follow this link http://stackoverflow.com/questions/322715/when-to-use-linkedlist-over-arraylist
	 * TODO : check that indeed LinkedList > ArrayList (but anyway we're only talking about only a few refs per List, so any should be fine)
	 */
	private final Hashtable<Qubject, LinkedList<SampleControllerInterface>> sampleControllers
		= new Hashtable<Qubject, LinkedList<SampleControllerInterface>>(configuredQubjects.size());
	private final Hashtable<Qubject, ScheduledFuture<?>> tasks
		= new Hashtable<Qubject, ScheduledFuture<?>>();
	
	
	
	//Variables de référence Thread
	private Thread playerThread, projectionThread, cameraThread;
	private boolean hasStarted = false, isPlaying = false;

	/**
	 * New project overload
	 * @param data reference to Data assets class
	 */
	public Qubble(){
		super();
		camera = new FakeCamera(this);
		initialise();
		
		//The sequencer no longer needs to be run
		sequencer = new Sequencer(this, period);
		cameraThread = new Thread((Runnable) camera, "Camera Thread");
		projectionThread = new Thread((Runnable) projection, "Projection OpenGL");
		playerThread = new Thread((Runnable) player, "Player Thread");
	}

	/**
	 * Open new project overload
	 * @param data Reference to Data assets class
	 * @param path The path of the saved project
	 */
	public Qubble(String path){
		super();
		sequencer = new Sequencer(this, period);
		camera = new FakeCamera(this);
		initialise();
		
		cameraThread = new Thread((Runnable) camera, "Camera Thread");
		projectionThread = new Thread((Runnable) projection, "Projection OpenGL");
		playerThread = new Thread((Runnable) player, "Player Thread");
	}
	
	/**
	 * Put every Qubject in the Hashtable
	 * 		qrCodes
	 * 		List for SampleControllers
	 */
	private void initialise(){
		for (Qubject qubject : configuredQubjects){
			sampleControllers.put(qubject, new LinkedList<SampleControllerInterface>());
			qrCodes.put(qubject.getBitIdentifier(), qubject);
		}
	}
	
	/**
	 * NOTE : requires TABLE_LENGTH and current time and total pause time
	 * Handle Play/Pause
	 * @param qubject
	 * @return time in MILLISECONDS
	 */
	public long computeQubjectStartingTime(Qubject qubject){
		//don't forget to divide double by double and not int !
		float absoluteStartingTime = 
				(float)((getTile(qubject.getCoords()).getWidth())/NB_TILES_X)*period;
		//Make sure the starting time is POSITIVE
		//Use  (a % b + b) % b (when a can be negative) 
		updateCurrentTime();
		float relativeStartingTime = absoluteStartingTime-currentTime+totalPauseTime;
		relativeStartingTime = (relativeStartingTime % period + period) % period;
		//-----DEBUG
		System.out.println("Demarrage Qubject <<" + qubject.getName() 
				+ ">> at relative time : <<"+ relativeStartingTime/1000f + ">> seconds");
		//----->> END DEBUG
		return (long) (relativeStartingTime);
	}
	
	private void updateCurrentTime(){
		currentTime = BaseRoutines.convertSysTimeToMS(Sys.getTime()-startTime)%period;
	}
	
	/**
	 * Computes the position of the Qubject on the table (including offsets !)
	 * Maps it to a percentage
	 * @param qubject
	 * @return the Y-position of the Qubject in percent 
	 */
	public float getYAsPercentage(Qubject qubject){
		return ((float)qubject.getCoords().getY()-(float)TABLE_OFFSET_Y)
				/(float)TABLE_HEIGHT;
	}

	/**
	 * Plays the Qubject sample and trigger its animation
	 * Synchronized with Player Thread
	 */
	@Override
	public void playQubject(Qubject qubject) {
		//play!
		SampleController qubjectSoundController = (SampleController) player.playSample(qubject.getSampleWhenPlayed());
		//adjust effect
		player.tweakSample(qubjectSoundController, qubject.getYAxisEffect(), (int)(getYAsPercentage(qubject)*100f));
		//add it to the list of sampleControllers
		synchronized(sampleControllers){
			sampleControllers.get(qubject).add(qubjectSoundController);
		}
		//show its animation
		projection.triggerEffect(qubject.getCoords(), qubject.getAnimationWhenPlayed());
	}
	
	@Override
	public void soundHasFinishedPlaying(SampleControllerInterface sc) {
		for(LinkedList<SampleControllerInterface> list : sampleControllers.values()){
			for (SampleControllerInterface controller : list){
				if (sc == controller){
					list.remove(sc);
					return;
				}
			}
		}
		System.err.print("Trying to remove an unexisting SoundController");
	}

	@Override
	public synchronized void QubjectDetected(int bitIdentifier, imageObject.Point pos) {
		Qubject qubject = qrCodes.get(bitIdentifier);
		
		if (qubject == null){
			System.err.print("Qubject inconnu détecté ! Pas de qubject chargé pour l'id " + bitIdentifier);
			return;
		}
		
		//Si on l'a trouvé, on change les coordonnées caméra -> OpenGL
		qubject.setCoords(Calibrate.mapToOpenGL(pos));

		//On planifie la tâche
		tasks.put(qubject, sequencer.schedule(qubject));

		//On indique a openGL qu'on a un Qubject 
		projection.highlightQubject(qubject.getCoords());

		//On joue l'animation posé sur la table
		projection.triggerEffect(qubject.getCoords(), qubject.getAnimationWhenDetected());

		//...et on demande le verrou pour ajouter à la liste des objets sur la table
		synchronized(qubjectsOnTable){
			qubjectsOnTable.add(qubject);				
		}
	}
	
	@Override
	public void QubjectRemoved(int bitIdentifier) {
		Qubject qubject = qrCodes.get(bitIdentifier);

		if (qubject == null){
			System.err.print("Qubject inconnu détecté ! Pas de qubject chargé pour l'id " + bitIdentifier);
			return;
		}
		
		//On annule la tâche
		tasks.get(qubject).cancel(true);

		//TODO : dire au player d'arrêter le son

		//On masque son ancien emplacement 
		projection.highlightQubject(qubject.getCoords());

		//On l'enlève de la liste des qubjects présents sur la table
		synchronized (qubjectsOnTable){
			qubjectsOnTable.remove(qubject);
		}
		return;
	}
	
	@Override
	public void QubjectHasMoved(int bitIdentifier, imageObject.Point position) {
		Qubject qubject = qrCodes.get(bitIdentifier);
		
		if (qubject == null){
			System.err.print("Qubject inconnu détecté ! Pas de qubject chargé pour l'id " + bitIdentifier);
			return;
		}
		//On masque son ancien emplacement 
		projection.highlightQubject(qubject.getCoords());

		//on change les coordonnées caméra -> OpenGL
		org.lwjgl.util.Point glCoords = Calibrate.mapToOpenGL(position);
		qubject.setCoords(glCoords);

		//On replanifie
		sequencer.reschedule(tasks.get(qubject), qubject);

		//On indique son nouvel emplacement
		projection.highlightQubject(qubject.getCoords());
	}

	@Override
	public void QubjectHasTurned(int bitIdentifier, float dR) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void playPause(){
		if (hasStarted){
			if(isPlaying){
				this.startPauseTime = Sys.getTime();
			}
			else{
				this.totalPauseTime += BaseRoutines.convertSysTimeToMS(Sys.getTime()-startPauseTime);
			}
			isPlaying = !isPlaying;
			sequencer.playPause();
			projection.playPause(projectionThread);
			player.playPause();
		}
	}

	@Override
	public void close() {
		sequencer.destroy();
		player.destroy();
		projection.terminate();
	}

	@Override
	public void start() {
		if(!hasStarted){
			startTime = Sys.getTime();
			projectionThread.start();
			playerThread.start();
			cameraThread.start();
			hasStarted = true;
			isPlaying = true;
		}
	}

	@Override
	public void toggleGrid() {
		this.projection.toggleGrid();
	}

	@Override
	public Dimension getPosition(MediaInterface qubject) {
		QRInterface qr = (QRInterface) qubject;
		Point pos = qr.getCoords();
		return getTile(pos);
	}
	
	/**
	 * 
	 * @param pos
	 * @return the X,Y tile where the qubject is
	 */
	public static Dimension getTile(org.lwjgl.util.Point pos){
		return new Dimension((int)((pos.getX()-Qubble.TABLE_OFFSET_X)/Qubble.SPACING_X),
				(int)((pos.getY()-Qubble.TABLE_OFFSET_Y)/Qubble.SPACING_Y));
	}
	

	@Override
	public ArrayList<Qubject> getAllQubjects() {
		return configuredQubjects;
	}

	@Override
	public ArrayList<Qubject> getQubjectsOnTable() {
		return qubjectsOnTable;
	}
	
	public float getPeriod() {
		return period;
	}
	
	public Hashtable<Qubject, ScheduledFuture<?>> getTasks() {
		return tasks;
	}

	//TODO : remove if period is fixed to 30
	public void setPeriod(float period) {
		this.period = period;
	}

	@Override
	public void panic() {
		close();
		
		hasStarted = false;
		isPlaying = false;
		
		cameraThread = new Thread((Runnable) camera, "Camera Thread");
		projectionThread = new Thread((Runnable) projection, "Projection OpenGL");
		//TODO : cancel all tasks
		playerThread = new Thread((Runnable) player, "Player Thread");
	}

}
