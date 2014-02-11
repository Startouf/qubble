package sequencer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import qubject.Qubject;

/**
 * TODO
 * @author duchon
 * The class that tells when to show and play what animation/music/sound effect
 * 
 * VERY INTERESTING:
 * http://www.javapractices.com/topic/TopicAction.do?Id=54
 * 
 * Seems like using ScheduledExecutor Service is the most recent way to handle tasks
 *
 */
public class Sequencer
{
	//TODO : store these variables somewhere else
	private static final int TABLE_LENGTH = 800;
	private static final int TABLE_HEIGHT = 800;
	private final int NUM_THREADS = 1;
	private final ScheduledExecutorService fScheduler;
	/**
	 * period in seconds
	 */
	private double period;
	/**
	 * Time is converted later when it's needed by the Schedule Service
	 */
	private float currentTime =0;
	private final Qubble qubble;
	/**
	 * List of scheduled tasks
	 * Initialise with the number of qubjects 
	 * (and eventually multiply if they need more tasks)
	 */
	private final ArrayList<ScheduledFuture<?>> scheduledQubjects = 
			new ArrayList<ScheduledFuture<?>>(this.qubble.getAllQubjects().size());
	
	public Sequencer(Qubble qubble, float tempo){
		this.qubble = qubble;
		this.period = tempo;
		this.fScheduler = Executors.newScheduledThreadPool(NUM_THREADS);
		
		recalculate();
		
		//TODO : loop that waits for changes to be detected
		//Something like try/catch on wait() with InterruptedException
	}

	/**
	 * Reschedule the actions for EVERY Qubject (destroys every existing Schedule Actions)
	 */
	private void recalculate(){
		destroyScheduledActions();
		//remove the nulls from the list
		this.scheduledQubjects.clear();
		for (Qubject qubject : this.qubble.getQubjectsOnTable()){
			//Schedule when the Qubject should be played
			Runnable qubjectTask = new QubjectTask(qubject);
		    ScheduledFuture<?> scheduledQubjectTask = fScheduler.scheduleAtFixedRate(
		      qubjectTask, getQubjectStartingTime(qubject), (long)period*1000, TimeUnit.MILLISECONDS
		    );
		}
	}
	
	/**
	 * 
	 * @param qubject
	 * @return time in MILLISECONDS
	 */
	private long getQubjectStartingTime(Qubject qubject){
		//TODO : Quantify !
		//dividing double by double !
		return (long) (qubject.getCoords().getX()/(double)TABLE_LENGTH*period);
	}

	/**
	 * Forcefully removes every task (any running task will end ASAP without finishing)
	 */
	private void destroyScheduledActions(){
		for(ScheduledFuture<?> qubjectTask : scheduledQubjects){
			//Note : Parameter is MayInterruptIfRunning
			//What about letting the musics and animations end before we crush everything ??
			//But then if the table restarts just after, it may end up weird....
			qubjectTask.cancel(true);
		}

	}
	
	private void playPause(){
		
	}
}
