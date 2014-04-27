package main;



import imageObject.Point;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import com.googlecode.javacv.FrameGrabber.Array;

import motionEstimation.MotionEstimation;
import qrDetection.QR_Detection;
import sequencer.QubbleInterface;
import camera.Camera;
import camera.ImageDetectionInterface;


public class ImageDetection implements Runnable, ImageDetectionInterface, TerminateThread{
	
	private QubbleInterface qubble;	
	
	private Camera webcam;
	private QR_Detection qr;
	private MotionEstimation mo;
	
	private BufferedImage lastImage;
	private boolean newImageQR, newImageMotion;
	private boolean qrDetectionDone, motionEstimationDone;
	
	// Liste actuelle des cubes détectés
	private volatile HashMap<Integer, Point> qubbleList;
	
	private volatile boolean run;
	
	
	public ImageDetection(QubbleInterface qubble){
		this.qubble = qubble; 
		
		webcam = new Camera(this);
		qr = new QR_Detection(this, true);
		mo = new MotionEstimation(this, true);
		
		run = true;
	}
	
	/**
	 * @param args
	 */
	public void run() {
		webcam.run();
		qr.run();
		mo.run();
		
		while(run){
			
			
		}
	}
	

	@Override
	public void terminate() {
		webcam.terminate();
		qr.terminate();
		mo.terminate();
		run =  false;
	}

	@Override
	public void setImage(BufferedImage newImage) {
		lastImage = newImage;
		newImageQR = newImageMotion = true;
	}

	@Override
	public BufferedImage getLastImage() {
		return lastImage;
	}

	@Override
	public boolean isNewImageQR() {
		return newImageQR;
	}

	@Override
	public boolean isNewImageMotion() {
		return newImageMotion;
	}

	@Override
	public void qrDetectionDone(HashMap<Integer, Point> newQubject, ArrayList<Integer> deletedQubject){
		// Suppression des qubjects
		
		// Ajout des nouveaux qubjects détectés
		
	}

	@Override
	public void motionEstimationDone() {
		
	}


}
