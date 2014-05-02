package calibration;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Frame;

import opengl.BaseRoutines;
import opengl.InitRoutines;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import routines.Squares;

public class CalibrationProjection implements Runnable{
	
	private static float thickness = 20f;
	
	private static int WIDTH = Calibrate.OpenGL_WIDTH;
	private static int HEIGHT = Calibrate.OpenGL_HEIGHT;
	private boolean done = false;
	private long time = Sys.getTime();
	
	public void start(){
		Frame frame = InitRoutines.initDisplayOnSecondDevice(WIDTH, HEIGHT);
		glEnable(GL_CULL_FACE);

		while (!Display.isCloseRequested() && !Thread.interrupted() && !done){
			glClearColor(0f,0f,0f,1f);
			glClear(GL_COLOR_BUFFER_BIT | 
					GL_DEPTH_BUFFER_BIT);
			time();
			initGL();
			renderGL();
			Display.update(); 
			Display.sync(60); 
		}
		Display.destroy();
		if (frame != null){
			frame.dispose();
		}
	}
	
	private void time(){
		if (BaseRoutines.getDt(time) >= 1500){
			done = true;
		}
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
		glColor3f(1f,1f,1f);
		//Show a white border
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
