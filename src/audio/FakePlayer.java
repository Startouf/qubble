package audio;

import java.io.File;

import qubject.SampleInterface;
import sequencer.QubbleInterface;

public class FakePlayer implements PlayerInterface, Runnable{

	QubbleInterface qubble;
	
	public FakePlayer(QubbleInterface qubble) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public synchronized void run() {
		try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public SampleControllerInterface playSample(SampleInterface sample) {
		return null;
	}

	@Override
	public void tweakSample(SampleControllerInterface ref, EffectType effect,
			int amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playPause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopAllSounds() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startRecording(File file) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopRecording() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mute() {
		// TODO Auto-generated method stub
		
	}

}
