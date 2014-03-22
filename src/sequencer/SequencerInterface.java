package sequencer;

import java.util.concurrent.ScheduledFuture;

import qubject.Qubject;
import qubject.SampleInterface;
import audio.EffectType;
import audio.SampleControllerInterface;

public interface SequencerInterface
{
	/**
	 * Schedule a task for the first time
	 * @param the qubject which must be scheduled
	 * @return The scheduled task
	 */
	public ScheduledFuture<?> schedule(Qubject qubject);
	
	/**
	 * Reschedule a task (eg : Qubject has moved)
	 * @param task
	 * @param qubject
	 */
	public void reschedule(ScheduledFuture<?> task, Qubject qubject);

	/**
	 * Pause everything 
	 */
	public void playPause();

	/**
	 * Quand le projet est fermé, il faut terminer le thread et tout détruire
	 * (la destruction se fait par le Garbage Collector, en gros il faut juste terminer les threads)
	 */
	public void destroy();
}
