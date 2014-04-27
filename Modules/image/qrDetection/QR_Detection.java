package qrDetection;

import imageObject.Point;
import imageTransform.TabImage;
import image_GUI.Window;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

import camera.ImageDetectionInterface;

import sequencer.QubbleInterface;

import main.TerminateThread;


public class QR_Detection implements Runnable, TerminateThread{
	private Window qrWindow;
	private boolean windowMode, run;
	private ImageDetectionInterface controlImage;
	
	// Varaible de mémorisation de l'analyse
	BufferedImage camera;
	ComponentsAnalyser compo;
	QRCodesAnalyser qrAnal;
	TabImage grey, variance;
	
	public QR_Detection(ImageDetectionInterface controlImage, boolean windowMode){
		this.controlImage = controlImage;
		this.windowMode = windowMode;
		run = true;
		if(windowMode){
			qrWindow = new Window(this, 5, 42, (float)0.80);
		}
	}
	
	public void run() {
		while(run){
			// Attente d'une nouvelle image
			while(!controlImage.isNewImageQR()){}
			if(controlImage.isNewImageQR()){
				
				camera = controlImage.getLastImage();
				
				long startTime = System.currentTimeMillis();
				
				// Transformation en niveau de gris
				grey = (new TabImage(camera)).getGrey();
				long greyTime = System.currentTimeMillis();
				
				// Transformation par le filtre de variance
				variance = grey.getVarianceFilter(3, 5);
				long binaryTime = System.currentTimeMillis();
				
				// Recherche des composantes connexes
				compo = new ComponentsAnalyser(variance);
				long componentTime = System.currentTimeMillis();
				
				// Récupération de la valeur des qr
				qrAnal = new QRCodesAnalyser(grey, variance, compo);
				qrAnal.getValeur(camera);
				
				long qrTime = System.currentTimeMillis();
							
				long endTime = System.currentTimeMillis();
				
				System.out.println("Temps de calcul de la transformation en niveau de gris : " + (greyTime-startTime) + " ms.");
				System.out.println("Temps de calcul de la transformation en binaire : " + (binaryTime-greyTime) + " ms.");
				System.out.println("Temps de calcul pour trouver les composantes connexes: " + (componentTime-binaryTime) + " ms.");
				System.out.println("Temps de calcul pour trouver le qr code : " + (qrTime-componentTime) + " ms.");
				System.out.println("Temps de calcul de la reconnaissance : " + (endTime-startTime) + " ms.");
			}
		}
	}
	
	
	
	/**
	 * Recherche tous les QR codes parmis screen 
	 * @param screen
	 * @return une hashmap avec l'id du qr code et sa position sur la table?image
	 */
	public static HashMap<Integer, Point> getQRcodes(BufferedImage screen){
		HashMap<Integer, Point> finalQRcodes = new HashMap<Integer, Point>();
		
		return finalQRcodes;
	}

	public void terminate() {
		if(windowMode){
			qrWindow.setVisible(false);
		}
		
	}

	public BufferedImage getCamera() {
		return camera;
	}

	public ComponentsAnalyser getCompo() {
		return compo;
	}

	public QRCodesAnalyser getQrAnal() {
		return qrAnal;
	}

	public TabImage getGrey() {
		return grey;
	}

	public TabImage getVariance() {
		return variance;
	}
	
	



}
