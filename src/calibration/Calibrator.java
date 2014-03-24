package calibration;

import ui.App;

public class Calibrator implements Runnable{

	private Thread image, camera;
	
	public Calibrator(){
		image = new Thread(new CalibrationProjection());
		
		//TODO : start a thread camera
	}
	
	@Override
	public void run() {
		image.start();
		//TODO : start detection thread
		
		try {
			//Should be interrupted by the camera once calibration is done
			wait();
		} catch (InterruptedException e) {
			/**
			 * Calibration done ! Can start the app
			 */
		}
		
		App app = new App();
	}
	
	public static void main(String[] args){
		Calibrator calib = new Calibrator();
	}

}
