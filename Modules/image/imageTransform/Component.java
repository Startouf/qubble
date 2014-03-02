package imageTransform;

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
	private ArrayList<ArrayList<Point>> listComponents;
	private ArrayList<Point> listPoints;
	private MyImage image;
	
	/**
	 * Analyse l'image pour récupérer les composantes connexes
	 * @param binaryImage
	 */
	public Component(MyImage binaryImage){
		
		imageHeight = binaryImage.getHeight();
		imageWidth = binaryImage.getWidth();
		int[][] labels = new int[binaryImage.getHeight()][binaryImage.getWidth()];
		
		int nbLabels = 1;
		HashMap<Integer, ArrayList<Integer>> labelUnion = new HashMap<Integer, ArrayList<Integer>>();
		// Analyse spécifique pour la première ligne et la première colonne car il n'y a pas d'antécédent
		
		//premiere case i=0, j=0
		if (binaryImage.getRGB(0, 0) == new Color(0,0,0).getRGB()) {
			labels[0][0] = nbLabels;
			nbLabels ++;
		}
		
		//premiere ligne i=0
		for (int j=1 ; j < binaryImage.getHeight() ; j++) {
			if (binaryImage.getRGB(0, j) == new Color(0,0,0).getRGB()){
				if(labels[0][j-1] == 0) {
					labels[0][j] = nbLabels;
					nbLabels ++;
				}else{
					labels[0][j] = labels [0][j-1];
				}
			}
		}
		
		//premiere colonne j=0
		for (int i=1 ; i < binaryImage.getWidth() ; i++) {
			if (binaryImage.getRGB(i, 0) == new Color(0,0,0).getRGB()){
				if(labels[i-1][0] == 0) {
					labels[i][0] = nbLabels;
					nbLabels ++;
				}else{
					labels[i][0] = labels [i-1][0];
				}
			}
		}
		/*
		ArrayList<Point> list1 = new ArrayList<Point>();
		ArrayList<Point> list2 = new ArrayList<Point>();
		ArrayList<Point> list3 = new ArrayList<Point>();
		ArrayList<Point> list4 = new ArrayList<Point>();
		*/
		//Reste du tableau
		for (int i=1; i<binaryImage.getWidth() ; i++) {
			for (int j=1 ; j<binaryImage.getHeight() ; j++){
				if (binaryImage.getRGB(i, j) == new Color(0,0,0).getRGB()){
					// Pas d'antécédent
					if(labels[i][j-1] == 0 && labels[i-1][j] ==0){
						labels[i][j] = nbLabels;
						nbLabels ++;
						//list1.add(new Point(i,j));
					// Antécédent au-dessus
					}else if(labels[i][j-1] != 0 && labels[i-1][j] ==0){
						labels[i][j] = labels [i][j-1];
						//list2.add(new Point(i,j));
					// Antécédent à gauche
					}else if(labels[i][j-1] == 0 && labels[i-1][j] !=0){
						labels[i][j] = labels[i-1][j];
						//list3.add(new Point(i,j));
					// Antécédent à gauche et au-dessus
					}else if(labels[i][j-1] != 0 && labels[i-1][j] !=0){
						labels[i][j] = Math.min(labels[i][j-1], labels[i-1][j]);
						//list4.add(new Point(i,j));
						if(labels[i][j-1] != labels[i-1][j]){
							if(!labelUnion.containsKey(labels[i][j]))
								labelUnion.put(labels[i][j], new ArrayList<Integer>());
							if(!labelUnion.get(labels[i][j]).contains(Math.max(labels[i][j-1], labels [i-1][j])))
								labelUnion.get(labels[i][j]).add(Math.max(labels[i][j-1], labels [i-1][j]));
							
						}
							
					}
					
				}
			}
		}
		
		// Création des listes de points par composante connexe
		HashMap<Integer, ArrayList<Point>> component = new HashMap<Integer, ArrayList<Point>>();
		for (int i=0; i<binaryImage.getWidth() ; i++) {
			for (int j=0 ; j<binaryImage.getHeight() ; j++){
				if(labels[i][j] > 0){
					// Il n'y a pas déjà une référence vers une liste de point pour le label
					if(!component.containsKey(labels[i][j]))
						component.put(labels[i][j], new ArrayList<Point>());
					// Ajout d'un point
					component.get(labels[i][j]).add(new Point(i, j));
				}
				
			}
		}
		
		HashMap<Integer, HashSet<Integer>> labelUnion2 = new HashMap<Integer, HashSet<Integer>>();
		boolean find = false;
		int max = 0;
		for(Integer label : labelUnion.keySet()){
			find = false;
			if(labelUnion2.size() == 0)
				labelUnion2.put(label, new HashSet<Integer>(labelUnion.get(label)));
			else{
				for(Integer labelFinal : labelUnion2.keySet()){
					if(labelUnion2.get(labelFinal).contains(label)){
						labelUnion2.get(labelFinal).addAll(new HashSet<Integer>(labelUnion.get(label)));
						find = true;
					}else if(labelUnion.get(label).contains(labelFinal)){
						labelUnion2.get(labelFinal).add(label);
						labelUnion.get(label).remove(labelFinal);
						labelUnion2.get(labelFinal).addAll(labelUnion.get(label));
						find = true;
					}
		
					
				}
				if (find == false){
					labelUnion2.put(label, new HashSet<Integer>(labelUnion.get(label)));
				}
				
			}
			if (label>max);
				max = label;
		}
		
		
		for(Integer label : labelUnion2.keySet()){
			for(Integer label2 : labelUnion2.get(label)){
				if(component.get(label2) != null && component.get(label) != null){
					component.get(label).addAll(component.get(label2));
					component.remove(label2);
				}
			}
		}
		
		listComponents = new ArrayList<ArrayList<Point>>();
		listComponents.addAll(component.values());
		
		/*
		listComponents.add(list1);
		listComponents.add(list2);
		listComponents.add(list3);
		listComponents.add(list4);
		*/
	
	}	

		
	
	
	// Créer une image avec chaque composante connexe d'une autre couleur
	public MyImage getCCMyImage() {
		MyImage CCMyImage = new MyImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
		Color compoColor = null;
		
		// Affichage d'un fond blanc
		Graphics g = CCMyImage.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, imageWidth, imageHeight);
				
		for (ArrayList<Point> listPoint : listComponents) {
			compoColor = new Color ((int) (Math.random()*255), ((int) Math.random()*255), (int) (Math.random()*255) );
			for (Point pixel : listPoint) {
					if(CCMyImage.getRGB(pixel.getX(), pixel.getY()) == Color.WHITE.getRGB())
						CCMyImage.setRGB(pixel.getX(), pixel.getY(), compoColor.getRGB());
			}
		}
		
		return CCMyImage;
	} 
	
	
	public void addPoint (Point p) {
		listPoints.add(p);
	}
	
	public boolean containsPoint (Point p) {
		return listPoints.contains(p);
	}
	
	public ArrayList<Point> getContour() {
		ArrayList<Point> contour = new ArrayList<Point>();

		for (int i=0 ; i<image.getHeight(); i++) {
			for (int j = 0 ; j<image.getWidth(); j++) {
				Point p = new Point (i,j);
				Point voisin_bas = new Point (i,j+1);
				Point voisin_haut = new Point (i,j-1);
				Point voisin_droite = new Point (i+1,j);
				Point voisin_gauche = new Point (i-1,j);
				
				if (this.containsPoint(p)) {
					if ((this.containsPoint(voisin_haut) && this.containsPoint(voisin_bas)==false)
						|| (this.containsPoint(voisin_haut)==false) && this.containsPoint(voisin_bas)
						|| (this.containsPoint(voisin_droite)==false) && this.containsPoint(voisin_gauche)
						|| (this.containsPoint(voisin_gauche)==false) && this.containsPoint(voisin_droite)) {
						
							contour.add(p);
					}				
				}
			}
		}
		return contour;
	}
}
