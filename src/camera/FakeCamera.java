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
		qubble.setQubjectOnTable(100100, new Point(150, 350));
		qubble.setQubjectOnTable(100101, new Point(250, 550));
		qubble.setQubjectOnTable(100110, new Point(450, 350));
		qubble.setQubjectOnTable(100111, new Point(760, 550));
		qubble.setQubjectOnTable(101000, new Point(950, 150));
		qubble.setQubjectOnTable(101001, new Point(1100, 650));
//		try {
//			wait(30000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		qubble.QubjectHasMoved(100100, new Point(150, 550));
//		qubble.QubjectHasMoved(100101, new Point(350, 150));
//		qubble.QubjectHasMoved(100110, new Point(550, 350));
//		qubble.QubjectHasMoved(100111, new Point(760, 550));
//		qubble.QubjectHasMoved(101000, new Point(950, 150));
		
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
