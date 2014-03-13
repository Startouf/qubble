package image_default;

import imageObject.Point;
import imageTransform.MyImage;
import image_GUI.Window;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;


public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Window wind = new Window();
		
		// Image avec un qr code
		//wind.readImage(new File("Modules/image/database/test/qr_bis.jpg"), true);
		//wind.readImage(new File("Modules/image/database/test/qr_bis_rot.jpg"), true);
		//Image de plusieurs qr codes (1 petit et 3 moyens) 
		//wind.readImage(new File("Modules/image/database/test/multi.jpg"), true, 180, 100, 18);
		//Photo d'un vrai qr code en grand
		//wind.readImage(new File("Modules/image/database/test/qr.png"), true);
		
		//wind.readImage(new File("Modules/image/database/test/QR_Reel_2.png"), true);
		//wind.readImage(new File("Modules/image/database/test/QR_photo.jpg"), true);
		//wind.readImage(new File("Modules/image/database/test/QR_photo_720p.jpg"), true, 180, 570, 110);
		
		// Image avec des carrés
		wind.readImage(new File("Modules/image/database/test/Square_720p.jpg"), false, 10, 50, 0);  // Binary level : 10
		
	}
	
	
	/**
	 * Recherche tous les QR codes parmis screen 
	 * @param screen
	 * @return une hashmap avec l'id du qr code et sa position sur la table?image
	 */
	public static HashMap<Integer, Point> getQRcodes(MyImage screen){
		HashMap<Integer, Point> finalQRcodes = new HashMap<Integer, Point>();
		
		return finalQRcodes;
	}
	
	/**
	 * Recherche tous les carrés sur l'image (version simplifiée)
	 * @param screen
	 * @return une hashmap avec l'id du carré et sa position sur la table?image
	 */
	public static HashMap<Integer, Point> getSquares(MyImage screen){
		HashMap<Integer, Point> finalQRcodes = new HashMap<Integer, Point>();
		
		return finalQRcodes;
	}

}
