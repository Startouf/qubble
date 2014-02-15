package sequencer;

import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import qubject.Qubject;

public class QubjectTask implements Runnable
{
	private final Qubject qubject;
	private final QubbleInterface qubble;

	private static void log(String aMsg){
		System.out.println(aMsg);
	}

	public QubjectTask(QubbleInterface qubble, Qubject qubject){
		this.qubject=qubject;
		this.qubble = qubble;
	}

	/**
	 * Note : The eventScheduler can span multiple threads
	 */
	@Override
	public void run() {
		qubble.playQubject(qubject);
	}
}
