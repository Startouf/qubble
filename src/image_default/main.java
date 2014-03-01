package image_default;

import java.io.File;
import GUI.Window;


public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Window wind = new Window();
		wind.readImage(new File("qr.png"));
	}

}
