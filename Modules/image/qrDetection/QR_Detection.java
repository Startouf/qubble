package qrDetection;

import imageObject.ConnexeComponent;
import imageObject.QRCode;
import imageTransform.TabImage;
import image_GUI.Window;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import main.ImageDetection;
import main.ImageDetectionInterface;
import main.TerminateThread;


public class QR_Detection implements Runnable, TerminateThread{
	public static int VAR_WINDOW = 3, VAR_TRESH = 5;
	private Window qrWindow;
	private boolean run, pause;
	private ImageDetectionInterface controlImage;
	
	// Varaible de mémorisation de l'analyse
	BufferedImage lastDetection;
	ComponentsAnalyser compo;
	QRCodesAnalyser qrAnal;
	TabImage grey, variance;
	
	
	public QR_Detection(ImageDetectionInterface controlImage){
		this.controlImage = controlImage;
		run = true;
		pause = true;
	}
	
	public void run() {
		while(run){
			// Attente d'une nouvelle image
			while(!controlImage.isNewImageQR() || pause){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(controlImage.isNewImageQR()){
				analyseTable(new TabImage(controlImage.getLastImage()));
			}
			
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	/**
	 * Recherche tous les QR codes parmis screen 
	 * @param screen
	 *
	 */
	public void analyseTable(TabImage camera){
		long startTime = System.currentTimeMillis();
		
		// Transformation en niveau de gris
		grey = camera.getGrey(true);
		long greyTime = System.currentTimeMillis();
		
		// Transformation par le filtre de variance
		variance = grey.getVarianceFilter(VAR_WINDOW, VAR_TRESH);
		long binaryTime = System.currentTimeMillis();
		
		// Recherche des composantes connexes
		compo = new ComponentsAnalyser(variance);
		long componentTime = System.currentTimeMillis();
		
		// Récupération de la valeur des qr
		qrAnal = new QRCodesAnalyser(grey, variance, compo);
		lastDetection = qrAnal.printValeur(camera);
		
		// Ecriture des valeurs des signatures dans un fichier texte
		if(ImageDetection.PRINTDEBUG){
			int i = 1;
			for(QRCode qr : qrAnal.getListQRcode()){
				try {
					PrintWriter out  = new PrintWriter(new FileWriter("signature" + i + ".txt", false));
					float[] courbe = qr.getBorder().getCourbe();
					float[] aliasing = qr.getBorder().getAliasing();
					int dephasage = qr.getBorder().getBestAngle();
					float[] perfect = ConnexeComponent.perfectSquare;
					out.println("COURBE ## PERFECT ## CALCUL");
					for(int j = 0; j<180; j++){
						out.println(courbe[(j+dephasage)%180] + " ## " + perfect[(j*2)%90] + " ## " + aliasing[j]);
					}
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				i++;
				
			}
		}
		
		long qrTime = System.currentTimeMillis();
					
		long endTime = System.currentTimeMillis();
		
	/*
	 	if(ImageDetection.PRINTDEBUG){
		 	System.out.println("Temps de calcul de la transformation en niveau de gris : " + (greyTime-startTime) + " ms.");
			System.out.println("Temps de calcul de la transformation en binaire : " + (binaryTime-greyTime) + " ms.");
			System.out.println("Temps de calcul pour trouver les composantes connexes: " + (componentTime-binaryTime) + " ms.");
			System.out.println("Temps de calcul pour trouver le qr code : " + (qrTime-componentTime) + " ms.");
			System.out.println("Temps de calcul de la reconnaissance : " + (endTime-startTime) + " ms.");
		}
		*/
		controlImage.setQrDetectionDone(true);
	}

	public void terminate() {
		
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
	
	/**
	 * Renvoie une image avec la signature des qrs détectés
	 * @return
	 */
	public BufferedImage getPattern(){
		return qrAnal.getPatternImage();
	}
	
	public boolean switchPause(){
		if(pause){
			pause = false;
		}else{
			pause = true;
		}
		return pause;
	}

}
