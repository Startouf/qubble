package qrDetection;

import imageObject.Point;
import imageTransform.TabImage;
import image_GUI.Window;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;


import sequencer.QubbleInterface;

import main.ImageDetectionInterface;
import main.TerminateThread;


public class QR_Detection implements Runnable, TerminateThread{
	private Window qrWindow;
	private boolean windowMode, run;
	private ImageDetectionInterface controlImage;
	
	// Varaible de mémorisation de l'analyse
	BufferedImage lastDetection;
	ComponentsAnalyser compo;
	QRCodesAnalyser qrAnal;
	TabImage grey, variance;
	
	public QR_Detection(ImageDetectionInterface controlImage){
		this.controlImage = controlImage;
		run = true;
	}
	
	public void run() {
		while(run){
			System.out.println("Essai");
			// Attente d'une nouvelle image
			while(!controlImage.isNewImageQR()){
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(controlImage.isNewImageQR()){
				
				lastDetection = controlImage.getLastImage();
				
				analyseTable(lastDetection);
				
				//controlImage.setQrDetectionDone(true);
				
			}
		}
	}
	
	
	
	/**
	 * Recherche tous les QR codes parmis screen 
	 * @param screen
	 *
	 */
	public void analyseTable(BufferedImage camera){
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
		qrAnal.printValeur(camera);
		
		long qrTime = System.currentTimeMillis();
					
		long endTime = System.currentTimeMillis();
		
		System.out.println("Temps de calcul de la transformation en niveau de gris : " + (greyTime-startTime) + " ms.");
		System.out.println("Temps de calcul de la transformation en binaire : " + (binaryTime-greyTime) + " ms.");
		System.out.println("Temps de calcul pour trouver les composantes connexes: " + (componentTime-binaryTime) + " ms.");
		System.out.println("Temps de calcul pour trouver le qr code : " + (qrTime-componentTime) + " ms.");
		System.out.println("Temps de calcul de la reconnaissance : " + (endTime-startTime) + " ms.");
		
		controlImage.setQrDetectionDone(true);
	}

	public void terminate() {
		if(windowMode){
			qrWindow.setVisible(false);
		}
		
	}

	public BufferedImage getLastDetection() {
		return lastDetection;
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
