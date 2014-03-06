package imageObject;

import java.util.ArrayList;

public class ConnexeComponent {
	
	private ArrayList<Point> list;
	private int xMax, xMin, yMax, yMin, xCenter, yCenter;
	
	public ConnexeComponent(){
		list = new ArrayList<Point>();
		xMax = 0;
		yMax = 0;
		xMin = 200;
		yMin = 200;
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
			if(pt.getX() < yMin)
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
	public boolean isSquare(){
		this.getCenter();
		
		float[] mySquare = new float[361];
		int angle = 0; 
		float distance = 0;
		
		// Calculer la distance
		for(Point pt : list){
			distance = (float) Math.sqrt((Math.pow((pt.getX()-xCenter), 2)+Math.pow((pt.getY()- yCenter), 2)));
			// Calcul de l'angle : produit sca / diviser par les distances ==> transformer le cosinus
			// le deuxième vecteur est (1, 0)
			angle = (int) (Math.acos(((pt.getX()-xCenter)/(float)distance))*360/(float)Math.PI);
			//System.out.println(angle);
			if(mySquare[angle] < distance){
				// Ajout de la distance
				mySquare[angle] = distance;
			}
			
		}
		
		
		if(this.calculError(mySquare, (int)((Math.abs(xMax-xMin) + Math.abs(yMax-yMin))/(float)2)) < 50){
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Calcul l'erreur entre le patron trouvé pour la composante connexe et celui d'un carré parfait
	 * @param real
	 * @param length
	 * @return
	 */
	public float calculError(float[] real, int length){
		
		float error = 0;
		float current = 0;
		float best = 0;
		
		for(int i = 0 ; i < 361 ; i++){
			if(real[i] == 0){
				error +=5;
			}else{
				// Forme pour un carré parfait
				best = (float) (0.5 + Math.sin((i%89/(float)360)*2*Math.PI)*(Math.sqrt(2)-(0.5)));
				// Erreur relative
				current = (Math.abs((real[i]/(float)length) - best) / (float)best) * 100;
				error += current;
				//System.out.println(Math.abs((real[i]/(float)length)) + "||" + best);
			}
		}
		System.out.println(error/360);
		return error/360;
	}
	
	public void getCenter(){

		for(Point pt : list){
			xCenter += pt.getX();
			yCenter += pt.getY();
		}
		
		xCenter = (int) ((float)(xCenter)/(float)list.size());
		yCenter = (int) ((float)(yCenter)/(float)list.size());
		
	}

}
