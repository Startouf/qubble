package sequencer;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ScheduledFuture;

import org.lwjgl.Sys;
import org.lwjgl.input.Controllers;
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
 * Acts as an observable for the audio, GUI and openGL modules
 *
 */
public class Qubble implements QubbleInterface {
	
	public static final int BPM = 128;
	public static final float BPS = BPM*60f/(float)BPM;
	/** 
	 * Period (time to make a loop) in float milliseconds
	 * Samples are 128 bpm
	 * -> total period of 60s/4 = 15s (/4 because it's nice)
	 */
	public static final float LOOP_MS = 15000f; //	= 15000f; 
	/*
	 * Constantes de projection
	 * (Pour les variables de calibration, utiliser les variables de calibration.Calibrate)
	 */
	public static final int TABLE_OFFSET_X = 50; 
	public static final int TABLE_OFFSET_Y=50;
	public static final int TABLE_LENGTH = Calibrate.OpenGL_WIDTH - 2*TABLE_OFFSET_X;
	public static final int TABLE_HEIGHT = Calibrate.OpenGL_HEIGHT - 2*TABLE_OFFSET_Y;
	
	/**
	 * One measure displayed on Qubble
	 */
	public static final float GRID_COLUMNS = 8f;
	public static final float GRID_ROWS = 1f;
	public static final float SPACING_X = (float)TABLE_LENGTH/GRID_COLUMNS;
	public static final float SPACING_Y = (float)TABLE_HEIGHT/GRID_ROWS;
	
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
	
	/*
	 * Attributs coeur
	 */
	private final PlayerInterface player = new Player(this);
	private final OutputImageInterface projection = new ProjectorOutput(this);
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
		sequencer = new Sequencer(this, LOOP_MS);
		cameraThread = new Thread((Runnable) camera, "Camera Thread");
		projectionThread = new Thread((Runnable) projection, "Projection OpenGL");
		playerThread = new Thread((Runnable) player, "Player Thread");
		
		prepare();
	}

	/**
	 * Open new project overload
	 * @param data Reference to Data assets class
	 * @param path The path of the saved project
	 */
	public Qubble(String path){
		super();
		sequencer = new Sequencer(this, LOOP_MS);
		camera = new FakeCamera(this);
		initialise();
		
		cameraThread = new Thread((Runnable) camera, "Camera Thread");
		projectionThread = new Thread((Runnable) projection, "Projection OpenGL");
		playerThread = new Thread((Runnable) player, "Player Thread");
		
		prepare();
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
		//don't forget to divide float by float and not int !
		float absoluteStartingTime = 
				((float)(getTile(qubject.getCoords()))*LOOP_MS/GRID_COLUMNS);
		//Make sure the starting time is POSITIVE
		//Use  (a % b + b) % b (when a can be negative) 
		updateCurrentTime();
		float relativeStartingTime = absoluteStartingTime-(currentTime%LOOP_MS)+totalPauseTime;
		relativeStartingTime = (relativeStartingTime + LOOP_MS) % LOOP_MS;
		
		//-----DEBUG
		System.out.println("Demarrage Qubject <<" + qubject.getName() 
				+ ">> at relative time : <<"+ relativeStartingTime/1000f + ">> seconds");
		//----->> END DEBUG
		
		return (long) (relativeStartingTime);
	}
	
	private void updateCurrentTime(){
		currentTime = BaseRoutines.convertSysTimeToMS(Sys.getTime()-startTime)%LOOP_MS;
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
			synchronized(list){
				Iterator<SampleControllerInterface> iter = list.iterator();
				while (iter.hasNext()){
					if (sc == iter.next()){
						iter.remove();
						return;
					}
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
		//projection.highlightQubject(qubject.getCoords());
		projection.trackQubject(qubject);

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

		//TODO : dire au player d'arrêter le son ?? (A discuter)

		//On masque son ancien emplacement 
		//projection.highlightQubject(qubject.getCoords());
		projection.trackQubject(qubject);

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
		//On masque son ancien emplacement OLD
//		projection.highlightQubject(qubject.getCoords());

		//on change les coordonnées caméra -> OpenGL
		org.lwjgl.util.Point glCoords = Calibrate.mapToOpenGL(position);
		qubject.setCoords(glCoords);

		//On replanifie
//		sequencer.reschedule(tasks.get(qubject), qubject);
		
		for(SampleControllerInterface sample : sampleControllers.get(qrCodes.get(qubject))){
			player.tweakSample(sample, qubject.getYAxisEffect(), qubject.getCoords().getY());
		}

		//On indique son nouvel emplacement OLD
//		projection.highlightQubject(qubject.getCoords());
	}

	@Override
	public void QubjectHasTurned(int bitIdentifier, float dR) {
		Qubject qubject = qrCodes.get(bitIdentifier);
		
		if (qubject == null){
			System.err.print("Qubject inconnu détecté ! Pas de qubject chargé pour l'id " + bitIdentifier);
			return;
		}
		
		qubject.setRotation((float) ((qubject.getRotation()+dR)%(2*Math.PI)));
		
		for(SampleControllerInterface controller : sampleControllers.get(qrCodes.get(bitIdentifier))){
			//TODO : float --> int ? (for value of tweaksample)
			player.tweakSample(controller, qubject.getRotationEffect(), (int) (qubject.getRotation()*100/(2*Math.PI)));
		}
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
			projection.playPause();
			player.playPause();
		}
		else{
			startTime = Sys.getTime();
			hasStarted = true;
			isPlaying = true;
			cameraThread.start();
			projection.playPause();
		}
	}

	@Override
	public void close() {
		sequencer.destroy();
		player.destroy();
		projection.terminate();
	}

	@Override
	public void prepare() {
		if(!hasStarted){
			cameraThread.setPriority(Thread.MIN_PRIORITY);
			playerThread.setPriority(Thread.MAX_PRIORITY);
			projectionThread.start();
			playerThread.start();
		}
	}

	@Override
	public void toggleGrid() {
		this.projection.toggleGrid();
	}

	@Override
	public Dimension getPosition(MediaInterface qubject) {
		QRInterface qr = (QRInterface) qubject;
		if (qubjectsOnTable.contains(qubject) == true){
			return new Dimension(-1, -1);
		}
		Point pos = qr.getCoords();
		return new Dimension(getTile(pos), qr.getCoords().getY());
	}
	
	/**
	 * @param pos
	 * @return the column where the point is (column from <b>1</b> to 8 !)
	 */
	public static int getTile(org.lwjgl.util.Point pos){
		return (int) Math.floor((float)(pos.getX()-Qubble.TABLE_OFFSET_X)/Qubble.SPACING_X);
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
		return LOOP_MS;
	}
	
	public Hashtable<Qubject, ScheduledFuture<?>> getTasks() {
		return tasks;
	}

	@Override
	public void panic() {
		player.destroy();
		projection.terminate();
		camera.terminate();
		
		hasStarted = false;
		isPlaying = false;
		
		cameraThread = new Thread((Runnable) camera, "Camera Thread");
		projectionThread = new Thread((Runnable) projection, "Projection OpenGL");
		sequencer.destroyScheduledtasks(tasks);
		playerThread = new Thread((Runnable) player, "Player Thread");
	}

}
