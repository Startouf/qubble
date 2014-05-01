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
		try {
			image = new TabImage(ImageIO.read(new File("test/calibration/photo miroir sale.png")));
			image.getGrey();
			image.getVarianceFilter(3,5);
			
			/**
			for (int i=0 ; i<image.getHeight() ; i++) {
				for (int j=0 ; j>image.getWidth() ; j++) {
					if (image.getRGB(i,j) == new Color(255,255,255).getRGB()) {
						image.setRGB(i, j, (new Color(0, 0, 0).getRGB()));
					}
					else image.setRGB(i, j, (new Color(255, 255, 255).getRGB()));
				}
			}
			**/
			
//			ComponentsAnalyser analyser = new ComponentsAnalyser (image);
//			ArrayList<ConnexeComponent> CClist = analyser.getCClist();
//			int k=0;
//			while (CClist.get(k).getLength() < 10) {
//				k++;
//			}
//			ConnexeComponent CalibrationSquare = CClist.get(0);
			
//			Calibrate.CAMERA_PIXEL_LOWER_RIGHT = new Point (CalibrationSquare.getxMax(), CalibrationSquare.getyMin());
//			Calibrate.CAMERA_PIXEL_LOWER_LEFT = new Point (CalibrationSquare.getxMin(), CalibrationSquare.getyMin());
//			Calibrate.CAMERA_PIXEL_UPPER_LEFT = new Point (CalibrationSquare.getxMin(), CalibrationSquare.getyMax());
//			Calibrate.CAMERA_PIXEL_UPPER_RIGHT = new Point (CalibrationSquare.getxMax(), CalibrationSquare.getyMax());
			
		} 
		
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		//TODO : close image
//		App app = new App();
	}

}