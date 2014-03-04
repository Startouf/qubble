package camera;

import imageObject.Point;
import sequencer.QubbleInterface;

public class FakeCamera implements CameraInterface, Runnable
{
	private final QubbleInterface Qubble;
	
	
	public FakeCamera(QubbleInterface qubble){
		this.Qubble = qubble;
	}

	@Override
	public void run() {
		Qubble.setQubjectOnTable(100101, new Point(350, 350));
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
