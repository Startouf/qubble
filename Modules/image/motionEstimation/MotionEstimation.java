package motionEstimation;

import image_GUI.Window;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.ImageDetectionInterface;
import main.TerminateThread;


public class MotionEstimation implements Runnable, TerminateThread{
	private Window motionWindow;
	private boolean windowMode, run;
	private MotionDetection motionAnalyse;
	private ImageDetectionInterface controlImage;
	// Lisye des blocks à analyser
	ArrayList<Block> listBlock;
	
	public static int SQUARESIZE = 52;
	
	// Varaible de mémorisation de l'analyse
	BufferedImage ref;
	BufferedImage cur;
	
	public MotionEstimation(ImageDetectionInterface controlImage){
		this.controlImage = controlImage;
		run = true;
		listBlock = new ArrayList<Block>();
		motionAnalyse = new MotionDetection(new BlockMatching(SQUARESIZE,SQUARESIZE, 8, 0));
	}
	
	public void run() {
		while(run){
			System.out.println("Essai");
			// Attente d'une nouvelle image
			while(!controlImage.isNewImageMotion()){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// Afficher la nouvelle image dans Window
			
			// Mise à jour de la liste des blocks à suivre
			
			
			// Récupération du mouvement
			if(controlImage.isNewImageMotion()){
				ref = cur;
				cur = controlImage.getLastImage();
				
				analyseTable(cur, ref);
			}
		}
	}
	
	
	
	/**
	 * Recherche tous les QR codes parmis screen 
	 * @param screen
	 *
	 */
	public void analyseTable(BufferedImage cur, BufferedImage ref){
		// Démarre la détection de mouvement
		long startTime = System.currentTimeMillis();
		
		motionAnalyse.searchMotion(listBlock);

		long endTime = System.currentTimeMillis();
		System.out.println("Temps de calcul du Block Matching : " + (endTime-startTime) + " ms.");
	
		controlImage.setQrDetectionDone(true);
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
	
	/**
	 * prend en entrée la position du qubble et l'incorpore dans la liste des blocs dont nous voulons déterminer le mouvement
	 * ainsi on ne fait le block matching que sur quelques blocs aulieu de le faire sur tous
	 */
	public void addQubbleToList(int positionX, int positionY){
		// Création du cadre/Block qui englobe le Qr code
		listBlock.add(new Block(positionX, positionY, cur.getWidth(), cur.getHeight(), SQUARESIZE, SQUARESIZE));
	}

	


}
