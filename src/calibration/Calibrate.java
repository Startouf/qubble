package calibration;

import imageObject.Point;

public class Calibrate{

	/**
	 * Le module openGL affiche des coordonnées qui correspondent à une fenêtre de
	 * OpenGL_WIDTH_PX x openGL_HEIGHT_PX
	 * 
	 * La caméra capture une zone
	 * Camera_WIDTH_PX x camera_HEIGHT_PX
	 * 
	 * (PX pour pixel)
	 */
	
	/*
	 * Dimensions openGL
	 */
	static public int OpenGL_WIDTH = 1280;
	static public int OpenGL_HEIGHT = 720;

	/*
	 * Les points doivent être donnés par le module image
	 */
	static public Point CAMERA_PIXEL_LOWER_LEFT = new Point(0,0);
	static public Point CAMERA_PIXEL_LOWER_RIGHT = new Point(OpenGL_WIDTH,0);
	static public Point CAMERA_PIXEL_UPPER_RIGHT = new Point(OpenGL_WIDTH,OpenGL_HEIGHT);
	static public Point CAMERA_PIXEL_UPPER_LEFT = new Point(0,OpenGL_HEIGHT);
	
	static public int Camera_WIDTH_Px = 1024; //??
	static public int Camera_Height_Px = 600;
	
	static public float ratioX =1, ratioY =1;
	
	/*
	 * Current computation assumes both the camera and openGL output are Horizontal Rectangles
	 */

	/**
	 * After the required points have been provided, do some math
	 */
	static public void computeTransformation(){
		ratioX = OpenGL_WIDTH/(CAMERA_PIXEL_LOWER_RIGHT.getX()-CAMERA_PIXEL_LOWER_LEFT.getX());
		ratioY = OpenGL_HEIGHT/(CAMERA_PIXEL_UPPER_LEFT.getY()-CAMERA_PIXEL_LOWER_LEFT.getY());
	}

	static public org.lwjgl.util.Point mapToOpenGL(Point pos){
		return new org.lwjgl.util.Point(mapXToOpenGL(pos.getX()), mapYToOpenGL(pos.getY()));
	}

	static private int mapXToOpenGL(int x){
		int xGL = (int) ((x-CAMERA_PIXEL_LOWER_LEFT.getX())*ratioX);
		if (xGL > OpenGL_WIDTH) //Point outside of openGL display
			System.out.println("Point outside openGL window");
		return(xGL);
	}

	static private int mapYToOpenGL(int y){
		int yGL = (int) ((y-CAMERA_PIXEL_LOWER_LEFT.getY())*ratioY);
		if (yGL > OpenGL_HEIGHT) //Point outside of openGL display
			System.out.println("Point outside openGL window");
		return(yGL);
	}
}