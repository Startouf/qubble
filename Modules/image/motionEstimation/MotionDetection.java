package motionEstimation;

import imageObject.Point;
import image_GUI.Window;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.color.CMMException;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import main.ImageDetection;
import main.ImageDetectionInterface;
import main.TerminateThread;


public class MotionDetection implements Runnable, TerminateThread{
	private Window motionWindow;
	private boolean run, pause, firstTime;
	private MotionAnalyser motionAnalyse;
	private ImageDetectionInterface controlImage;
	// Liste des blocks à analyser
	HashMap<Integer, Block> listBlock;
	
	public static int SQUARESIZE = 100;
	
	// Varaible de mémorisation de l'analyse
	private int imageHeight, imageWidth;
	BufferedImage ref;
	BufferedImage cur;
	
	public MotionDetection(ImageDetectionInterface controlImage){
		this.controlImage = controlImage;
		// Bloquer le démarrage tant que au moins deux images n'ont pas été récupérés
		firstTime = true;
		pause = true;
		run = true;
		listBlock = new HashMap<Integer, Block>();
		motionAnalyse = new MotionAnalyser(new BlockMatching(SQUARESIZE,SQUARESIZE, SQUARESIZE*3, 0));
		imageWidth = controlImage.getWidthCamera();
		imageHeight = controlImage.getHeightCamera();
	}
	
	public void run() {	
		while(run){
			// Attente d'une nouvelle image
			while(!controlImage.isNewImageMotion() || pause || firstTime){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(controlImage.isNewImageMotion()){
					motionAnalyse.setNewImage(controlImage.getLastImage());
					firstTime = false;
				}
			}			
			// Mise à jour de la liste des blocks à suivre
			HashMap<Integer, Point> temp = controlImage.getAddedQubbleList();
			for(int id : temp.keySet()){
				addQubbleToList(temp.get(id).getX(), temp.get(id).getY(), id);
			}
			controlImage.resetAddedQubbleList();
			for(int id : controlImage.getRemovedQubbleList().keySet()){
				removeQubbleToList(id);
			}
			controlImage.resetRemovedQubbleList();
			
			// Récupération du mouvement
			if(controlImage.isNewImageMotion()){
				motionAnalyse.setNewImage(controlImage.getLastImage());
				
				analyseTable();
			}
			try {
				Thread.sleep(10);
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
	public void analyseTable(){
		// Démarre la détection de mouvement
		long startTime = System.currentTimeMillis();
		
		motionAnalyse.searchMotion(listBlock.values());

		long endTime = System.currentTimeMillis();
		if(ImageDetection.PRINTDEBUG)
			System.out.println("Temps de calcul du Block Matching : " + (endTime-startTime) + " ms.");
	
		controlImage.setMotionEstimationDone(true);
	}

	public void terminate() {
	
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
	public void addQubbleToList(int positionX, int positionY,int id){
		// Création du cadre/Block qui englobe le Qr code
		listBlock.put(id, new Block(positionX, positionY, imageWidth, imageHeight, SQUARESIZE, SQUARESIZE));
	}
	
	public void removeQubbleToList(int id){
		int save = -1;
		for(Integer num : listBlock.keySet()){
			if(num == id){
				save = id;
			}
		}
		if(save >= 0){
			listBlock.remove(save);
		}
	}
	
	/**
	 * Dessine le contour des blocks sur l'image en paramètre
	 * @param camera
	 */
	public void addMotionOnImage(BufferedImage camera){
		if(camera != null){
			Graphics g = camera.getGraphics();
			g.setColor(Color.green);
			for(Block bl : listBlock.values()){
				g.drawRect(bl.getxCorner(), bl.getyCorner(), bl.getWidth(), bl.getHeight());
			}
		}
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
