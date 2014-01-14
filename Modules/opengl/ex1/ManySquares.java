package ex1;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;
import org.lwjgl.util.Point;

//The static imports all classes without the need to write class name each time (here GL11)
import static org.lwjgl.opengl.GL11.*;

public class ManySquares
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
			Display.update(); 
			Display.sync(60); 
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
		glOrtho(0, 800, 0, 600, 0, 300);	//Orthonormal projection
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
		squareFromQuad(coords.getX(), coords.getY(), 200);
		squareFromFan(4*coords.getX(), coords.getY(), 200);
		squareFromStrip(coords.getX(), (float)3.1*coords.getY(), 200);
		cube(4*coords.getX(), (float)3.1*coords.getY(), -5, 200);
	}
	
	private long getTime(){ //Sys.getTime returns time in nanoticks (with TimerResolutions = ticks/sec)
		return (Sys.getTime()*1000)/Sys.getTimerResolution();
	}
	
	private void squareFromQuad(float x, float y, float s){
        glBegin(GL_QUADS);
        glNormal3f(0,0,1);
        glVertex2f(x, y);
        glVertex2f(x+s, y);
        glVertex2f(x+s, y+s);
        glVertex2f(x, y+s);
        glEnd();
    }
	
	private void squareFromFan(float x, float y, float s){
		glBegin(GL_TRIANGLE_FAN);
        glNormal3f(0,0,1);
		glVertex2f(x+(float)s/2,y+(float)s/2); //center of quad
		glVertex2f(x+s, y);
		glVertex2f(x+s,y+s);
		glVertex2f(x,y+s);
		glVertex2f(x,y);
		glVertex2f(x+s,y);
		glEnd();
	}
	
	private void squareFromStrip(float x, float y, float s){
		glBegin(GL_TRIANGLE_STRIP);
		glNormal3f(0,0,1);
		glVertex2f(x,y);
		glVertex2f(x+s,y);
		glVertex2f(x,y+s);
		glVertex2f(x+s,y+s);
		glEnd();
	}
	
	private void cube(float x, float y, float z, float s){
		//Use Matrix
		glEnableClientState(GL_VERTEX_ARRAY);
		FloatBuffer vertexArray = BufferUtils.createFloatBuffer(3*8);
		float[] vertices = {
				//4 sommets de devant
				x,y,z,
				x+s,y,z,
				x+s,y+s,z,
				x,y+s,z,
				//4 sommets de derrière
				x,y,z+s,
				x+s,y,z+s,
				x+s,y+s,z+s,
				x,y+s,z+s
		};
		vertexArray.put(vertices);
		glVertexPointer(3, 0, vertexArray);
		
		glBegin(GL_QUAD_STRIP);
		glArrayElement(0);
		glArrayElement(3);
		glArrayElement(1);
		glArrayElement(2);
		glColor3f(1f,1f,0f);
		glArrayElement(5);
		glArrayElement(6);
		glColor3f(0f,1f,1f);
		glArrayElement(4);
		glArrayElement(7);
		glColor3f(1f,0f,1f);
		glArrayElement(0);
		glArrayElement(3);
		glEnd();
		
		glBegin(GL_QUADS); //could be a separate function but this one is a square on the xoz plane
		glColor3f(1f,0,0);
		glArrayElement(0);
		glArrayElement(1);
		glArrayElement(4);
		glArrayElement(5);
		glEnd();
		
		glBegin(GL_QUADS);
		glColor3f(0,1f,0);
		glArrayElement(3);
		glArrayElement(2);
		glArrayElement(6);
		glArrayElement(7);
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
        ManySquares app = new ManySquares();
        app.start();
    }
}

