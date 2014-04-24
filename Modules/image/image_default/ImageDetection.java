package image_default;

import imageObject.Point;
import imageTransform.MyImage;
import image_GUI.Window;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

import camera.Camera;


public class ImageDetection {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		// TODO Auto-generated method stub
		
				/*Camera wb = new Camera();
				wb.start();
				Window wind = new Window(wb);*/
				
				Window wind = new Window();
				
				// Image avec un qr code
				//wind.readImage(new File("Modules/image/database/test/qr_bis.jpg"), true, 180, 200, 50);
				//wind.readImage(new File("Modules/image/database/test/qr_bis_rot.jpg"), true);
				//Image de plusieurs qr codes (1 petit et 3 moyens) 
				//wind.readImage(new File("Modules/image/database/test/multi.jpg"), true, 180, 100, 18);
				//Photo d'un vrai qr code en grand
				//wind.readImage(new File("Modules/image/database/test/qr.png"), true);
				
				//wind.readImage(new File("Modules/image/database/test/QR_Reel_2.png"), true);
				//wind.readImage(new File("Modules/image/database/test/QR_photo.jpg"), true);
				//wind.readImage(new File("Modules/image/database/test/QR_photo_720p.jpg"), true, 180, 570, 110);
				//wind.readImage(new File("Modules/image/database/test/qr_code_light.jpg"), true, 180, 200, 0);
				//wind.readImage(new File("Modules/image/database/test/qr_codes_light_sym.jpg"), true, 180, 210, 0);
				//wind.readImage(new File("Modules/image/database/test/qr_codes_light_sym_small.jpg"), true, 180, 21, 0);
				//wind.readImage(new File("Modules/image/database/test/vlcsnap-2014-04-08-14h55m54s173.png"), true, 180, 21, 88);
				//wind.readImage(new File("Modules/image/database/test/vlcsnap-2014-04-08-14h55m22s0.png"), true, 180, 21, 88);
				// Qr moyen : ext : 116px, int : 88 px
				wind.readImage(new File("Modules/image/database/test/essai_qr_petit.png"), true, 180, 116, 59);
				//wind.readImage(new File("Modules/image/database/test/vlcsnap-2014-04-08-16h13m36s195.png"), true, 180, 21, 88);
				// Image avec des carrés
				//wind.readImage(new File("Modules/image/database/test/Square_720p.jpg"), false, 10, 50, 0);  // Binary level : 10
				//wind.readImage(new File("Modules/image/database/test/new_reel.png"), true, 180, 75, 0);	
				//wind.readImage(new File("Modules/image/database/test/traite.png"), true, 180, 75, 0);
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
