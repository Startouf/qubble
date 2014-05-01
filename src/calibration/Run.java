package calibration;

import imageObject.Point;
import imageTransform.TabImage;

import javax.swing.UIManager;

import ui.App;

public class Run {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");
		} catch (Exception e) {System.out.println("Erreur de chargement ");}
		
		
		Calibrator calib = new Calibrator();
		
		//TODO : reconnaissance d'images
		TabImage image;
		/*image = new TabImage(ImageIO.read(new File("test/calibration/photo miroir sale.png")));
		 */			
		
		//à faire manuellement
		Calibrate.CAMERA_PIXEL_UPPER_RIGHT = new Point (1280,690);
		Calibrate.CAMERA_PIXEL_UPPER_LEFT = new Point (0,650);
		Calibrate.CAMERA_PIXEL_LOWER_LEFT = new Point (35,35);
		Calibrate.CAMERA_PIXEL_LOWER_RIGHT = new Point (1200,35);
		
		//test
		Point point = new Point (35, 300);
		Calibrate.mapToOpenGL(point);
		
		//TODO : close image
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		calib.terminate();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		App app = new App();
	}
}
