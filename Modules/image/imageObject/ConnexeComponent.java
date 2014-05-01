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
	// Coefficient pour accepter qu'une composante est un carré
	public static float SQUARETRIGGER = (float) 0.80;
	
	private ArrayList<Point> list;
	private int xCenter, yCenter;
	private  Point corner;
	
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
		yCenter = xCenter = -1;
	}
	
	/**
	 * Ajoute un point à la composante connexe et modifie les coordonnées de la CC si besoi
	 * @param pt
	 */
	public void addPoint(Point pt){
		
		if(pt != null){
			list.add(pt);

		}
		
	}
	
	public ArrayList<Point> getConnexePoints(){
		return list;
	}
	
	/**
	 * Returne true si la composante connexe à l'allure d'un carré
	 * rayon : valeur du rayon/2 désiré pour filtrer les carrés
	 * @return
	 */
	public boolean isSquare(int rayon){
		if(xCenter < 0 || yCenter < 0){
			this.getCenter();
		}
				
		float[] mySquare = new float[180]; //pas de 2
		float mySquareAverage = 0;
		float mySquareSD = 0;
		int angle = 0; 
		float distance = 0;
		int distanceMax = 0;
		// Calculer la distance
		for(Point pt : list){
			distance = (float) Math.sqrt((pt.getX()-xCenter)*(pt.getX()-xCenter) + (pt.getY()- yCenter)*(pt.getY()- yCenter));
			// Calcul de l'angle : produit sca / diviser par les distances ==> transformer le cosinus
			// le deuxième vecteur est (1, 0)
			
			//entre 0 et 180 degres
			if ((pt.getY()-yCenter)/(float)distance >= 0) {
				angle = (int) ((Math.acos(((pt.getX()-xCenter)/(float)distance))*180/(float)Math.PI)/2) %180;
			}
			//entre 180 et 360 degres 
			else if ((pt.getY()-yCenter)/(float)distance < 0) {						
				angle = ((360 - (int) ((float)Math.PI - (Math.acos(((pt.getX()-xCenter)/(float)distance))*180/(float)Math.PI)) ) /2) %180;
			}

			//System.out.println(angle);
			if(mySquare[angle] < distance){
				// Ajout de la distance
				mySquare[angle] = distance;
			}
			if(distanceMax < distance){
				distanceMax = (int)distance;
				corner = pt;
			}
		}
		
		//si mySquare[angle] nul, barycentre entre les deux valeurs non nulles superieure et inferieure
		for (int i=0; i<180; i++) {
			if(mySquare[angle] ==0) {
				int ind_inf=angle - 1;
				int ind_sup = angle + 1;
				while (mySquare[ind_inf]==0) {
					ind_inf --;
				}
				while (mySquare[ind_sup] == 0) {
					ind_sup ++;
				}
			
				mySquare[angle] = (mySquare[ind_inf]*(ind_sup-angle) + mySquare[ind_sup]*(angle-ind_inf)) /(ind_sup-ind_inf);
			}
		}

		
		// La composante étudiée est top grande
		//System.out.println("Distance maximale par rapport au centre : " + distanceMax);
		if(Math.abs(distanceMax-rayon) > 5){
			return false;
		}
		
		
		
/*		 //Afficher la composante connexe sous forme de courbe.
		for(int i = 0; i<180; i += 2){
			g.drawLine(i, (int)mySquare[i], i, (int)mySquare[i]);
		}*/
		
		
		for(int i = 0; i<180; i++){
			mySquareAverage += mySquare[i];
		}
		mySquareAverage /= 180;
		mySquareSD = getStandartDeviation(mySquare, 180, mySquareAverage);
		
		
		// Calcul de la ressemblance pour le carré en le déphasant jusqu'à 90° (robuste à la rotation)
		float save = 0, temp = 0;
		int bestAngle = 0;
		for(int dephasage = 0; dephasage < 90 ; dephasage++){
			temp = calculError(mySquare, mySquareAverage, mySquareSD, dephasage);
			if(temp > save){
				save = temp;	
				bestAngle = dephasage;
			}
		}
			if(save > SQUARETRIGGER ){
				System.out.println("True : " + save + " (Angle : " + bestAngle + ")");
				return true;
			}else{
				System.out.println("False : " + save);
				return false;
			}
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
			result += Math.abs(real[(i+dephasage)%180]-realAverage)*Math.abs(perfectSquare[(i*2)%90]-perfectSquare_Average);
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
	
	
	public int getxCenter() {
		if(xCenter < 0 || yCenter < 0){
			this.getCenter();
		}
		return xCenter;
	}

	public int getyCenter() {
		if(xCenter < 0 || yCenter < 0){
			this.getCenter();
		}
		return yCenter;
	}
	
	public Point getCorner(){
		return corner;
	}
	

}

