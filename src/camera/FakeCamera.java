package camera;

import imageObject.Point;
import sequencer.QubbleInterface;

public class FakeCamera implements CameraInterface, Runnable
{
	private final QubbleInterface qubble;
	
	
	public FakeCamera(QubbleInterface qubble){
		this.qubble = qubble;
	}

	@Override
	public synchronized void run() {
		try {
			wait(300); //in milliseconds
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		qubble.setQubjectOnTable(100101, new Point(350, 350));
		try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void terminate() {
		
	}
	
}
