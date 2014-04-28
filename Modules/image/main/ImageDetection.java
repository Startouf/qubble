package main;



import imageObject.Point;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import motionEstimation.MotionEstimation;
import qrDetection.QR_Detection;
import sequencer.QubbleInterface;
import camera.Camera;


public class ImageDetection implements Runnable, ImageDetectionInterface, TerminateThread{
	
	private QubbleInterface qubble;	
	
	private Thread t_webcam, t_qr, t_mo;
	private Camera webcam;
	private QR_Detection qr;
	private MotionEstimation mo;
	
	private BufferedImage lastImage;
	private volatile boolean newImageQR, newImageMotion;
	private boolean qrDetectionDone, motionEstimationDone;
	
	// Liste actuelle des cubes détectés
	private volatile HashMap<Integer, Point> qubbleList;
	
	private volatile boolean run;
	
	
	public ImageDetection(QubbleInterface qubble){
		this.qubble = qubble; 
		webcam = new Camera(this);
		qr = new QR_Detection(this, true);
		mo = new MotionEstimation(this, false);
		t_webcam = new Thread(webcam);
		t_qr = new Thread(qr);
		t_mo = new Thread(mo);
		
		qubbleList = new HashMap<Integer, Point>();
		
		run = true;
	}
	
	/**
	 * @param args
	 */
	public void run() {
		t_webcam.start();
		t_qr.start();
		t_mo.start();
		
		while(run){
			// Actualiser la liste des QR codes
			if(qrDetectionDone){
				qrDetectionDone();
				qrDetectionDone = false;
			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	
	public void setNewImageQR(boolean newImageQR) {
		this.newImageQR = newImageQR;
	}

	@Override
	public boolean isNewImageMotion() {
		return newImageMotion;
	}
	
	@Override
	public void qrDetectionDone(){
		
		HashMap<Integer, Point> qrFound = qr.getQrAnal().getQRList();
		HashMap<Integer, Point> temp = new HashMap<Integer, Point>();
		
		// Ajout des nouveaux qubjects détectés
		for(int id : qubbleList.keySet()){
			// Alerter de la suppression des qubjects
			if(qrFound.containsKey(id) == false){
				qubble.QubjectRemoved(id);
			}else{
				// Le qubject est déjà détecté, on le garde dans la future liste
				temp.put(id, qrFound.get(id));
				// On l'enlève de la liste des qr détectés
				qrFound.remove(id);
			}
				
		}
		// On rajoute les éléments restants et on alerte le processus principale
		for(int idNew : qrFound.keySet()){
			qubble.QubjectDetected(idNew, qrFound.get(idNew));
			temp.put(idNew, qrFound.get(idNew));
		}
		
		qubbleList = temp;
		
	}
 
	@Override
	public void motionEstimationDone() {
		
	}
	
	@Override
	public void setQrDetectionDone(boolean qrDetectionDone) {
		this.qrDetectionDone = qrDetectionDone;
	}

	@Override
	public void setMotionEstimationDone(boolean motionEstimationDone) {
		this.motionEstimationDone = motionEstimationDone;
	}
	
	

}
