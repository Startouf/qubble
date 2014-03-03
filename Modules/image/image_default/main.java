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
		//wind.readImage(new File("Modules/image/database/test/qr.png"));
	}

}
