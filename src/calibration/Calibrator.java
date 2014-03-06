package calibration;

public class Calibrator implements Runnable{

	private Thread image, camera;
	
	public Calibrator(){
		image = new Thread(new CalibrationProjection());
		image.start();
		
		//TODO : start a thread camera
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
