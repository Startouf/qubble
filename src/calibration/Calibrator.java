package calibration;

import imageObject.ConnexeComponent;
import imageObject.Point;
import imageTransform.TabImage;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.UIManager;

import qrDetection.ComponentsAnalyser;
import ui.App;

public class Calibrator{

	private static Thread image;
	
	public Calibrator(){
		image = new Thread(new CalibrationProjection());
		image.setName("Projection calibrator");
		image.start();
	}

	public void terminate() {
		image.interrupt();
	}
}