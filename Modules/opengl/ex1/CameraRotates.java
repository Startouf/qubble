package ex1;

import static org.lwjgl.opengl.GL11.*;
import static routines.Squares.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.Point;
import org.lwjgl.util.glu.GLU;

/**
 * @author Cyril
 * Ex 1.5: 
     - Sans changer les coordonnées de vos objets, animer le point de vue de votre caméra virtuelle
     - manuellement via le clavier (fleches de direction)
     - de maniere automatique en faisant un tour par seconde
 *
 */
public class CameraRotates
{
private static int WIDTH = 800, HEIGHT = 600;
	
	private Point coords = new Point(200,200);
	private int angle = 0, angleUser = 0;
	
	private int fps =0;
	private long lastFrameTime = getTime();
	private long delta;
	private long lastFPSDisplay = getTime(); 
	public long sleep;
	
	public void start(){
		initDisplay();		
		
		while (!Display.isCloseRequested()){
			glClear(GL_COLOR_BUFFER_BIT | 
					GL_DEPTH_BUFFER_BIT);
			glEnable(GL_CULL_FACE);
			
			initGL();
			viewTransform();
			renderGL();
			
			initGL2();
			view2Transform();
			renderGL();
			
			pollInputs();
			updateFPSDelta();
			
			Display.update(); 
			Display.sync(60);
		}
		Display.destroy();
	}
	
	private void viewTransform(){ //User moves it
		glTranslatef(+coords.getX()+100, +coords.getY()+100, -105);
		glRotated(angleUser, 0.0, 1.0, 0.0);
		glTranslatef(-coords.getX()-100, -coords.getY()-100, +105);
	}
	
	private void view2Transform(){ //Auto move
		glTranslatef(+coords.getX()+100, +coords.getY()+100, -105);
		glRotated(angle, 0.0, 1.0, 0.0);
		glTranslatef(-coords.getX()-100, -coords.getY()-100, +105);
	}

	private void renderGL(){
		drawCubef(coords.getX(), coords.getY(), -5, 200);
	}
	
	private void renderGL2(){
		drawCubef(coords.getX(), coords.getY(), -5, 200);
	}
	
	private void pollInputs(){
		if (Mouse.isButtonDown(0)){ //Will generate as many lines as FPS
			int x = Mouse.getX();
			int y = Mouse.getY();
			
			System.out.println("Mouse @ (" + x+", " +y+")");
		}
		
		while(Keyboard.next()){ //Will generate only one line per event
			if (Keyboard.getEventKeyState()){ //Key pressed
				if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT){
					System.out.println("Right pressed");
					angleUser = (int) ((angleUser + 15) % 360);
				}
				else if (Keyboard.getEventKey() == Keyboard.KEY_LEFT){
					System.out.println("LEFT pressed");
					angleUser = (int) ((angleUser - 15) % 360);
				}
				else if (Keyboard.getEventKey() == Keyboard.KEY_UP){
					System.out.println("LEFT pressed");
					coords.translate(0, (int) delta);
				}
				else if (Keyboard.getEventKey() == Keyboard.KEY_DOWN){
					System.out.println("LEFT pressed");
					coords.translate(0, (int) -delta);
				}
			}
			else{ //key released
				if (Keyboard.getEventKey() == Keyboard.KEY_A){
					System.out.println("A released");
				}
			}
		}
	}
	
	private void updateFPSDelta(){
		fps++;
		delta = getTime() - lastFrameTime;
		angle = (int) ((angle + delta/10) % 360);
		lastFrameTime = getTime();
		if (getTime()-lastFPSDisplay > 1000){
			System.out.println("FPS : "+ fps);
			fps = 0;
			lastFPSDisplay = lastFrameTime;
		}
	}
	
	private long getTime(){ //Sys.getTime returns time in nanoticks (with TimerResolutions = ticks/sec)
		return (Sys.getTime()*1000)/Sys.getTimerResolution();
	}
	
	private void initDisplay(){
		try{
			Display.setDisplayMode(new DisplayMode(800,600));
			Display.create();
		} catch (LWJGLException e){
			e.printStackTrace();
		}
	}
	
	private void initGL(){
		glViewport(0,0,WIDTH/2, HEIGHT);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 800, 0, 600, 300, -300);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		GLU.gluLookAt(50f, 50f, 0, 35f, 35f, -50f, 0f, 1f, 0f);
	}
	
	private void initGL2(){
		glViewport(WIDTH/2,0,WIDTH/2, HEIGHT);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 800, 0, 600, 300, -300);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		GLU.gluLookAt(50f, 50f, 0, 35f, 35f, -50f, 0f, 1f, 0f);
	}
	
	public static void main(String[] args){
        CameraRotates app = new CameraRotates();
        app.start();
    }
}
