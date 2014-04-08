package calibration;

import imageObject.ConnexeComponent;
import imageObject.Point;
import imageTransform.ComponentsAnalyser;
import imageTransform.MyImage;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

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
		MyImage image;
		try {
			image = new MyImage(ImageIO.read(new File("Modules/image/database.test/qr_1.jpg")));
			image.getGreyMyImage();
			image.getBinaryMyImage();
			
			for (int i=0 ; i<image.getHeight() ; i++) {
				for (int j=0 ; j>image.getWidth() ; j++) {
					if (image.getRGB(i,j) == new Color(255,255,255).getRGB()) {
						image.setRGB(i, j, (new Color(0, 0, 0).getRGB()));
					}
					else image.setRGB(i, j, (new Color(255, 255, 255).getRGB()));
				}
			}
			
			ComponentsAnalyser analyser = new ComponentsAnalyser (image);
			ArrayList<ConnexeComponent> CClist = analyser.getCClist();
			int k=0;
			while (CClist.get(k).getLength() < 100) {
				k++;
			}
			ConnexeComponent CalibrationSquare = CClist.get(k);
			
			Calibrate.CAMERA_PIXEL_LOWER_RIGHT = new Point (CalibrationSquare.getxMax(), CalibrationSquare.getyMin());
			Calibrate.CAMERA_PIXEL_LOWER_LEFT = new Point (CalibrationSquare.getxMin(), CalibrationSquare.getyMin());
			Calibrate.CAMERA_PIXEL_UPPER_LEFT = new Point (CalibrationSquare.getxMin(), CalibrationSquare.getyMax());
			Calibrate.CAMERA_PIXEL_UPPER_RIGHT = new Point (CalibrationSquare.getxMax(), CalibrationSquare.getyMax());
		} 
		
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		//TODO : close image
		App app = new App();
	}

}