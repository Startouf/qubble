package qrDetection;

import imageObject.ConnexeComponent;
import imageObject.Point;
import imageTransform.TabImage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

/**
 * Prend en entrée une image binarisée
 * Enumération les différentes composantes connexes de l'image
 * @author masseran
 *
 */
public class ComponentsAnalyser {
	
	private int imageHeight, imageWidth;
	private ArrayList<ConnexeComponent> listCC;
	private TabImage image;
	
	/**
	 * Analyse l'image pour récupérer les composantes connexes
	 * @param binaryImage
	 */
	public ComponentsAnalyser(TabImage binaryImage){
		
		imageHeight = binaryImage.getHeight();
		imageWidth = binaryImage.getWidth();
		
		// Tableau de référence
		int[][] labels = new int[imageWidth][imageHeight];
		
		int nbLabels = 1;
		
		//premiere case i=0, j=0
		if (binaryImage.getRGB(0, 0) == new Color(0,0,0).getRGB()) {
			labels[0][0] = nbLabels;
			nbLabels ++;
		}
		
		//premiere colonne i=0
		for (int j=1 ; j < imageHeight ; j++) {
			if (binaryImage.getRGB(0, j) == new Color(0,0,0).getRGB()){
				if(labels[0][j-1] == 0) {
					labels[0][j] = 0;
					nbLabels ++;
				}else{
					labels[0][j] = labels[0][j-1];
				}
			}
		}
		
		//premiere ligne j=0
		for (int i=1 ; i < imageWidth ; i++) {
			if (binaryImage.getRGB(i, 0) == new Color(0,0,0).getRGB()){
				if(labels[i-1][0] == 0) {
					labels[i][0] = nbLabels;
					nbLabels ++;
				}else{
					labels[i][0] = labels [i-1][0];
				}
			}
		}

		//Reste du tableau
		for (int j=1 ; j<imageHeight ; j++){
			for (int i=1; i<imageWidth ; i++) {
				if (binaryImage.getRGB(i, j) == new Color(0,0,0).getRGB()){
					if(labels[i][j-1] == 0 && labels[i-1][j] == 0) {
						labels[i][j] = nbLabels;
						nbLabels ++;
					}else if (labels[i][j-1] > 0 && labels[i-1][j] == 0){
						labels[i][j] = labels[i][j-1];
					}else if (labels[i][j-1] == 0 && labels[i-1][j] > 0){
						labels[i][j] = labels[i-1][j];
						
					}else if (labels[i][j-1] > 0 && labels[i-1][j] > 0){
						labels[i][j] = Math.min(labels[i-1][j], labels[i][j-1]);
					}
				
				}	
				
			}
		}
		
		// Parcours de l'image de haut en bas puis de bas en haut tant qu'il y a des fusions de composantes connexes
		// Je néglige les bords de l'image x = 0 ou y = 0
		boolean modif = true;
		int labelSave = 0;
		int x = 0;
		
		while(modif){
			modif = false;
			x++;
			for (int j=imageHeight-2 ; j >= 0 ; j--){
				for (int i=imageWidth-2; i >= 0 ; i--) {
					labelSave = labels[i][j];
					if (binaryImage.getRGB(i, j) == new Color(0,0,0).getRGB()){
						if (labels[i][j+1] != 0 && labels[i+1][j] == 0){
							labels[i][j] = Math.min(labels[i][j+1], labels[i][j]);
							
						}else if (labels[i][j+1] == 0 && labels[i+1][j] != 0){
							labels[i][j] = Math.min(labels[i+1][j], labels[i][j]);
							
						}else if (labels[i][j+1] != 0 && labels[i+1][j] != 0){
							labels[i][j] = Math.min(labels[i][j],Math.min(labels[i+1][j], labels[i][j+1]));
						}
					}
					if(labelSave != labels[i][j])
						modif = true;					
				}
			}
			
			
			if(modif){
				for (int j= 1 ; j < imageHeight ; j++){
					for (int i= 1; i < imageWidth ; i++) {
						labelSave = labels[i][j];
						if (binaryImage.getRGB(i, j) == new Color(0,0,0).getRGB()){
							if (labels[i][j-1] != 0 && labels[i-1][j] == 0){
								labels[i][j] = Math.min(labels[i][j-1], labels[i][j]);
								
							}else if (labels[i][j-1] == 0 && labels[i-1][j] != 0){
								labels[i][j] = Math.min(labels[i-1][j], labels[i][j]);
								
							}else if (labels[i][j-1] != 0 && labels[i-1][j] != 0){
								labels[i][j] = Math.min(labels[i][j],Math.min(labels[i-1][j], labels[i][j-1]));
							}
						}	
						if(labelSave != labels[i][j])
							modif = true;	
					}
				}
			}
				
		}
		// Affichage du nombre de parcours
		System.out.println("Nombre de parcours dans la recherche des composantes connexes : " + x);
		
		HashMap<Integer, ConnexeComponent> component = new HashMap<Integer, ConnexeComponent>();
		/**
		 * Création des différents ensembles connexes grâce à l'indice
		 */
		for (int i=0; i<imageWidth ; i++) {
			for (int j=0 ; j<imageHeight ; j++){
				if(labels[i][j] != 0){
					if(!component.containsKey(labels[i][j])){
						component.put(labels[i][j], new ConnexeComponent());
					}
					component.get(labels[i][j]).addPoint(new Point(i, j));
				}
				
			}
		}
		
		listCC = new ArrayList<ConnexeComponent>();
		listCC.addAll(component.values());
	
	}	

		
	
	
	/**
	 *  Créer une image avec chaque composante connexe d'une autre couleur
	 * @return
	 */
	public BufferedImage getCCMyImage() {
		BufferedImage CCMyImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
		Color compoColor = null;
		
		// Affichage d'un fond blanc
		Graphics g = CCMyImage.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, imageWidth, imageHeight);
		
		
		for (ConnexeComponent listPoint : listCC) {
			compoColor = new Color ((int) (Math.random()*255), ((int) Math.random()*255), (int) (Math.random()*255) );
			
			// Affichage des coins des composantes connexes
			/*for(int i = 0 ; i < 4 ; i++){
				g.fillRect(listPoint.getCorner(i).getX()-1, listPoint.getCorner(i).getY()-1, 2, 2);
			}*/
			
			// Affichages des composantes connexes par couleurs
			if(listPoint.getConnexePoints().size() > 100){
				for (Point pixel : listPoint.getConnexePoints()) {
						if(CCMyImage.getRGB(pixel.getX(), pixel.getY()) == Color.WHITE.getRGB())
							CCMyImage.setRGB(pixel.getX(), pixel.getY(), compoColor.getRGB());
				}
				
			}
			
			if(listPoint.getConnexePoints().size()> 100){
				g.setColor(Color.green);
				g.fillRect(listPoint.getCorner(0).getX()-4, listPoint.getCorner(0).getY()-4, 8, 8);
				
				g.setColor(Color.green);
				g.fillRect(listPoint.getxCenter()-4, listPoint.getyCenter()-4, 8, 8);
				
			}
		}
		
		return CCMyImage;
	} 

	
	public ArrayList<ConnexeComponent> getCClist(){
		return listCC;
		
	}
}
