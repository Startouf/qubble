package main;



import imageObject.Point;
import image_GUI.Window;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import motionEstimation.MotionEstimation;
import qrDetection.QR_Detection;
import sequencer.QubbleInterface;
import camera.Camera;


public class ImageDetection implements Runnable, ImageDetectionInterface{
	
	private QubbleInterface qubble;	
	
	private Thread t_webcam, t_qr, t_mo;
	private Camera webcam;
	private QR_Detection qr;
	private MotionEstimation mo;
	
	private BufferedImage lastImage;
	private volatile boolean newImageQR, newImageMotion, newImage;
	private boolean qrDetectionDone, motionEstimationDone;
	
	// Gestion de l'interface graphique de suivi
	private Window window;
	
	// Liste actuelle des cubes détectés
	private volatile HashMap<Integer, Point> qubbleList;
	// Pour le mouvement
	// Liste des cubes supprimés depuis la dernière capture
	private volatile HashMap<Integer, Point> removedQubbleList;
	// Liste des cubes ajoutés depuis la dernière capture
	private volatile HashMap<Integer, Point> addedQubbleList;
	
	private volatile boolean run;
	
	
	public ImageDetection(QubbleInterface qubble){
		this.qubble = qubble; 
		webcam = new Camera(this);
		qr = new QR_Detection(this);
		mo = new MotionEstimation(this);
		t_webcam = new Thread(webcam);
		t_qr = new Thread(qr);
		t_mo = new Thread(mo);
		
		qubbleList = new HashMap<Integer, Point>();
		removedQubbleList = new HashMap<Integer, Point>();
		addedQubbleList = new HashMap<Integer, Point>();
		addedQubbleList.put(888, new Point(640, 360));
		if(true){
			window = new Window(qr, mo, 5, 42 ,80);
		}
		
		mo.addQubbleToList(640, 360, 22);
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
			if(motionEstimationDone){
				motionEstimationDone();
				motionEstimationDone = false;
			}
			try {
				Thread.sleep(10);
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
	public void setImage(BufferedImage Image) {
		lastImage = Image;
		window.displayCamera(lastImage);
		newImage = newImageQR = newImageMotion = true;
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
				removedQubbleList.put(id, qubbleList.get(id));
			}else{
				// Le qubject est déjà détecté, on le garde dans la future liste
				temp.put(id, qrFound.get(id));
				// On l'enlève de la liste des qr détectés
				qrFound.remove(id);
			}
				
		}
		// On rajoute les éléments restants et on alerte le processus principale
		for(int idNew : qrFound.keySet()){
			Point pt = qrFound.get(idNew);
			qubble.QubjectDetected(idNew, pt);
			temp.put(idNew, pt);
			addedQubbleList.put(idNew, pt);
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

	public HashMap<Integer, Point> getRemovedQubbleList() {
		return removedQubbleList;
	}

	public HashMap<Integer, Point> getAddedQubbleList() {
		return addedQubbleList;
	}
	
	public void resetRemovedQubbleList() {
		removedQubbleList.clear();
	}

	public void resetAddedQubbleList() {
		addedQubbleList.clear();
	}
	
	public int getWidthCamera(){
		return webcam.IMAGEWIDTH;
	}
	
	public int getHeightCamera(){
		return webcam.IMAGEHEIGHT;
	}

}
