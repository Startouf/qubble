package calibration;

import imageObject.ConnexeComponent;
import imageObject.Point;
import imageTransform.TabImage;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import qrDetection.ComponentsAnalyser;

import ui.App;

public class Calibrator{

	private Thread image, camera;
	
	public Calibrator(){
		image = new Thread(new CalibrationProjection());
		image.start();
		
	}
	
	public static void main(String[] args) {
		
		Calibrator calib = new Calibrator();
		
		//TODO : reconnaissance d'images
		TabImage image;
		/*image = new TabImage(ImageIO.read(new File("test/calibration/photo miroir sale.png")));
		 */			
		
		//à faire manuellement
		Calibrate.CAMERA_PIXEL_LOWER_RIGHT = new Point (1100,860);
		Calibrate.CAMERA_PIXEL_LOWER_LEFT = new Point (119,860);
		Calibrate.CAMERA_PIXEL_UPPER_LEFT = new Point (35,250);
		Calibrate.CAMERA_PIXEL_UPPER_RIGHT = new Point (1187,250);
		
		//test
		//Point point = new Point (119, 300);
		//Calibrate.mapToOpenGL(point);
		
		//TODO : close image
//		App app = new App();
	}

}