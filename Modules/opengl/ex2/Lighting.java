package ex2;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.Point;
import org.lwjgl.util.glu.GLU;

import routines.Time;

import static routines.Buffers.*;
import static routines.Init.*;
import static routines.Squares.*;

/**
 * @author Cyril
 * Ex 2.3: 
     - Petit travail biblio: comment marche le modèle de lumière en OpenGL. Faites en particulier attention à vos définitions de normales !
     - Placez une lumière dans votre scène (sans texture) éclairant un cube placé au centre de la scène
     - modifiez les parametres de materiel pour changer l'apparence de votre cube
     - faites tourner votre cube ou votre lumière pour visualiser les changements liés à l'éclairage.
 *
 */
public class Lighting
{
	private Point coords = new Point(200,200);
	private static int WIDTH = 800;
	private static int HEIGHT = 600;

	public void start(){
		initDisplay();
		glEnable(GL_CULL_FACE);
		
		while (!Display.isCloseRequested()){
			glClear(GL_COLOR_BUFFER_BIT | 
					GL_DEPTH_BUFFER_BIT);
			initGL();
			initLights();
			
			viewTransform();
			
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
		drawCubeWithNormals(coords.getX(), coords.getY(), -30f, 200f);
	}
	
	private void viewTransform(){
		glTranslatef(+coords.getX()+100, +coords.getY()+130, -105);
		glRotated(Time.uniformRotation(), 0.0, 1.0, 0.0);
		glTranslatef(-coords.getX()-100, -coords.getY()-130, +105);
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
        Lighting app = new Lighting();
        app.start();
    }
}
