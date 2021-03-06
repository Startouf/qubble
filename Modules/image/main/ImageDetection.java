package main;



import imageObject.Point;
import image_GUI.Window;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import org.omg.CORBA._PolicyStub;

import motionEstimation.MotionDetection;
import qrDetection.QR_Detection;
import sequencer.QubbleInterface;
import camera.Camera;


public class ImageDetection implements Runnable, ImageDetectionInterface{
	
	private QubbleInterface qubble;	
	
	private Thread t_webcam, t_qr, t_mo;
	private Camera webcam;
	private QR_Detection qr;
	private MotionDetection mo;
	
	private BufferedImage lastImage;
	private volatile boolean newImageQR, newImageMotion, newImage;
	private boolean qrDetectionDone, motionEstimationDone;
	public static boolean GUI = Camera.GUI;
	public final static boolean PRINTDEBUG = false;
	
	// Gestion de l'interface graphique de suivi
	private static Window window;
	
	// Liste actuelle des cubes détectés
	private volatile HashMap<Integer, Point> qubbleList;
	// Pour le mouvement
	private volatile HashMap<Integer, Integer> countBeforeRemoved;
	
	private volatile boolean run;
	
	
	public ImageDetection(QubbleInterface qubble){
		
		this.qubble = qubble; 
		webcam = new Camera(this);
		qr = new QR_Detection(this);
		//mo = new MotionDetection(this);
		t_webcam = new Thread(webcam);
		t_qr = new Thread(qr);
		//t_mo = new Thread(mo);
		
		qubbleList = new HashMap<Integer, Point>();
		countBeforeRemoved  = new HashMap<Integer, Integer>();
		//addedQubbleList.put(888, new Point(640, 360));
		if(GUI){
			window = new Window(this,qr, mo, 60, 42 ,80, 3, 5, 0);
		}
	
		run = true;
	}
	
	/**
	 * @param args
	 */
	public void run() {
		t_webcam.start();
		t_qr.start();
		//t_mo.start();
		
		while(run){
			// Actualiser la liste des QR codes
			if(qrDetectionDone){
				qrDetectionDone();
				if(GUI){
					window.displayQRDetection(qr.getLastDetection(), qr.getGrey(), qr.getVariance(), qr.getCompo(), qr.getQrAnal(), qr.getPattern());
				}
				qrDetectionDone = false;
			}
//			if(motionEstimationDone){
//				motionEstimationDone();
//				motionEstimationDone = false;
//			}
			try {
				Thread.sleep(100);
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
		//mo.terminate();
		run =  false;
	}

	@Override
	public void setImage(BufferedImage Image) {
		lastImage = Image;
		if(GUI){
			window.displayCamera(lastImage);
		}
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
		Point pt, pt2;
		// Ajout des nouveaux qubjects détectés
		for(int id : qubbleList.keySet()){
			// Alerter de la suppression des qubjects
			if(qrFound.containsKey(id) == false){
				//S'il a déjà été détecté comme supprimé
				if(countBeforeRemoved.containsKey(id)){
					// Diminuer de 1 le compteur
					int tempCont = (int)(countBeforeRemoved.get(id))-1;
					// Supprime définitevement le qr
					if(tempCont <= 0){
						System.out.println("######### Remove " + id + " #########");
						countBeforeRemoved.remove(id);
						qubble.QubjectRemoved(id);
						// Actualise la valeur
					}else
						countBeforeRemoved.put(id, new Integer(tempCont));
				}else{
					// Indique le nombre de "vies" restantes
					countBeforeRemoved.put(id, new Integer(3));
				}
				// ????
//				removedQubbleList.put(id, qubbleList.get(id));
//				System.out.println("######### Remove " + id + " #########");
//				qubble.QubjectRemoved(id);
			}else{
				// S'il avait été perdu précedemment, on annule le compteur de suppression
				if(countBeforeRemoved.containsKey(id)){
					countBeforeRemoved.remove(id);
				}
				// Le qubject est déjà détecté, on le garde dans la future liste
				temp.put(id, qrFound.get(id));
				
				// Détection du mouvement
				pt = qrFound.get(id);
				pt2 = qubbleList.get(id);
				if(pt != null && pt2 != null){
					if(Math.abs(pt.getX()-pt2.getX()) > 10 || Math.abs(pt.getY()-pt2.getY()) > 10){
						System.out.println("######### Move " + id + " #########");
						qubble.QubjectHasMoved(id, qrFound.get(id));
				}
			}
				// On l'enlève de la liste des qr détectés
				qrFound.remove(id);
			}
		}
		// On rajoute les éléments restants et on alerte le processus principale
		for(int idNew : qrFound.keySet()){
			pt = qrFound.get(idNew);
			qubble.QubjectDetected(idNew, pt);
			temp.put(idNew, pt);
			System.out.println("######### Add " + idNew + " #########");
		}
		// On garde en mémoire ceux en passe de mourir
		for(int idNew : countBeforeRemoved.keySet()){
			pt = qrFound.get(idNew);
			temp.put(idNew, pt);
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
	
	public int getWidthCamera(){
		return webcam.IMAGEWIDTH;
	}
	
	public int getHeightCamera(){
		return webcam.IMAGEHEIGHT;
	}

	@Override
	public void switchCamera() {
		webcam.switchPause();
	}

	@Override
	public void switchDetection() {
		qr.switchPause();
	}

	@Override
	public void switchMotion() {
		mo.switchPause();
	}

	@Override
	public void closeGUI() {
		if(window != null){
			window.dispose();
		}
	}

}