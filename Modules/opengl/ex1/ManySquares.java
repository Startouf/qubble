package ex1;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;
import org.lwjgl.util.Point;
import org.lwjgl.util.glu.GLU;

//The static imports all classes without the need to write class name each time (here GL11)
import static org.lwjgl.opengl.GL11.*;

//Routines are there :
import static routines.SquareRoutines.*;

public class ManySquares
{
	private Point coords = new Point(100,100);
	private static int WIDTH = 800;
	private static int HEIGHT = 600;
	
	public void start(){
		initDisplay();		

		while (!Display.isCloseRequested()){
			glClear(GL_COLOR_BUFFER_BIT | 
					GL_DEPTH_BUFFER_BIT);
			initGL();
			renderGL();
			initGL2();
			renderGL();
			Display.update(); 
			Display.sync(60); 
		}
		Display.destroy();
	}
	
	private void initGL(){
		glViewport(0,0,WIDTH/2, HEIGHT);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 800, 0, 600, 0, -300);	//Othronormal projection
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}
	
	private void initGL2(){
		glViewport(WIDTH/2,0,WIDTH/2, HEIGHT);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 800, 0, 600, 0, -300);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		GLU.gluLookAt(50f, 50f, 0, 35f, 35f, -50f, 0f, 1f, 0f);

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
		
		//Set color of quad (R,G,B,A)
		glColor3f(0.5f,0.5f,1.0f);
		
		//draw
		squareFromQuad(coords.getX(), coords.getY(), 200);
		squareFromFan(4*coords.getX(), coords.getY(), 200);
		squareFromStrip(coords.getX(), (float)3.1*coords.getY(), 200);
		cube(4*coords.getX(), (float)3.1*coords.getY(), -5, 200);
	}
	
	public static void main(String[] args){
        ManySquares app = new ManySquares();
        app.start();
    }
}

