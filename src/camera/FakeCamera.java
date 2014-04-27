package camera;

import java.util.ArrayList;
import java.util.HashMap;

import imageObject.Point;
import sequencer.QubbleInterface;

public class FakeCamera implements ImageDetectionInterface, Runnable
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
			e1.printStackTrace();
		}
		qubble.QubjectDetected(100100, new Point(150, 350));
		qubble.QubjectDetected(100101, new Point(250, 550));
		qubble.QubjectDetected(100110, new Point(450, 350));
		qubble.QubjectDetected(100111, new Point(760, 550));
		qubble.QubjectDetected(101000, new Point(950, 150));
		qubble.QubjectDetected(101001, new Point(1100, 250));
		try {
			wait(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		qubble.QubjectHasMoved(100100, new Point(150, 550));
		qubble.QubjectHasMoved(100101, new Point(350, 150));
		qubble.QubjectHasMoved(100110, new Point(550, 350));
		qubble.QubjectHasMoved(100111, new Point(760, 550));
		qubble.QubjectHasMoved(101000, new Point(950, 150));
		
		long i = 0;
		while(i<=500000000){
			i++;
		}
		
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void terminate() {
		
	}

	@Override
	public HashMap<Integer, Point> qubjectDetected() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<Integer, Integer> qubjectTranslation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<Integer, Integer> qubjectRotation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Integer> qubjectRemoved() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
