package imageObject;

import imageTransform.MyImage;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 * Représente un QR Code détecté par l'ordinateur
 * Contient : un cadre, 3 repères, une liste de composantes connexes
 * @author masseran
 *
 */
public class QRCode {
	// Taille de la fenêtre pour définir si une zone contient une composante (demi-longueur)
	public static int sizeWindow = 4;
	
	private ConnexeComponent border;
	//private ArrayList<ConnexeComponent> landmark;
	private MyImage qrImage;
	private MyImage greyImage;
	
	public QRCode(ConnexeComponent border, MyImage greyImage,MyImage binaryImage){
		this.border = border;
		this.qrImage = binaryImage.getBinaryMyImage();
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
	private boolean isBlack(int x, int y){
		Graphics g = greyImage.getGraphics();
		int moy = 0;
		
		if(sizeWindow>0){
			for(int i = x-sizeWindow ; i<x+sizeWindow ; i++){
				for(int j = y-sizeWindow ; j<y+sizeWindow ; j++){
					if(x>=0 && x<greyImage.getWidth() && y >= 0 && y<greyImage.getHeight()){
						if(greyImage.getRGB(i, j) == Color.BLACK.getRGB()){
							moy++;
						}
					}
				}
			}
			
			if(moy/(float)(Math.pow(sizeWindow*2, 2)) > 0.5){
				g.setColor(Color.red);
				g.fillRect(x + border.getCorner(0).getX()-5 -sizeWindow, y + border.getCorner(2).getY()-5-sizeWindow, sizeWindow*2, sizeWindow*2);
				return true;
			}
			else{
				g.setColor(Color.blue);
				g.fillRect(x + border.getCorner(0).getX()-5 -sizeWindow, y + border.getCorner(2).getY()-5-sizeWindow, sizeWindow*2, sizeWindow*2);
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
				g.setColor(Color.red);
				g.fillRect(x + border.getCorner(0).getX()-5, y + border.getCorner(2).getY()-5, 1, 1);
				return true;
			}
			else{
				g.setColor(Color.blue);
				g.fillRect(x + border.getCorner(0).getX()-5, y + border.getCorner(2).getY()-5, 1, 1);
				return false;
			}
		}else
			return false;

			
	}
	
	/**
	 * Renvoie la valeur associée au QR code
	 * # : Bord
	 * #######
	 * #     #
	 * # 012 #
	 * # 345 #
	 * # 678 #
	 * #     #
	 * #######
	 * @return int : bit pd fort >> 876543210 << bit pd faible
	 *
	 * @return
	 **/
	public int getValeur(){
		int offsetX = 22, offsetY = 25;
		int angleX = Math.abs(border.getCorner(2).getX() - border.getCorner(0).getX())/5;
		int angleY = Math.abs(border.getCorner(0).getY() - border.getCorner(2).getY())/5;
		int angleX2 = Math.abs(border.getCorner(3).getX() - border.getCorner(0).getX())/5;
		int angleY2 = Math.abs(border.getCorner(0).getY() - border.getCorner(3).getY())/5;
		int pas = 16;
		
		int X = 5;
		int Y = border.getCorner(0).getY()-border.getCorner(2).getY()+5;

		
		int valeur = 0, masque = 1;
		
		for(int i = 0 ; i < 3; i++){
			for(int j = 0 ; j < 3; j++){
				if(isBlack( border.getCorner(0).getX() + j*(angleX+angleX2), border.getCorner(0).getY() + i*(angleY+angleY2))){
					valeur |= masque; 
				}
				masque = masque << 1;
			}
		}
		
		return valeur;
	
	}
	
	
	
}
