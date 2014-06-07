package opengl;

import org.lwjgl.util.Point;

import qubject.AnimationInterface;
import qubject.QRInterface;

public class FakeProjector implements OutputImageInterface, Runnable {

	private volatile boolean stop = false;
	
	@Override
	public void toggleGrid() {
		// TODO Auto-generated method stub

	}

	@Override
	public void trackQubject(QRInterface qubject) {
		// TODO Auto-generated method stub

	}

	@Override
	public void stopTrackingQubject(QRInterface qubject) {
		// TODO Auto-generated method stub

	}

	@Override
	public void highlightQubject(Point qubject) {
		// TODO Auto-generated method stub

	}

	@Override
	public void triggerEffect(Point coords,
			AnimationInterface animationWhenPlayed) {
		// TODO Auto-generated method stub

	}

	@Override
	public void triggerOtherEffect(AnimationInterface anim) {
		// TODO Auto-generated method stub

	}

	@Override
	public void playPause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void terminate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		
	}

	@Override
	public void resynchronize(float currentTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public synchronized void run() {
		while(!stop){
			try {
				wait(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
