package opengl;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;
import org.lwjgl.util.Point;

//The static imports all classes without the need to write class name each time (here GL11)
import static org.lwjgl.opengl.GL11.*;

public class Ex1_1
{
	private Point coords = new Point(100,100);
	
	private int fps =0;
	private long lastFrameTime = getTime();
	private long delta;
	private long lastFPSDisplay = getTime(); 
	
	public void start(){
		initDisplay();		
		initGL();
		
		while (!Display.isCloseRequested()){
			renderGL();
			
			pollInputs();
			updateFPSDelta();
			Display.update(); //Handles double buffer and mouse/keyb poll
			Display.sync(60); //Handles FPS
		}
		Display.destroy();
	}
	
	private void updateFPSDelta(){
		fps++;
		delta = getTime() - lastFrameTime;
		lastFrameTime = getTime();
		if (getTime()-lastFPSDisplay > 1000){
			System.out.println("FPS : "+ fps);
			fps = 0;
			lastFPSDisplay = lastFrameTime;
		}
	}
	
	private void initGL(){
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 800, 0, 600, 1, -1);
		glMatrixMode(GL_MODELVIEW);
	}
	
	private void initDisplay(){
		try{
			Display.setDisplayMode(new DisplayMode(800,600));
			Display.create();
		} catch (LWJGLException e){
			e.printStackTrace();
		}
	}
	
	private void renderGL(){
		//Clear screen and depth buffer
		glClear(GL_COLOR_BUFFER_BIT | 
				GL_DEPTH_BUFFER_BIT);
		
		//Set color of quad (R,G,B,A)
		glColor3f(0.5f,0.5f,1.0f);
		
		//draw
		draw_square(coords.getX(), coords.getY(), 100f);
	}
	
	private long getTime(){ //Sys.getTime returns time in nanoticks (with TimerResolutions = ticks/sec)
		return (Sys.getTime()*1000)/Sys.getTimerResolution();
	}
	
	private void draw_square(float x, float y, float s){
        glBegin(GL_QUADS);
        //GL11.glNormal3f(0f,0f,1f);
        glVertex2f(x, y);
        glVertex2f(x+s, y);
        glVertex2f(x+s, y+s);
        glVertex2f(x, y+s);
        glEnd();
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
					coords.translate((int) delta,0);
				}
				else if (Keyboard.getEventKey() == Keyboard.KEY_LEFT){
					System.out.println("LEFT pressed");
					coords.translate((int) -delta, 0);
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
	
	public static void main(String[] args){
        Ex1_1 app = new Ex1_1();
        app.start();
    }
}

