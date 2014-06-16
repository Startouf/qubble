package imageObject;

import image_GUI.Window;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import main.ImageDetection;

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
	public static int SQUAREDIFF = 5;
	
	private ArrayList<Point> list;
	private int xCenter, yCenter;
	private  Point corner;
	
	// Information sur la signature
	private boolean squareTest;
	private float[] courbe;
	private float[] aliasing;
	private float[] bestAliasing;
	private int bestAngle, maxDistance, bestTresh;
	
	static float perfectDistanceMin;
	float distanceMin;

	
	// Forme pour un carré parfait
	public static float[] perfectSquare;
	public static float perfectSquareSD, perfectSquare_Average;
	static{
		perfectSquare = new float[90];
		int theta = 0;
		float total = 0;
		perfectSquare[0] = (float) 101.04455;
		perfectSquare[1] = (float) 101.04455;
		perfectSquare[2] = (float) 101.24229;
		perfectSquare[3] = (float) 101.24229;
		perfectSquare[4] = (float) 101.49384;
		perfectSquare[5] = (float) 101.49384;
		perfectSquare[6] = (float) 101.96568;
		perfectSquare[7] = (float) 101.96568;
		perfectSquare[8] = (float) 102.4207;
		perfectSquare[9] = (float) 102.4207;
		perfectSquare[10] = (float) 103.16007;
		perfectSquare[11] = (float) 103.16007;
		perfectSquare[12] = (float) 104.048065;
		perfectSquare[13] = (float) 104.048065;
		perfectSquare[14] = (float) 104.80935;
		perfectSquare[15] = (float) 104.80935;
		perfectSquare[16] = (float) 105.9481;
		perfectSquare[17] = (float) 105.9481;
		perfectSquare[18] = (float) 107.22407;
		perfectSquare[19] = (float) 107.22407;
		perfectSquare[20] = (float) 108.63241;
		perfectSquare[21] = (float) 108.63241;
		perfectSquare[22] = (float) 110.16805;
		perfectSquare[23] = (float) 110.16805;
		perfectSquare[24] = (float) 112.25863;
		perfectSquare[25] = (float) 112.25863;
		perfectSquare[26] = (float) 114.061386;
		perfectSquare[27] = (float) 114.061386;
		perfectSquare[28] = (float) 116.46888;
		perfectSquare[29] = (float) 116.46888;
		perfectSquare[30] = (float) 119.03781;
		perfectSquare[31] = (float) 119.03781;
		perfectSquare[32] = (float) 121.75796;
		perfectSquare[33] = (float) 121.75796;
		perfectSquare[34] = (float) 124.61942;
		perfectSquare[35] = (float) 124.61942;
		perfectSquare[36] = (float) 127.61269;
		perfectSquare[37] = (float) 127.61269;
		perfectSquare[38] = (float) 131.3659;
		perfectSquare[39] = (float) 131.3659;
		perfectSquare[40] = (float) 135.28119;
		perfectSquare[41] = (float) 135.28119;
		perfectSquare[42] = (float) 140.0357;
		perfectSquare[43] = (float) 140.0357;
		perfectSquare[44] = (float) 142.83557;
		perfectSquare[45] = (float) 142.83557;
		perfectSquare[46] = (float) 140.0357;
		perfectSquare[47] = (float) 140.0357;
		perfectSquare[48] = (float) 135.28119;
		perfectSquare[49] = (float) 135.28119;
		perfectSquare[50] = (float) 131.3659;
		perfectSquare[51] = (float) 131.3659;
		perfectSquare[52] = (float) 127.61269;
		perfectSquare[53] = (float) 127.61269;
		perfectSquare[54] = (float) 124.61942;
		perfectSquare[55] = (float) 124.61942;
		perfectSquare[56] = (float) 121.75796;
		perfectSquare[57] = (float) 121.75796;
		perfectSquare[58] = (float) 119.03781;
		perfectSquare[59] = (float) 119.03781;
		perfectSquare[60] = (float) 116.46888;
		perfectSquare[61] = (float) 116.46888;
		perfectSquare[62] = (float) 114.061386;
		perfectSquare[63] = (float) 114.061386;
		perfectSquare[64] = (float) 112.25863;
		perfectSquare[65] = (float) 112.25863;
		perfectSquare[66] = (float) 110.16805;
		perfectSquare[67] = (float) 110.16805;
		perfectSquare[68] = (float) 108.63241;
		perfectSquare[69] = (float) 108.63241;
		perfectSquare[70] = (float) 107.22407;
		perfectSquare[71] = (float) 107.22407;
		perfectSquare[72] = (float) 105.9481;
		perfectSquare[73] = (float) 105.9481;
		perfectSquare[74] = (float) 104.80935;
		perfectSquare[75] = (float) 104.80935;
		perfectSquare[76] = (float) 104.048065;
		perfectSquare[77] = (float) 104.048065;
		perfectSquare[78] = (float) 103.16007;
		perfectSquare[79] = (float) 103.16007;
		perfectSquare[80] = (float) 102.4207;
		perfectSquare[81] = (float) 102.4207;
		perfectSquare[82] = (float) 101.96568;
		perfectSquare[83] = (float) 101.96568;
		perfectSquare[84] = (float) 101.49384;
		perfectSquare[85] = (float) 101.49384;
		perfectSquare[86] = (float) 101.24229;
		perfectSquare[87] = (float) 101.24229;
		perfectSquare[88] = (float) 101.04455;
		perfectSquare[89] = (float) 101.04455;
		perfectDistanceMin = (float)101;
		for(int i = 0; i < 90 ; i++){
//			if(i>45)
//				theta = 90 - i;
//			else
//				theta = i;
//			// Diviser par deux : diagonale => rayon
//			perfectSquare[i] = (float) ((float)0.5*(1/Math.cos((theta)%90*Math.PI/(float)180)));
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
	private static float getStandartDeviation(float[] sample, int size, float average){
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
	public Integer[] isSquare(int rayon){
		if(xCenter < 0 || yCenter < 0){
			this.getCenter();
		}
		aliasing = new float[180];
		courbe = new float[180];
				
		float[] mySquare = new float[180]; //pas de 2
		float mySquareAverage = 0;
		float mySquareSD = 0;
		int angle = 0; 
		float distance = 0;
		// Calculer la distance
		for(Point pt : list){
			distance = (float) Math.sqrt((pt.getX()-xCenter)*(pt.getX()-xCenter) + (pt.getY()- yCenter)*(pt.getY()- yCenter));
			// Calcul de l'angle : produit sca / diviser par les distances ==> transformer le cosinus
			// le deuxième vecteur est (1, 0)
			
			//entre 0 et 180 degres
			if ((pt.getY()-yCenter) >= 0) {
				angle = (int) (Math.acos(((pt.getX()-xCenter)/(float)distance))*180/(float)Math.PI);
				// Prendre un degré sur deux
				angle = (angle / 2)%180;
			}
			//entre 180 et 360 degres 
			else if ((pt.getY()-yCenter) < 0) {						
				angle = 360 - (int) ((Math.acos(((pt.getX()-xCenter)/(float)distance))*180/(float)Math.PI));
				// Prendre un degré sur deux
				angle = (angle / 2)%180;
			}

			//System.out.println(angle);
			if(mySquare[angle] < distance){
				// Ajout de la distance
				mySquare[angle] = distance;
			}
			if(maxDistance < distance){
				maxDistance = (int)distance;
				corner = pt;
			}
		}
		
		distanceMin = Float.MAX_VALUE;
		//si mySquare[angle] nul, barycentre entre les deux valeurs non nulles superieure et inferieure
		for (int i=0; i<180; i++) {
			if(mySquare[i] ==0) {
				int ind_inf=i - 1;
				int ind_sup = i + 1;
//				while (mySquare[ind_inf]==0) {
//					ind_inf --;
//				}
//				while (mySquare[ind_sup] == 0) {
//					ind_sup ++;
//				}
				// Remplacement du barycentre par la moyenne
				if(ind_inf < 0)
					ind_inf = 1;
				if(ind_sup >= 179)
					ind_sup = 178;
				mySquare[i] = (mySquare[ind_inf] + mySquare[ind_sup]) /(2);
			}
			//Sauvegarde la distance minimale
			if(mySquare[i] < distanceMin)
				distanceMin = mySquare[i];
		}
		
		//System.out.println("Distance minimale : " + distanceMin);

		
		// La composante étudiée est trop grande
		//System.out.println("Distance maximale par rapport au centre : " + distanceMax);
		if(Math.abs(maxDistance-rayon) > SQUAREDIFF){
			return null;
		}
		
		
		for(int i = 0; i<180; i++){
			mySquareAverage += mySquare[i];
		}
		mySquareAverage /= 180;
		mySquareSD = getStandartDeviation(mySquare, 180, mySquareAverage);
		
		
		// Calcul de la ressemblance pour le carré en le déphasant jusqu'à 90° (robuste à la rotation)
		float save = 0, temp = 0;
		for(int dephasage = 0; dephasage < 45 ; dephasage++){
			temp = calculError(mySquare, mySquareAverage, mySquareSD, dephasage);
			//System.out.println(dephasage + " : " + temp);
			if(temp > save){
				save = temp;	
				bestAngle = dephasage;
				bestAliasing = aliasing;
			}
		}
		
		courbe = mySquare;
		bestTresh = (int) (save*100);
			if(save > SQUARETRIGGER ){
				if(ImageDetection.PRINTDEBUG){
					System.out.println("True : " + save + " (Angle : " + bestAngle*2 + ")");
				}
				Integer[] tab = new Integer[180];
				for(int i = 0; i < 180; i ++){
					tab[i] = Integer.valueOf((int)mySquare[i]);
				}
				return tab;
			}else{
				if(ImageDetection.PRINTDEBUG){
					System.out.println("False : " + save);
				}
				return null;
			}
	}
	
	/**
	 * Calcul l'erreur entre le patron trouvé pour la composante connexe et celui d'un carré parfait
	 * @param real
	 * @param length
	 * @return
	 */
	private float calculError(float[] real, float realAverage, float realSD, int dephasage){
		
		float result = 0;
		
		for(int i = 0 ; i < 180 ; i++){
//			aliasing[i] = Math.abs(real[(i+dephasage)%180]-realAverage)*Math.abs(perfectSquare[(i*2)%90]-perfectSquare_Average);
//			result += aliasing[i];
			aliasing[i] = Math.abs(((real[(i+dephasage)%180]/realAverage) / (perfectSquare[(i*2)%90]/perfectSquare_Average)) - 1);
			result += aliasing[i];
		}
		
		//return result/(perfectSquareSD*realSD*180);
		return (1 - result/(float)(180.0));
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

	public boolean isSquareTest() {
		return squareTest;
	}

	public float[] getCourbe() {
		return courbe;
	}

	public float[] getAliasing() {
		return bestAliasing;
	}

	public int getBestAngle() {
		return bestAngle;
	}

	public static float[] getPerfectSquare() {
		return perfectSquare;
	}
	
	
	

}

