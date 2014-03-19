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
	public static int sizeWindow = 1;
	
	private ConnexeComponent border;
	//private ArrayList<ConnexeComponent> landmark;
	private MyImage binaryImage;
	
	public QRCode(ConnexeComponent border, MyImage binaryImage){
		this.border = border;
		this.binaryImage = binaryImage;
		//landmark = new ArrayList<ConnexeComponent>();
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
		Graphics g = binaryImage.getGraphics();
		int moy = 0;
		
		if(sizeWindow>0){
			for(int i = x-sizeWindow ; i<x+sizeWindow ; i++){
				for(int j = y-sizeWindow ; j<y+sizeWindow ; j++){
					if(x>=0 && x<binaryImage.getWidth() && y >= 0 && y<binaryImage.getHeight()){
						if(binaryImage.getRGB(i, j) == Color.BLACK.getRGB()){
							moy++;
						}
					}
				}
			}
			
			if(moy/(float)(Math.pow(sizeWindow*2, 2)) > 0.5){
				g.setColor(Color.red);
				g.fillRect(x-sizeWindow, y-sizeWindow, sizeWindow*2, sizeWindow*2);
				return true;
			}
			else{
				g.setColor(Color.blue);
				g.fillRect(x-sizeWindow, y-sizeWindow, sizeWindow*2, sizeWindow*2);
				return false;
			}
			// Recherche sur 1 pixel
		}else if(sizeWindow == 0){
					if(x>=0 && x<binaryImage.getWidth() && y >= 0 && y<binaryImage.getHeight()){
						if(binaryImage.getRGB(x, y) == Color.BLACK.getRGB()){
							moy++;
						}
					}
			
			if(moy > 0.5){
				g.setColor(Color.red);
				g.fillRect(x, y, 1, 1);
				return true;
			}
			else{
				g.setColor(Color.blue);
				g.fillRect(x, y, 1, 1);
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
		// Vecteur directeur [0][]: selon x // [1][]: selon y et [][0]: valeur x // [][1]: valeur y
		int[][] vector = new int[2][2];
		// On divise par 7 pour avoir la valeur unitaire d'une case
		vector[0][0] = (border.getxMax()-border.getxMin())/7;
		vector[0][1] = 0;
		vector[1][0] = 0;
		vector[1][1] = (border.getyMax()-border.getyMin())/7;
		int offX = vector[0][0]/2, offY = vector[1][1]/2;		
		int valeur = 0;
		int masque = 1;
		
		for(int i = 2 ; i < 5; i++){
			for(int j = 2 ; j < 5; j++){
				if(isBlack(border.getxMin() + offX + i*vector[0][0], border.getyMin() +  offY + j*vector[1][1])){
					valeur |= masque; 
				}
				masque = masque << 1;
			}
		}
		
		return valeur;
	
	}
	
	/*public void addLandMark(ConnexeComponent cc){
		if(landmark.size() < 3){
			landmark.add(cc);
		}else
			System.out.println("Déjà trois repères dans le qr code.");
	}*/
	
	/*public ArrayList<ConnexeComponent> getLandMark(){
		return landmark;
	}*/
	
	/**
	 * Renvoie la valeur associée au QR code
	 * $ : repère
	 * $01$
	 * 2456
	 * 3789
	 * $abc
	 * @return int : bit pd fort >> cba9876543210 << bit pd faible
	 *//*
	*//**
	 * @return
	 *//*
	public int getValeur(){
		int valeur = 0;
		if(landmark.size() == 3){
			int[][] v = new int[3][2];
			// Index dans l'arraylist du répère ayant 2 voisins repères
			int indexCenter = 0;
			// Dans le cas d'un qr code dans le bon sens
			int indexUnder = 0;
			int indexRight = 1;
			ArrayList<ConnexeComponent> unknownlandmark = new ArrayList<ConnexeComponent>();
			
			v[0][0] = landmark.get(0).getxCenter()-landmark.get(1).getxCenter();
			v[0][1] = landmark.get(0).getyCenter()-landmark.get(1).getyCenter();
			v[1][0] = landmark.get(0).getxCenter()-landmark.get(2).getxCenter();
			v[1][1] = landmark.get(0).getyCenter()-landmark.get(2).getyCenter();
			v[2][0] = landmark.get(2).getxCenter()-landmark.get(1).getxCenter();
			v[2][1] = landmark.get(2).getyCenter()-landmark.get(1).getyCenter();
			
				 Création de l'axe relatif :
				 * 
				 
				// Recherche du repère central
				for(int i = 0; i < 3 ; i++){
					// Les axes sont perpendiculaires, on peut définir un repère central
					System.out.println(Math.abs(v[i][0]*v[i][0]+ v[(i+2)%3][1]*v[(i+2)%3][1]));
					if(Math.abs(v[i][0]*v[i][0]+ v[(i+2)%3][1]*v[(i+2)%3][1]) < 100){
						if(i == 0 && (i+2)%3 == 1){
							indexCenter = 0;
						}else if(i == 1 && (i+2)%3 == 2){
							indexCenter = 2;
						}else if(i == 2 && (i+2)%3 == 0){
							indexCenter = 1;
						}
					}
				}
				
				for(int i = 0; i < 3 ; i++){
					// Les axes sont perpendiculaires, on peut définir repère central
					if(i != indexCenter){
						unknownlandmark.add(landmark.get(i));
					}
				}
				
				
				// Définir l'orientation du qr code : 
				// 4 possibilités : repère > 2 // repère < 2 // repère entre les 2 à gauche // repère entre les 2 à droite
					// Vue par dessus
					if( landmark.get(indexCenter).getxMin() > unknownlandmark.get(0).getxMax() && landmark.get(indexCenter).getxMin() > unknownlandmark.get(1).getxMax()){
						if(unknownlandmark.get(0).getyMax() > unknownlandmark.get(1).getyMax()){
							indexUnder = 1;
							indexRight = 0;
						}else{
							indexUnder = 0;
							indexRight = 1;
						}
					}
					// Vue par dessous
					if( landmark.get(indexCenter).getxMax() < unknownlandmark.get(0).getxMin() && landmark.get(indexCenter).getxMax() < unknownlandmark.get(1).getxMin()){
						if(unknownlandmark.get(0).getyMax() > unknownlandmark.get(1).getyMax()){
							indexUnder = 0;
							indexRight = 1;
						}else{
							indexUnder = 1;
							indexRight = 0;
						}
					}
					// Vue de gauche
					if( landmark.get(indexCenter).getyMax() < unknownlandmark.get(0).getyMin() && landmark.get(indexCenter).getyMax() < unknownlandmark.get(1).getyMin()){
						if(unknownlandmark.get(0).getxMax() > unknownlandmark.get(1).getxMax()){
							indexUnder = 0;
							indexRight = 1;
						}else{
							indexUnder = 1;
							indexRight = 0;
						}
					}
					// Vue par dessus
					if( landmark.get(indexCenter).getyMin() > unknownlandmark.get(0).getyMax() && landmark.get(indexCenter).getyMin() > unknownlandmark.get(1).getyMax()){
						if(unknownlandmark.get(0).getxMax() > unknownlandmark.get(1).getxMax()){
							indexUnder = 1;
							indexRight = 0;
						}else{
							indexUnder = 0;
							indexRight = 1;
						}
					}
					
					// Vecteur unité de l'axe relatif
					v[0][0] = (unknownlandmark.get(indexRight).getxCenter()-landmark.get(indexCenter).getxCenter())/3;
					v[0][1] = (unknownlandmark.get(indexRight).getyCenter()-landmark.get(indexCenter).getyCenter())/3;
					v[1][0] = (unknownlandmark.get(indexUnder).getxCenter()-landmark.get(indexCenter).getxCenter())/3;
					v[1][1] = (unknownlandmark.get(indexUnder).getyCenter()-landmark.get(indexCenter).getyCenter())/3;
					
					if(isBlack(landmark.get(indexCenter).getxCenter()+ v[1][0], landmark.get(indexCenter).getyCenter() + v[1][1]))
							valeur = valeur | 0x01;
					if(isBlack(landmark.get(indexCenter).getxCenter()+ v[1][0]*2, landmark.get(indexCenter).getyCenter() + v[1][1]*2))
							valeur = valeur | 0x02;
					if(isBlack(landmark.get(indexCenter).getxCenter()+ v[0][0], landmark.get(indexCenter).getyCenter() + v[0][1]))
							valeur = valeur | 0x04;
					if(isBlack(landmark.get(indexCenter).getxCenter()+ v[0][0]*2, landmark.get(indexCenter).getyCenter() + v[0][1]*2))
							valeur = valeur | 0x08;
					int masque = 16;
					for(int i = 1; i < 4 ; i++){
						for(int j = 1; j < 4 ; j++){
							if(isBlack(landmark.get(indexCenter).getxCenter()+ v[1][0]*j + v[0][0]*i, landmark.get(indexCenter).getyCenter() + v[1][1]*j + v[0][1]*i)){
								valeur = valeur | masque;
							}
							masque = masque << 1;
						}
					}
					
					Graphics g = binaryImage.getGraphics();
					g.setColor(Color.green);
					g.fillRect(landmark.get(0).getxCenter(), landmark.get(0).getyCenter(), 8, 8);
					g.setColor(Color.yellow);
					g.fillRect(landmark.get(1).getxCenter(), landmark.get(1).getyCenter(), 8, 8);
					g.setColor(Color.cyan);
					g.fillRect(landmark.get(2).getxCenter(), landmark.get(2).getyCenter(), 8, 8);
					
					
					
			
			}else
				System.out.println("Impossible de récupérer la valeur du QR code : il n'y a pas 3 repères");
			
		
		return valeur;
	}*/
	
}
