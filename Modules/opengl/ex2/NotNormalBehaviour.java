package ex2;

import static org.lwjgl.opengl.GL11.*;
import static routines.Buffers.FB;
import static routines.Init.initDisplay;
import static routines.Squares.*;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.Point;
import org.lwjgl.util.glu.GLU;

import routines.Time;
import routines.someMath;

//Petit jeu de mot sur le titre de la classe ^^

public class NotNormalBehaviour
{
	private Point coords = new Point(400,400);
	private static int WIDTH = 800;
	private static int HEIGHT = 600;

	public void start(){
		initDisplay();
		glEnable(GL_CULL_FACE);
		glEnableClientState(GL_NORMAL_ARRAY);
		glEnableClientState(GL_VERTEX_ARRAY);
		
		while (!Display.isCloseRequested()){
			glClear(GL_COLOR_BUFFER_BIT | 
					GL_DEPTH_BUFFER_BIT);
			initGL();
			initLights();
			
			renderGL();
			
			Display.update(); 
			Display.sync(60);
		}
		Display.destroy();
	}
	
	private void initLights(){
		FloatBuffer mat_specular = FB(new float[] { 1f, 1f, 1f, 1f });
		Float mat_shininess = 50f;
		FloatBuffer light_position = FB(new float[]{ 1f, 30f, 1f, 0f });
		glClearColor (0, 0, 0, 0);
		glShadeModel (GL_SMOOTH);

		glMaterial(GL_FRONT, GL_SPECULAR, mat_specular);
		glMaterialf(GL_FRONT, GL_SHININESS, mat_shininess);
		glLight(GL_LIGHT0, GL_POSITION, light_position);

		glEnable(GL_LIGHTING);
		glEnable(GL_LIGHT0);
		glEnable(GL_DEPTH_TEST);
	}
	
	private void renderGL(){
		drawCubeWithWeirdNormals(100f, 100f, -30f, 200f);
	}

	private void drawCubeWithWeirdNormals(float x, float y, float z, float s){

		float[][] v = {
				{x,y,-z}, {x+s,y,-z}, {x+s,y+s,-z}, {x,y+s,-z},
				{x,y,-z-s}, {x+s,y,-z-s}, {x+s,y+s,-z-s}, {x,y+s,-z-s}
				};
		
		float[] n;
		
		glColor3f(0,0,1f); //front face :blue 
		n = someMath.getRandomNormal(); //:P you asked for it !
		square3DWithNormal(v[0],v[1],v[2],v[3],n);
		
		glColor3f(1f,0,0); //top face :red
		n = someMath.getRandomNormal();
		square3DWithNormal(v[3], v[2], v[6], v[7],n);
		
		glColor3f(0,1f,0); //back face : green
		n = someMath.getRandomNormal();
		square3DWithNormal(v[5],v[4],v[7],v[6],n);
		
		glColor3f(1f,1f,0); //bottom face : yellow
		n = someMath.getRandomNormal();
		square3DWithNormal(v[1],v[0], v[4], v[5],n);

		glColor3f(1f,0,1f); //left face magenta
		n = someMath.getRandomNormal();
		square3DWithNormal(v[0], v[3], v[7], v[4],n);

		glColor3f(0,1f,1f); //right face Cyan
		n = someMath.getRandomNormal();
		square3DWithNormal(v[1], v[5], v[6], v[2],n);
	}
	
	private void initGL(){
		glViewport(0,0,WIDTH, HEIGHT);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 800, 0, 600, 300, -300);	//Othronormal projection
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		GLU.gluLookAt(50f, 50f, 0, 35f, 35f, -50f, 0f, 1f, 0f);
	}
	
	public static void main(String[] args){
        NotNormalBehaviour app = new NotNormalBehaviour();
        app.start();
    }
}
