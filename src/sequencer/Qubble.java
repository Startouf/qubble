package sequencer;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

import org.lwjgl.Sys;
import org.lwjgl.util.Point;

import calibration.Calibrate;
import camera.CameraInterface;
import camera.FakeCamera;
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
	 * Variables de données
	 */
	private final Data data;
	private final ArrayList<Qubject> configuredQubjects;
	private final ArrayList<Qubject> qubjectsOnTable;
	
	/*
	 * Variables séquenceur/Dispatching d'évènements
	 */
	/**
	 * Current time in float (0<= currentTime < period);
	 */
	private float currentTime;
	/**
	 * Starting time (SYSTEM TIME IN TICKS!)
	 * (Might be updated with play/Pause)
	 */
	private long startTime = Sys.getTime();
	/**
	 * Period in float milliseconds
	 */
	private float period = 30000f; 
	public static final float TEST_PERIOD = 30;
	private final PlayerInterface player;
	private final OutputImageInterface projection;
	private final CameraInterface camera;
	/**
	 * Utilise un gestionnaire d'évènements /ordonneur 
	 * qui associera des threads à l'éxécution des tâches
	 */
	private final Sequencer sequencer;
	
	/**
	 * NOTE : Utile car on avait dit qu'un qubjet pouvait jouer un son quand placé, etc...
	 * When a Qubject plays a sound, a sampleController reference must be kept to be able to tweak the sound
	 * For now, use a Hashtable with a LinkedList (which assumes that cubes may play long-enough sound that they "interlace") 
	 * ...so an unknown number of sampleControllers refs can be kept.
	 * When a soundEffect is applied on a Qubject, LinkedLists allow for a quick access to every sampleReference  
	 * ...and a quick access when deleting (ie: a Sample just finished playing)
	 * Useful information: follow this link http://stackoverflow.com/questions/322715/when-to-use-linkedlist-over-arraylist
	 * TODO : check that indeed LinkedList > ArrayList (but anyway we're only talking about only a few refs per List, so any should be fine)
	 * TODO : check if we need to use a concurrent version (CoucurrentHashMap, ConcurrentLinkedQueue...) 
	 */
	private final Hashtable<Qubject, LinkedList<SampleControllerInterface>> sampleControllers;
	
	/*
	 * Variables liés à la taille de la table projetée
	 * (Pour les variables de calibration, utiliser les variables decalibration.Calibrate)
	 */
	public static final int TABLE_LENGTH = 1200;
	public static final int TABLE_HEIGHT = 600;
	public static final int TABLE_OFFSET_X = 50; 
	public static final int TABLE_OFFSET_Y=50;
	public static final float GRID_COLUMNS_PER_SEC = 1f;
	public static final float GRID_ROWS_PER_SEC = 10f;
	public static final float SPACING_X = (float)TABLE_LENGTH/TEST_PERIOD/GRID_COLUMNS_PER_SEC;
	public static final float SPACING_Y = (float)TABLE_HEIGHT/GRID_ROWS_PER_SEC;
	
	public static final float CURSOR_WIDTH =10f;
	/**
	 * Qubject size in millimeters
	 */
	public static final int QUBJECT_SIZE = 50;
	
	/*
	 * Variables de référence Thread (synchronisation)
	 */
	private final Thread sequencerThread, playerThread, projectionThread, cameraThread;
	private boolean hasStarted = false;

	/**
	 * New project overload
	 * @param data reference to Data assets class
	 */
	public Qubble(Data data){
		super();
		this.data = data;
		player = new Player(this);
		projection = new ProjectorOutput();
		camera = new FakeCamera(this);
		configuredQubjects = InitialiseProject.loadQubjectsForNewProject();
		qubjectsOnTable = new ArrayList<Qubject> (configuredQubjects.size());
		sampleControllers = new Hashtable<Qubject, LinkedList<SampleControllerInterface>>(configuredQubjects.size());
		initialiseSampleControllers();
		sequencer = new Sequencer(this, period);
		
		cameraThread = new Thread((Runnable) camera, "Camera Thread");
		projectionThread = new Thread((Runnable) projection, "Projection OpenGL");
		sequencerThread = new Thread((Runnable) sequencer, "Thread Sequencer");
		playerThread = new Thread((Runnable) player, "Player Thread");
	}

	/**
	 * Open new project overload
	 * @param data Reference to Data assets class
	 * @param path The path of the saved project
	 */
	public Qubble(Data data, String path){
		super();
		this.data = data;
		player = new Player(this);
		sequencer = new Sequencer(this, period);
		camera = new FakeCamera(this);
		projection = new ProjectorOutput();
		configuredQubjects = InitialiseProject.loadQubjectsFromProject(path);
		qubjectsOnTable = new ArrayList<Qubject> (configuredQubjects.size());
		sampleControllers = new Hashtable<Qubject, LinkedList<SampleControllerInterface>>(configuredQubjects.size());
		initialiseSampleControllers();
		
		cameraThread = new Thread((Runnable) camera, "Camera Thread");
		projectionThread = new Thread((Runnable) projection, "Projection OpenGL");
		sequencerThread = new Thread((Runnable) sequencer, "Thread Sequencer");
		playerThread = new Thread((Runnable) player, "Player Thread");
	}

	/**
	 * Debug Overload : don't start threads !
	 * @param data
	 */
	public Qubble(Data data, boolean runThreads){
		super();
		this.data = data;
		player = new Player(this);
		projection = new ProjectorOutput();
		camera = new FakeCamera(this);
		configuredQubjects = InitialiseProject.loadQubjectsForNewProject();
		qubjectsOnTable = new ArrayList<Qubject> (configuredQubjects.size());
		sampleControllers = new Hashtable<Qubject, LinkedList<SampleControllerInterface>>(configuredQubjects.size());
		initialiseSampleControllers();
		sequencer = new Sequencer(this, period);
		
		cameraThread = new Thread((Runnable) camera, "Camera Thread");
		projectionThread = new Thread((Runnable) projection, "Projection OpenGL");
		sequencerThread = new Thread((Runnable) sequencer, "Thread Sequencer");
		playerThread = new Thread((Runnable) player, "Player Thread");
		
		if (runThreads){
			start();
		}
	}
	
	/**
	 * Put every Qubject in the Hashtable, and initialise LinkedLists of their sampleControllers
	 */
	private void initialiseSampleControllers(){
		for (Qubject qubject : configuredQubjects){
			sampleControllers.put(qubject, new LinkedList<SampleControllerInterface>());
		}
	}

	@Override
	public ArrayList<Qubject> getAllQubjects() {
		return configuredQubjects;
	}

	@Override
	public ArrayList<Qubject> getQubjectsOnTable() {
		return qubjectsOnTable;
	}
	
	/**
	 * NOTE : requires TABLE_LENGTH and current time
	 * @param qubject
	 * @return time in MILLISECONDS
	 */
	public long computeQubjectStartingTime(Qubject qubject){
		//TODO : Quantify !
		//don't forget to divide double by double and not int !
		double absoluteStartingTime = 
				(qubject.getCoords().getX()-(double)TABLE_OFFSET_X)
				/((double)TABLE_LENGTH)		*period;
		System.out.println("Demarrage absolu Qubject " + qubject.getName() 
				+ " At absolute time : "+ absoluteStartingTime/1000f + " seconds");
		return (long) (absoluteStartingTime-currentTime);
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
	 * TODO : synchronise (when the player tells us a Sound is over, may data race with sequencer)
	 * (and if multiple threads are used by the sequencer, might data race simply put.)
	 */
	@Override
	public void playQubject(Qubject qubject) {
		//play!
		SampleController qubjectSoundController = (SampleController) player.playSample(qubject.getSampleWhenPlayed());
		//adjust effect
		player.tweakSample(qubjectSoundController, qubject.getYAxisEffect(), (int)(getYAsPercentage(qubject)*100f));
		//add it to the list of sampleControllers
		sampleControllers.get(qubject).add(qubjectSoundController);
		//show its animation
		projection.triggerEffect(qubject.getCoords(), qubject.getAnimationWhenPlayed());
	}
	

	/**
	 * TODO : synchronise ?  might have to make a custom lock to make sure nobody does something crazy 
	 */
	public void closeProject(){
		player.destroy();
		sequencer.terminate();
		projection.terminate();
	}

	public float getPeriod() {
		return period;
	}

	public void setPeriod(float period) {
		this.period = period;
	}
	
	/**
	 * Absolute System Time in milliseconds
	 * @return time in milliseconds
	 */
	public static float getTime(){
		return Sys.getTime()*1000/Sys.getTimerResolution();
	}

	@Override
	public void playPause(){
		if (hasStarted){
			sequencer.playPause(sequencerThread);
			projection.playPause(projectionThread);
			player.playPause();
		}
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
	public synchronized void setQubjectOnTable(int bitIdentifier, imageObject.Point pos) {
		for (Qubject qubject : configuredQubjects){
			if (qubject.getBitIdentifier() == bitIdentifier){
				//Si on l'a trouvé, on change les coordonnées caméra -> OpenGL
				org.lwjgl.util.Point glCoords = Calibrate.mapToOpenGL(pos);
				qubject.setCoords(glCoords);
				
				//On indique a openGL qu'on a un Qubject 
				projection.triggerQubject(qubject.getCoords());
				
				//...et on demande le verrou pour ajouter à la liste des objets sur la table
				synchronized(qubjectsOnTable){
					qubjectsOnTable.add(qubject);
					//Tell the sequencer somehting must be done
					sequencerThread.interrupt();					
				//Has been found, so we can end the loop
				return;
				}
			}
		}
		//If the qubject was not found
		System.err.print("Qubject inconnu détecté ! Pas de qubject chargé pour l'id " + bitIdentifier);
	}

	@Override
	public void close() {
		sequencer.terminate();
		sequencerThread.interrupt();
	}

	@Override
	public void QubjectGone(int bitIdentifier) {
		for (Qubject qubject : qubjectsOnTable){
			if (qubject.getBitIdentifier() == bitIdentifier){
				//Si on l'a trouvé on demande le verrou pour l'écriture
				synchronized (qubjectsOnTable){
					qubjectsOnTable.remove(qubject);
				//Has been found, so we can end the loop
				return;
				}
			}
		}
		//If the qubject was not found
		System.err.print("Le Qubject était supposé déjà sur la table mais ne l'est pas" + bitIdentifier);
	}

	@Override
	public void start() {
		if(!hasStarted){
			projectionThread.start();
			playerThread.start();
			sequencerThread.start();
			cameraThread.start();
			hasStarted = true;
		}
	}

	@Override
	public void toggleGrid() {
		this.projection.toggleGrid();
	}

	@Override
	public String whereIsIt(MediaInterface qubject) {
		QRInterface qr = (QRInterface) qubject;
		Point pos = qr.getCoords();
		return new String("X : " + (int)((pos.getX()-Qubble.TABLE_OFFSET_X)/Qubble.SPACING_X) + 
				" Y : " + (int)((pos.getY()-Qubble.TABLE_OFFSET_Y)/Qubble.SPACING_Y));
	}
}
