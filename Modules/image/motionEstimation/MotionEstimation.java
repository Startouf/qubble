package qrDetection;

import image_GUI.Window;

import java.awt.image.BufferedImage;

import main.ImageDetectionInterface;
import main.TerminateThread;


public class MotionEstimation implements Runnable, TerminateThread{
	private Window motionWindow;
	private boolean windowMode, run;
	private ImageDetectionInterface controlImage;
	
	// Varaible de mémorisation de l'analyse
	BufferedImage ref;
	BufferedImage cur;
	
	public MotionEstimation(ImageDetectionInterface controlImage, boolean windowMode){
		this.controlImage = controlImage;
		this.windowMode = windowMode;
		run = true;
		if(windowMode){
			motionWindow = new Window(this);
		}
	}
	
	public void run() {
		while(run){
			System.out.println("Essai");
			// Attente d'une nouvelle image
			while(!controlImage.isNewImageMotion()){
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(controlImage.isNewImageMotion()){
				ref = cur;
				cur = controlImage.getLastImage();
				
				analyseTable(cur, ref);
				
				//controlImage.setQrDetectionDone(true);
				
			}
		}
	}
	
	
	
	/**
	 * Recherche tous les QR codes parmis screen 
	 * @param screen
	 *
	 */
	public void analyseTable(BufferedImage cur, BufferedImage ref){
		long startTime = System.currentTimeMillis();
		
		
					
		long endTime = System.currentTimeMillis();
		
		
		
		controlImage.setMotionEstimationDone(true);
	}

	public void terminate() {
		if(windowMode){
			motionWindow.setVisible(false);
		}
		
	}

	public BufferedImage getRef() {
		return ref;
	}

	public BufferedImage getCur() {
		return cur;
	}
	
	

	


}
