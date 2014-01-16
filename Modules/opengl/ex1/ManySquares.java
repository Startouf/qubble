package ex1;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;
import org.lwjgl.util.Point;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.glu.GLU;

//The static imports all classes without the need to write class name each time (here GL11)
import static org.lwjgl.opengl.GL11.*;

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
		//Four faces with QUAD_Strip (front, top, back, bottom)
		//Normals must be sent before the vertex that finish a face
		float[][] v = {
				{x,y,-z}, {x+s,y,-z}, {x+s,y+s,-z}, {x,y+s,-z},
				{x,y,-z-s}, {x+s,y,-z-s}, {x+s,y+s,-z-s}, {x,y+s,-z-s}
				};
		
		glColor3f(0,0,1f); //front face :blue 
		square3D(v[0],v[1],v[2],v[3]);
		
		glColor3f(1f,0,0); //top face :red
		square3D(v[2], v[3], v[6], v[7]);
		
		glColor3f(0,1f,0); //back face : green
		square3D(v[4],v[5],v[6],v[7]);
		
		glColor3f(1f,1f,0); //bottom face : yellow
		square3D(v[0],v[1], v[5], v[4]);

		glColor3f(1f,0,1f); //left face magenta
		square3D(v[0], v[4], v[7], v[3]);

		glColor3f(0,1f,1f); //right face Cyan
		square3D(v[1], v[2], v[6], v[5]);
		
	}
	
	private void square3D(float[] v1, float[]v2, float[] v3, float[] v4){
		glBegin(GL_QUADS);
		glVertex3f(v1[0], v1[1], v1[2]);
		glVertex3f(v2[0], v2[1], v2[2]);
		glVertex3f(v3[0], v3[1], v3[2]);
		glVertex3f(v4[0], v4[1], v4[2]);
		glEnd();
	}
	
	public static void main(String[] args){
        ManySquares app = new ManySquares();
        app.start();
    }
}

