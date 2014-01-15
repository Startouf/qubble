package audio2;

public class audioThread extends Thread {

	private boolean running;
	private Recorder recorder;
	
	public audioThread(Recorder recorder) {
		running = false;
		this.recorder = recorder;
	}

	public void stopRecord() {
		running = false;
	}

	@Override
	public void run() {
		running = true;
		
		recorder.start();
		
		while (running) {}
	}
}
