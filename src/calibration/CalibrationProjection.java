package calibration;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import routines.Squares;

public class CalibrationProjection implements Runnable{
	
	private static float thickness = 20f;
	
	private static int WIDTH = Calibrate.OpenGL_WIDTH;
	private static int HEIGHT = Calibrate.OpenGL_HEIGHT;
	
	public void start(){
		initDisplay();
		glEnable(GL_CULL_FACE);

		while (!Display.isCloseRequested()){
			glClearColor(1f,1f,1f,1f);
			glClear(GL_COLOR_BUFFER_BIT | 
					GL_DEPTH_BUFFER_BIT);
			initGL();
			renderGL();
			Display.update(); 
			Display.sync(60); 
		}
		Display.destroy();
	}
	
	private void initGL(){
		glViewport(0,0,WIDTH, HEIGHT);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, 0, HEIGHT, 1, -300);	//Orthonormal projection
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glScalef(1f, 1f, 1f);
	}
	
	private void initDisplay(){
		try{
			Display.setDisplayMode(new DisplayMode(WIDTH,HEIGHT));
			Display.create();
		} catch (LWJGLException e){
			e.printStackTrace();
		}
	}
	
	private void renderGL(){
		glColor3f(0f,0f,0f);
		
		Squares.rectangleHorizontal2f(0f, (float)WIDTH, 0f, thickness);
		Squares.rectangleHorizontal2f(0f, (float)WIDTH, (float)HEIGHT-thickness, (float)HEIGHT);
		Squares.rectangleHorizontal2f(0f, thickness, 0f, (float)HEIGHT);
		Squares.rectangleHorizontal2f((float)WIDTH-thickness, (float)WIDTH, 0f, HEIGHT);
	}
	
	public static void main(String[] args){
        CalibrationProjection app = new CalibrationProjection();
        app.start();
    }

	@Override
	public synchronized void run() {
		main(null);
	}
}
