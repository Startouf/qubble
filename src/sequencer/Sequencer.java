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
	
	/**
	 * 
	 * @param qubble
	 * @param tempo
	 */
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
			Runnable qubjectTask = new QubjectTask(qubble, qubject);
		    ScheduledFuture<?> scheduledQubjectTask = fScheduler.scheduleAtFixedRate(
		      qubjectTask, qubble.computeQubjectStartingTime(qubject), (long)period*1000, TimeUnit.MILLISECONDS
		    );
		}
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
	/**
	 * Should be called before closing a project
	 * TODO: Does the garbage collector handle this task well ?
	 */
	public void terminate(){
		fScheduler.shutdownNow();
	}
	
	/**
	 * TODO :
	 * Stop all tasks and restart them or...
	 * Find a way to Pause all tasks ?
	 */
	public void playPause(){
		//TODO
	}

	/**
	 * If both period and current time are changed, please use another method
	 * (otherwise, would recalculate 2 times the schedule)
	 * @param period
	 */
	public void setPeriod(double period) {
		this.period = period;
		recalculate();
	}

	/**
	 * If both period and current time are changed, please use another method
	 * (otherwise, would recalculate 2 times the schedule)
	 * @param currentTime
	 */
	public void setCurrentTime(float currentTime) {
		this.currentTime = currentTime;
		recalculate();
	}
	
}
