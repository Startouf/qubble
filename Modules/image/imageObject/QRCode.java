package imageObject;

import imageTransform.TabImage;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Représente un QR Code détecté par l'ordinateur
 * Contient : un cadre, 3 repères, une liste de composantes connexes
 * @author masseran
 *
 */
public class QRCode {
	// Taille de la fenêtre pour définir si une zone contient une composante (demi-longueur)
	public int SIZEWINDOW = 2, TRESH;
	
	private ConnexeComponent border;
	//private ArrayList<ConnexeComponent> landmark;
	private TabImage qrImage;
	private TabImage greyImage;
	
	private int iD;
	
	public QRCode(ConnexeComponent border, TabImage greyImage){
		this.border = border;
		this.greyImage = greyImage;
	}
	

	public ConnexeComponent getBorder() {
		return border;
	}
	
	/**
	 * Renvoie le seuil moyen de la zone étudiée
	 * Coordonnée du centre de la fenêtre à seuiller  
	 * @param x
	 * @param y
	 */
	private int getTresh(int x, int y, HashMap<Point, Boolean> target){
		int moy = 0;
		for(int i = x-SIZEWINDOW ; i<x+SIZEWINDOW ; i++){
			for(int j = y-SIZEWINDOW ; j<y+SIZEWINDOW ; j++){
				if(i>=0 && i<greyImage.getWidth() && j >= 0 && j<greyImage.getHeight()){
						moy += (greyImage.getRGB(i, j) & 0xff);
				}
			}
		}
		target.put(new Point(x, y), true);
		return moy/(SIZEWINDOW*SIZEWINDOW*4);
	}
	
	/**
	 * Renvoie true si la zone est noir
	 * Fais une moyenne sur une zone de 4*4 pixels
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isBlack(int x, int y, HashMap<Point, Boolean> target){
		int moy = 0;
	
			for(int i = x-SIZEWINDOW ; i<x+SIZEWINDOW ; i++){
				for(int j = y-SIZEWINDOW ; j<y+SIZEWINDOW ; j++){
					if(i>=0 && i<greyImage.getWidth() && j >= 0 && j<greyImage.getHeight()){
						if((greyImage.getRGB(i, j) & 0xff) < TRESH){
							moy++;
						}
					}
				}
			}
			
			if(moy/(float)(Math.pow(SIZEWINDOW*2, 2)) > 0.5){
				target.put(new Point(x, y), true);
				return true;
			}
			else{
				target.put(new Point(x, y), false);
				//g.setColor(Color.blue);
				//g.fillRect(x -sizeWindow, y -sizeWindow, sizeWindow*2, sizeWindow*2);
				return false;
			}
	}
	
	
	/**
	 * Analyse la valeur du QR code en se basant sur le centre du contour
	 * @return
	 */
	public int getValeurByCenter(HashMap<Point, Boolean> target){
		
		int xCenter = border.getxCenter();
		int yCenter = border.getyCenter();
		int oldPointX = 0, oldPointY = 0;
		// Coordonnées du point qui controle la valeur de la zone
		int targetX = 0, targetY = 0;
		// Coordonnées de la droite directrice
		int baseX = (xCenter - border.getCorner().getX())/3;
		int baseY = (yCenter - border.getCorner().getY())/3;
		
		targetX = oldPointX =  (xCenter - baseX);
		targetY = oldPointY =  (yCenter - baseY);
				
		int valeur = 0, masque = 1;
		
		// Fixer un seuil qui s'adapte à l'image
		// Prend en référence le coin pour la couleur noire et le vide pour la couleur blanche
		int treshIsWhite = getTresh((xCenter - 2*baseX), (yCenter - 2*baseY), target);
		int treshIsBack = getTresh((int)(xCenter - (3.5)*baseX), (int)(yCenter - (3.5)*baseY), target);
		TRESH = (treshIsWhite+treshIsBack)/2;

		// Calcul du point central
		if(isBlack(xCenter, yCenter, target)){
			valeur |= masque; 
		}
		masque = masque << 1;
		
		for(int j = 0 ; j < 4; j++){
				
				if(isBlack(targetX, targetY, target)){
					valeur |= masque; 
				}
				targetX = (int) (Math.cos(Math.PI/2) * (oldPointX-xCenter) - Math.sin(Math.PI/2) * (oldPointY-yCenter) + xCenter);
				targetY = (int) (Math.sin(Math.PI/2) * (oldPointX-xCenter) + Math.cos(Math.PI/2) * (oldPointY-yCenter) + yCenter);
				oldPointX = targetX;
				oldPointY = targetY;
				masque = masque << 1;
			
		}
		
		targetX = (int) (Math.cos(Math.PI/4) * (oldPointX-xCenter)/Math.sqrt(2) - Math.sin(Math.PI/4) * (oldPointY-yCenter)/Math.sqrt(2) + xCenter);
		targetY = (int) (Math.sin(Math.PI/4) * (oldPointX-xCenter)/Math.sqrt(2) + Math.cos(Math.PI/4) * (oldPointY-yCenter)/Math.sqrt(2) + yCenter);
		oldPointX = targetX;
		oldPointY = targetY;
		
		for(int j = 0 ; j < 4; j++){
			
			if(isBlack(targetX, targetY, target)){
				valeur |= masque; 
			}else{
				
			}
			targetX = (int) (Math.cos(Math.PI/2) * (oldPointX-xCenter) - Math.sin(Math.PI/2) * (oldPointY-yCenter) + xCenter);
			targetY = (int) (Math.sin(Math.PI/2) * (oldPointX-xCenter) + Math.cos(Math.PI/2) * (oldPointY-yCenter) + yCenter);
			oldPointX = targetX;
			oldPointY = targetY;
			masque = masque << 1;
		
		}
		iD = valeur;
		
		return valeur;
	
	}
	
	public int getID(){
		return iD;
	}
	
	
	
}
