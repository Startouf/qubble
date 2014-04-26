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
	public static int sizeWindow = 2;
	
	private ConnexeComponent border;
	//private ArrayList<ConnexeComponent> landmark;
	private TabImage qrImage;
	private TabImage greyImage;
	
	public QRCode(ConnexeComponent border, TabImage greyImage){
		this.border = border;
		this.greyImage = greyImage;
	}
	

	public ConnexeComponent getBorder() {
		return border;
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
		
		if(sizeWindow>0){
			for(int i = x-sizeWindow ; i<x+sizeWindow ; i++){
				for(int j = y-sizeWindow ; j<y+sizeWindow ; j++){
					if(i>=0 && i<greyImage.getWidth() && j >= 0 && j<greyImage.getHeight()){
						if((greyImage.getRGB(i, j) & 0xff) < 120){
							moy++;
						}
					}
				}
			}
			
			if(moy/(float)(Math.pow(sizeWindow*2, 2)) > 0.5){
				target.put(new Point(x, y), true);
				return true;
			}
			else{
				target.put(new Point(x, y), false);
				//g.setColor(Color.blue);
				//g.fillRect(x -sizeWindow, y -sizeWindow, sizeWindow*2, sizeWindow*2);
				return false;
			}
			// Recherche sur 1 pixel
		}else if(sizeWindow == 0){
					if(x>=0 && x<greyImage.getWidth() && y >= 0 && y<greyImage.getHeight()){
						if(greyImage.getRGB(x, y) == Color.BLACK.getRGB()){
							moy++;
						}
					}
			
			if(moy > 0.5){
				target.put(new Point(x, y), true);
				//g.setColor(Color.red);
				//g.fillRect(x , y , 1, 1);
				return true;
			}
			else{
				target.put(new Point(x, y), false);
				//g.setColor(Color.blue);
				//g.fillRect(x , y , 1, 1);
				return false;
			}
		}else
			return false;

			
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
		int baseX = (xCenter - border.getCorner(0).getX())/3;
		int baseY = (yCenter - border.getCorner(0).getY())/3;
		
		targetX = oldPointX =  (xCenter - baseX);
		targetY = oldPointY =  (yCenter - baseY);
				
		int valeur = 0, masque = 1;
		
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
		
		return valeur;
	
	}
	
	
	
}
