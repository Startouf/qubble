package ex2;

import static org.lwjgl.opengl.GL11.*;
import static routines.Buffers.FB;
import static routines.Init.*;
import static routines.Squares.*;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.Point;
import org.lwjgl.util.glu.GLU;

import routines.UserInputs;


/**
 * @author Cyril
 * 
 * Petit jeu de mot sur le nom de la classe ^^"
 * Flèches directionnelles pour augmenter le changement des normales
 * 
 * Ex 2.4:
     - Sans modifier vos paramètres de lumières (source de lumière et materiel de l'objet), 
     animer ***légèrement*** la normale de chaque face du cube (une légère rotation autour de la vraie normale)
     et observer le résultat
 *
 */
public class NotNormalBehaviour
{
	private Point coords = new Point(200,200);
	private float normalsDelta =2f, delta = 1f;

	public void start(){
		initDisplay();
		glEnable(GL_CULL_FACE);
		glEnableClientState(GL_NORMAL_ARRAY);
		glEnableClientState(GL_VERTEX_ARRAY);
		
		while (!Display.isCloseRequested()){
			glClear(GL_COLOR_BUFFER_BIT | 
					GL_DEPTH_BUFFER_BIT);
			userInput();
			System.out.println(normalsDelta);
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
		//(x,y,z, size, delta)
		//Find appropriate function for fluctuation and modify someMath.fluctuateNormal
		drawCubeWithFluctuatingNormals(coords.getX(), coords.getY(), -30f, 200f, 2, normalsDelta);
	}
	
	private void userInput(){
		//exponential increase in normals
		normalsDelta = UserInputs.incrementWithArrowKeys(normalsDelta, delta);
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
