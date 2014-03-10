package imageObject;

import image_GUI.Window;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 * Représentation du composante connexe
 * Contient les coordonnées extrénums et le pt central
 * Permet de vérifier s'il s'agit d'un carré
 * @author masseran
 *
 */
public class ConnexeComponent {
	
	public static final float SQUARETRIGGER = (float) 0.5;
	
	private ArrayList<Point> list;
	private int xMax, xMin, yMax, yMin, xCenter, yCenter;
	
	// Forme pour un carré parfait
	public static float[] perfectSquare;
	public static float perfectSquareSD, perfectSquare_Average;
	static{
		perfectSquare = new float[90];
		int theta = 0;
		float total = 0;
		for(int i = 0; i < 90 ; i++){
			if(i>45)
				theta = 90 - i;
			else
				theta = i;
			perfectSquare[i] = (float) ((float)0.5*(1/Math.cos((theta)%90*Math.PI/(float)180)));
			total += perfectSquare[i];
			
			
		}	
		perfectSquare_Average = total/90;
		perfectSquareSD = getStandartDeviation(perfectSquare, 90, perfectSquare_Average);
	}
	
	/**
	 * Calcul l'écart type pour un échantillon de valeurs
	 * @param sample
	 * @param size
	 * @param average
	 * @return
	 */
	public static float getStandartDeviation(float[] sample, int size, float average){
		float  variance = 0;
		for(int i = 0; i < size ; i++){
			variance += Math.pow((sample[i] - average), 2);
		}
		return (float) Math.sqrt(variance/size);
	}
	
	public ConnexeComponent(){
		list = new ArrayList<Point>();
		xMax = 0;
		yMax = 0;
		xMin = Window.imageWidth;
		yMin = Window.imageHeight;
	}
	
	/**
	 * Ajoute un point à la composante connexe et modifie les coordonnées de la CC si besoi
	 * @param pt
	 */
	public void addPoint(Point pt){
		
		if(pt != null){
			list.add(pt);
			if(pt.getX() > xMax)
				xMax = pt.getX();
			if(pt.getY() > yMax)
				yMax = pt.getY();
			if(pt.getX() < xMin)
				xMin = pt.getX();
			if(pt.getY() < yMin)
				yMin = pt.getY();
		}
		
	}
	
	public ArrayList<Point> getConnexePoints(){
		return list;
	}
	
	/**
	 * Returne true si la composante connexe à l'allure d'un carré
	 * @return
	 */
	public boolean isSquare(Graphics g){
		this.getCenter();
		
		float[] mySquare = new float[180];
		float mySquareAverage = 0;
		float mySquareSD = 0;
		int angle = 0; 
		float distance = 0;
		// Calculer la distance
		for(Point pt : list){
			distance = (float) Math.sqrt((Math.pow((pt.getX()-xCenter), 2)+Math.pow((pt.getY()- yCenter), 2)));
			// Calcul de l'angle : produit sca / diviser par les distances ==> transformer le cosinus
			// le deuxième vecteur est (1, 0)
			angle = (int) (Math.acos(((pt.getX()-xCenter)/(float)distance))*180/(float)Math.PI)%180;
			//System.out.println(angle);
			if(mySquare[angle] < distance){
				// Ajout de la distance
				mySquare[angle] = distance;
			}
		}
		
		 //Afficher la composante connexe sous forme de courbe.
//		for(int i = 0; i<180; i += 2){
//			g.drawLine(i, (int)mySquare[i], i, (int)mySquare[i]);
//		}
		
		
		for(int i = 0; i<180; i++){
			mySquareAverage += mySquare[i];
		}
		mySquareAverage /= 180;
		mySquareSD = getStandartDeviation(mySquare, 180, mySquareAverage);
		
		// Calcul de la ressemblance pour le carré en le déphasant jusqu'à 90° (robuste à la rotation)
		float save = 0;
		for(int dephasage = 0; dephasage < 90 ; dephasage++){
			save = Math.max(save, calculError(mySquare, mySquareAverage, mySquareSD, dephasage));
			if(calculError(mySquare, mySquareAverage, mySquareSD, dephasage) >SQUARETRIGGER ){
				System.out.println("True : " + save);
				return true;
			}
				
		}
		System.out.println("False : " + save);
		return false;
	}
	
	/**
	 * Calcul l'erreur entre le patron trouvé pour la composante connexe et celui d'un carré parfait
	 * @param real
	 * @param length
	 * @return
	 */
	public float calculError(float[] real, float realAverage, float realSD, int dephasage){
		
		float result = 0;
		
		for(int i = 0 ; i < 180 ; i++){
			result += Math.abs(real[(i+dephasage)%180]-realAverage)*Math.abs(perfectSquare[i%90]-perfectSquare_Average);
		}
		
		return result/(perfectSquareSD*realSD*180);
	}
	
	/**
	 * Retourne le point central de la composante en fonction de tous les points
	 */
	public void getCenter(){

		for(Point pt : list){
			xCenter += pt.getX();
			yCenter += pt.getY();
		}
		
		xCenter = (int) ((float)(xCenter)/(float)list.size());
		yCenter = (int) ((float)(yCenter)/(float)list.size());
		
	}
	
	/**
	 * Retourne la taille du coté du carré
	 */
	public int getLength(){
		return 	(int) (xMax-xMin + yMax-yMin)/2;
	}

	public int getxMax() {
		return xMax;
	}

	public int getxMin() {
		return xMin;
	}

	public int getyMax() {
		return yMax;
	}

	public int getyMin() {
		return yMin;
	}

	public int getxCenter() {
		return xCenter;
	}

	public int getyCenter() {
		return yCenter;
	}
	
	

}

