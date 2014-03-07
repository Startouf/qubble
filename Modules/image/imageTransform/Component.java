package imageTransform;

import imageObject.ConnexeComponent;
import imageObject.Point;

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
 * Enumère les différentes composantes connexes de l'image
 * @author masseran
 *
 */
public class Component {
	
	private int imageHeight, imageWidth;
	private ArrayList<ConnexeComponent> listCC;
	private MyImage image;
	
	/**
	 * Analyse l'image pour récupérer les composantes connexes
	 * @param binaryImage
	 */
	public Component(MyImage binaryImage){
		
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
		
		// Calcul 
		// Je néglige les bords de l'image et la valeur est temporaire
		boolean modif = true;
		int labelSave = 0;
		int x = 0;
		
		while(modif){
			modif = false;
			x++;
			System.out.println(x);
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
	public MyImage getCCMyImage() {
		MyImage CCMyImage = new MyImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
		Color compoColor = null;
		
		// Affichage d'un fond blanc
		Graphics g = CCMyImage.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, imageWidth, imageHeight);
		
		
		for (ConnexeComponent listPoint : listCC) {
			compoColor = new Color ((int) (Math.random()*255), ((int) Math.random()*255), (int) (Math.random()*255) );
			
			
			/* Affichage de l'image ou on ne voit que les pt principaux (bordure  et centre)
			 * g.setColor(compoColor);
			listPoint.getCenter();
			g.fillRect(listPoint.getxMin(), listPoint.getyMin(), 2, 2);
			g.fillRect(listPoint.getxMin(), listPoint.getyMax(), 2, 2);
			g.fillRect(listPoint.getxMax(), listPoint.getyMin(), 2, 2);
			g.fillRect(listPoint.getxMax(), listPoint.getyMax(), 2, 2);
			g.fillRect(listPoint.getxCenter(), listPoint.getyCenter(), 2, 2);
			
			System.out.println("---------");
			System.out.println(listPoint.getxMin());
			System.out.println(listPoint.getxMax());
			System.out.println(listPoint.getyMin());
			System.out.println(listPoint.getyMax());
			System.out.println("---------");*/
			
			for (Point pixel : listPoint.getConnexePoints()) {
					if(CCMyImage.getRGB(pixel.getX(), pixel.getY()) == Color.WHITE.getRGB())
						CCMyImage.setRGB(pixel.getX(), pixel.getY(), compoColor.getRGB());
			}
			
		}
		
		return CCMyImage;
	} 
	
	/**
	 * Retourne la liste des points qui font le contour de la composante
	 * On ne ne l'utilise plus
	 * @return
	 */
	public ArrayList<Point> getContour() {
		ArrayList<Point> contour = new ArrayList<Point>();

		for (int i=0 ; i<image.getHeight(); i++) {
			for (int j = 0 ; j<image.getWidth(); j++) {
				Point p = new Point (i,j);
				Point voisin_bas = new Point (i,j+1);
				Point voisin_haut = new Point (i,j-1);
				Point voisin_droite = new Point (i+1,j);
				Point voisin_gauche = new Point (i-1,j);
				/*
				if (this.containsPoint(p)) {
					if ((this.containsPoint(voisin_haut) && this.containsPoint(voisin_bas)==false)
						|| (this.containsPoint(voisin_haut)==false) && this.containsPoint(voisin_bas)
						|| (this.containsPoint(voisin_droite)==false) && this.containsPoint(voisin_gauche)
						|| (this.containsPoint(voisin_gauche)==false) && this.containsPoint(voisin_droite)) {
						
							contour.add(p);
					}				
				}
				*/
			}
		}
		return contour;
	}
	
	public ArrayList<ConnexeComponent> getCClist(){
		return listCC;
		
	}
}
