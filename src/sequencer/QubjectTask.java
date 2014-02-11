package sequencer;

import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import qubject.Qubject;

public class QubjectTask implements Runnable
{
	private final Qubject qubject;


	private static void log(String aMsg){
		System.out.println(aMsg);
	}

	public QubjectTask(Qubject qubject){
		this.qubject=qubject;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
