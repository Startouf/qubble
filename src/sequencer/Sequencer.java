package sequencer;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.lwjgl.opengl.Display;

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
public class Sequencer implements SequencerInterface
{
	//TODO : store these variables somewhere else
	private final int NUM_THREADS = 2;
	
	private final ScheduledExecutorService fScheduler;
	/**
	 * Time is converted later when it's needed by the Schedule Service
	 */
	private boolean play = true;
	private final Qubble qubble;
	/**
	 * List of scheduled tasks
	 * Initialise with the number of qubjects 
	 * (and eventually multiply if they need more tasks)
	 */
	//private final ArrayList<ScheduledFuture<?>> scheduledQubjects;
	
	/**
	 * @param qubble
	 * @param tempo
	 */
	public Sequencer(Qubble qubble, float tempo){
		this.qubble = qubble;
		this.fScheduler = Executors.newScheduledThreadPool(NUM_THREADS);
	}

	/**
	 * Reschedule the actions for EVERY Qubject (destroys every existing Schedule Actions)
	 * DOES NOT ASSUME TASKS HAVE BEEN CLEARED
	 */
	private void recalculate(Hashtable<Qubject, ScheduledFuture<?>> tasks){
		ArrayList<Qubject> list;
		synchronized(list = this.qubble.getQubjectsOnTable()){
			for (Qubject qubject : list){
				tasks.put(qubject, schedule(qubject));
			}
		}
	}

	/**
	 * Forcefully removes every task (any running task will end ASAP without finishing)
	 */
	public void destroyScheduledtasks(Hashtable<Qubject, ScheduledFuture<?>> tasks){
		for (ScheduledFuture<?> task : tasks.values()){
			task.cancel(true);
		}
	}

	@Override
	public ScheduledFuture<?> schedule(Qubject qubject) {
		Runnable qubjectTask = new QubjectTask(qubble, qubject);
		return fScheduler.scheduleAtFixedRate(
				qubjectTask, qubble.computeQubjectStartingTime(qubject), 
				(long) qubble.getPeriod(), TimeUnit.MILLISECONDS
				);
	}

	@Override
	public void reschedule(ScheduledFuture<?> task, Qubject qubject) {
		task.cancel(true);
		task = schedule(qubject);
	}

	/**
	 * Should be called before closing a project : 
	 * terminates the Scheduler Thread
	 */
	@Override
	public void destroy() {
		fScheduler.shutdownNow();
	}

	@Override
	public void playPause() {
		if(play){ //pause the sequencer
			destroyScheduledtasks(this.qubble.getTasks());
		}
		else{
			recalculate(this.qubble.getTasks());
		}
		play = !play;
		System.out.println("Sequencer is playing" + play);
	}
}
