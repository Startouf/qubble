package sequencer;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

import org.lwjgl.Sys;

import opengl.ImageInterface;
import opengl.ProjectorOutput;
import audio.Player;
import audio.SampleController;
import audio.SampleControllerInterface;
import audio.SoundInterface;
import database.Data;
import database.InitialiseProject;
import qubject.QRInterface;
import qubject.Qubject;

public class Qubble implements QubbleInterface {
	private final Data data;
	private final ArrayList<Qubject> configuredQubjects;
	private final ArrayList<Qubject> qubjectsOnTable;
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
	 * Period in float in seconds
	 */
	private float period = 30; 
	public static final float TEST_PERIOD = 30;
	private final SoundInterface player;
	private final ImageInterface projection;
	private final Sequencer sequencer;
	/**
	 * When a Qubject plays a sound, a sampleController reference must be kept to be able to tweak the sound
	 * For now, use a Hashtable with a LinkedList (which assumes that cubes may play long-enough sound that they "interlace") 
	 * ...so an unknown number of sampleControllers refs can be kept.
	 * When a soundEffect is applied on a Qubject, LinkedLists allow for a quick access to every sampleReference  
	 * ...and a quick access when deleting (ie: a Sample just finished playing)
	 * Useful information: follow this link http://stackoverflow.com/questions/322715/when-to-use-linkedlist-over-arraylist
	 * TODO : check that indeed LinkedList > ArrayList (but anyway we're only talking about only a few refs per List, so any should be fine) 
	 */
	private final Hashtable<Qubject, LinkedList<SampleControllerInterface>> sampleControllers;
	private Iterator iter;
	//Not clear whether thos two should be final
	public static final int TABLE_LENGTH = 1200;
	public static final int TABLE_HEIGHT = 600;
	public static final int TABLE_OFFSET_X = 50; 
	public static final int TABLE_OFFSET_Y=50;
	public static final float GRID_COLUMNS_PER_SEC = 1;
	public static final float GRID_ROWS_PER_SEC = 1;
	public static final float SPACING_X = (float)TABLE_LENGTH/TEST_PERIOD/GRID_COLUMNS_PER_SEC;
	public static final float SPACING_Y = (float)TABLE_HEIGHT/GRID_ROWS_PER_SEC;
	
	public static final float CURSOR_WIDTH =10f;

	/**
	 * Qubject size in millimeters
	 */
	public static final int QUBJECT_SIZE = 50;

	/**
	 * New project overload
	 * @param data reference to Data assets class
	 */
	public Qubble(Data data){
		super();
		this.data = data;
		player = new Player();
		projection = new ProjectorOutput();
		configuredQubjects = InitialiseProject.loadQubjectsForNewProject();
		qubjectsOnTable = new ArrayList<Qubject> (configuredQubjects.size());
		sampleControllers = new Hashtable<Qubject, LinkedList<SampleControllerInterface>>(configuredQubjects.size());
		initialiseSampleControllers();
		sequencer = new Sequencer(this, period);
		
		//TODO : launch threads 
	}

	/**
	 * Open new project overload
	 * @param data Reference to Data assets class
	 * @param path The path of the saved project
	 */
	public Qubble(Data data, String path){
		super();
		this.data = data;
		player = new Player();
		sequencer = new Sequencer(this, period);
		projection = new ProjectorOutput();
		configuredQubjects = InitialiseProject.loadQubjectsFromProject(path);
		qubjectsOnTable = new ArrayList<Qubject> (configuredQubjects.size());
		sampleControllers = new Hashtable<Qubject, LinkedList<SampleControllerInterface>>(configuredQubjects.size());
		initialiseSampleControllers();
	}
	/**
	 * Put every Qubject in the Hashtable, and initialise LinkedLists of their sampleControllers
	 */
	private void initialiseSampleControllers(){
		for (Qubject qubject : configuredQubjects){
			sampleControllers.put(qubject, new LinkedList<SampleControllerInterface>());
		}
		//Iterateur sur les sampleControllers
		iter = sampleControllers.entrySet().iterator();
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
	 * This implementation checks if the qubject is already on the list
	 */
	@Override
	public void newQubjectOnTable(QRInterface qubject) {
		//NOTE : if the program works well, this test shouldn't be needed
		if (!qubjectsOnTable.contains((Qubject) qubject)){
			qubjectsOnTable.add((Qubject)qubject);
		}
	}

	@Override
	public void qubjectRemovedFromTable(QRInterface qubject) {
		if (qubjectsOnTable.contains((Qubject) qubject)){
			qubjectsOnTable.remove((Qubject)qubject);
		}
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
				/(float)TABLE_HEIGHT
						;
	}

	/**
	 * Plays the Qubject sample and trigger its animation
	 */
	@Override
	public void playQubject(Qubject qubject) {
		SampleController qubjectSoundController = (SampleController) player.playSample(qubject.getSampleWhenPlayed());
		player.tweakSample(qubjectSoundController, qubject.getYAxisEffect(), getYAsPercentage(qubject));
		sampleControllers.get(qubject).add(qubjectSoundController);
		projection.triggerEffect(qubject.getCoords(), qubject.getAnimationWhenPlayed());
	}
	
	public void closeProject(){
		sequencer.terminate();
	}

	public float getPeriod() {
		return period;
	}

	public void setPeriod(float period) {
		this.period = period;
	}
	
	/**
	 * Time in milliseconds
	 * @return time in milliseconds
	 */
	public static long getTime(){
		return Sys.getTime()*1000/Sys.getTimerResolution();
	}
	
	public void playPause(){
		sequencer.playPause();
		projection.playPause();
		while(iter.hasNext()){
			for(SampleControllerInterface sc :sampleControllers.get(iter.next())){
				player.playPause(sc);
			}
		}
	}
}
