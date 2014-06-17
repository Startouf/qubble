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
	static public int OpenGL_WIDTH = 1920;
	static public int OpenGL_HEIGHT = 1080;

	/*
	 * Les points doivent être donnés par le module image
	 * <html> !! ON FILME LE MIROIR
	 */
	static public Point CAMERA_PIXEL_LOWER_LEFT = new Point(0,0);
	static public Point CAMERA_PIXEL_LOWER_RIGHT = new Point(OpenGL_WIDTH,0);
	static public Point CAMERA_PIXEL_UPPER_RIGHT = new Point(OpenGL_WIDTH,OpenGL_HEIGHT);
	static public Point CAMERA_PIXEL_UPPER_LEFT = new Point(0,OpenGL_HEIGHT);
	
	static public int Camera_WIDTH_Px = 1280; //??
	static public int Camera_Height_Px = 720;
	
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

	/**
	 * Convert to OpenGL Coords system
	 * (get the equivalent of the OpenGL pixel for the given camera pixel)
	 * @param pos camera Pixel Point
	 * @return OpenGL position (lwjgl.util.point)
	 */
	static public org.lwjgl.util.Point mapToOpenGL(Point pos){
//		int x = pos.getX() - CAMERA_PIXEL_UPPER_LEFT.getX();
//		int y = pos.getY() - CAMERA_PIXEL_UPPER_LEFT.getY();
//		int yGL = pos.getY() - CAMERA_PIXEL_LOWER_LEFT.getY();
//		
//		int dBordRect = Math.abs(CAMERA_PIXEL_LOWER_LEFT.getX()-CAMERA_PIXEL_UPPER_LEFT.getX()) 
//				* Math.abs(y - CAMERA_PIXEL_LOWER_LEFT.getY()) 
//				/ Math.abs(CAMERA_PIXEL_UPPER_LEFT.getY() 
//						- CAMERA_PIXEL_LOWER_LEFT.getY());
//		int dBordPoint = Math.abs(x-CAMERA_PIXEL_LOWER_LEFT.getX()) + dBordRect; 
//		int dBordBord = 2 * dBordRect + CAMERA_PIXEL_LOWER_RIGHT.getX() - CAMERA_PIXEL_LOWER_LEFT.getX();
//		int dRectBord = CAMERA_PIXEL_LOWER_LEFT.getX() - CAMERA_PIXEL_UPPER_LEFT.getX() - dBordRect;
//		int xGL = (x -dRectBord) * dBordPoint/dBordBord;
		
//		float fy =  (float)(CAMERA_PIXEL_LOWER_LEFT.getX()-CAMERA_PIXEL_UPPER_LEFT.getX())*(float)y
//				/(float)(CAMERA_PIXEL_LOWER_LEFT.getY()-CAMERA_PIXEL_UPPER_LEFT.getY());
//		int xGL = (int) ((float)(x-fy)/(float)(OpenGL_WIDTH-2*fy)*(float)OpenGL_WIDTH);
//		xGL = Camera_WIDTH_Px - xGL;
		// 
		float xC = pos.getX();
		float yC = pos.getY();
		float yLL = CAMERA_PIXEL_LOWER_LEFT.getY(), yUL = CAMERA_PIXEL_UPPER_LEFT.getY();
		float xLL = CAMERA_PIXEL_LOWER_LEFT.getX(), xUL= CAMERA_PIXEL_UPPER_LEFT.getX();
		float xUR = CAMERA_PIXEL_UPPER_RIGHT.getX();
		
//		float yG = yUL - yC - yLL;
//		float dx = xLL - xUL;
//		float DY = yUL - yLL;
//		
//		float fY = (yG*dx) /(yUL-yLL);
//		float y2 = yC - yLL;
//		float x2 = xC-xUL-2*fY;
//		float x3 = xUR - xUL - 2*fY;
		
		
		// Version Eric 1
//		// Hauteur de la table par la caméra
//		float tableY = yLL-yUL;
//		
//		float tableX = xUR - xUL; 
//		
//		// Calcul de dy
//		float dy = yC - yUL;
//		// Calcul de dx
//		float offsetX = (dy/tableY)*(yLL-yUL);
//		
//		float longueurX = tableX - 2*offsetX;
//		
//		
//		int xGL = (int)(OpenGL_WIDTH *(1f- (xC-offsetX-xUL)/longueurX));
//		int yGL = (int)(  (dy/tableY) *(float)OpenGL_HEIGHT);
		
		// Version Eric 2 : Y reste constant et X varie en fct de X
		// (dX/dY) * Yc = offset de X causé par la déformation optique
		float offsetX = ((xLL - xUL)/(yLL - yUL)) * yC;
		
		// Xc - PtOrigine - offsetX = longueur X avec comme origine le bord de la table déformée
		float xTable = xC - offsetX - xUL;
		
		// Longueur Max de la table = xUR-xUL/ Longueur réelle en fct de Y : Lmax - 2*offsetX
		// Logueur X sous openGL / longuer réelle => Ratio pour passer de coord réelle > coord OpenGL
		
		int xGL = (int) (xTable * (OpenGL_WIDTH/(xUR-xUL-2*offsetX)));
		int yGL = (int) ((yC - yUL) * OpenGL_HEIGHT / (yLL - yUL));
		
		System.out.println ("("+pos.getX()+", " + pos.getY() +") camera --> (" + xGL + ", " + yGL+") openGL");
		org.lwjgl.util.Point point = new org.lwjgl.util.Point (xGL, yGL);
		return point;
		
	}

/*	static private int mapXToOpenGL(int x){
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
	}*/
}
