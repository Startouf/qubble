package imageObject;

import java.util.ArrayList;

/**
 * Représente un QR Code détecté par l'ordinateur
 * Contient : un cadre, 3 repères, une liste de composantes connexes
 * @author masseran
 *
 */
public class QRCode {
	// Défini le seuil de tolérance pour accepter un produit scalaire comme nul dans la création de l'axe relatif
	public static int Trigger = 100;
	
	private ConnexeComponent border;
	private ArrayList<ConnexeComponent> landmark;
	
	public QRCode(ConnexeComponent border){
		this.border = border;
		landmark = new ArrayList<ConnexeComponent>();
	}

	public ConnexeComponent getBorder() {
		return border;
	}
	
	public void addLandMark(ConnexeComponent cc){
		if(landmark.size() < 3){
			landmark.add(cc);
		}else
			System.out.println("Déjà trois repères dans le qr code.");
	}
	
	public ArrayList<ConnexeComponent> getLandMark(){
		return landmark;
	}
	
	/**
	 * Renvoie la valeur associée au QR code
	 * $ : repère
	 * $01$
	 * 2345
	 * 6789
	 * $abc
	 * @return int : bit pd fort >> cba9876543210 << bit pd faible
	 */
	/**
	 * @return
	 */
	public int getValeur(){
		int valeur = -1;
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
		
		if(landmark.size() == 3){
			/* Création de l'axe relatif :
			 * 
			 */
			// Recherche du repère central
			for(int i = 0; i < 3 ; i++){
				// Les axes sont perpendiculaires, on peut définir repère central
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
				
				
		
		}else
			System.out.println("Impossible de récupérer la valeur du QR code : il n'y a pas 3 repères");
		return valeur;
	}
	
	
}
