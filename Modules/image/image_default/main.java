package image_default;

import image_GUI.Window;

import java.io.File;


public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Window wind = new Window();
		wind.readImage(new File("Modules/image/database/test/qr_bis.jpg"));
		//wind.readImage(new File("Modules/image/database/test/qr_bis_rot.jpg"));
		//wind.readImage(new File("Modules/image/database/test/qr.png"));
		//wind.readImage(new File("Modules/image/database/test/QR_Reel_2.png"));
		//wind.readImage(new File("Modules/image/database/test/QR_photo.jpg"));
		//wind.readImage(new File("Modules/image/database/test/QR_photo_720p.jpg"));
		
		
	}

}
