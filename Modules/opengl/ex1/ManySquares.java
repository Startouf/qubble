package ex1;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;
import org.lwjgl.util.Point;
import org.lwjgl.util.glu.GLU;

//The static imports all classes without the need to write class name each time (here GL11)
import static org.lwjgl.opengl.GL11.*;

//Routines are there :
import static routines.Squares.*;

/**
 * @author Cyril
 Ex 1.2: 
 - Dessinez un carré
 - Dessinez un carré à l'aide d'un "éventail" de triangles (Triangle Fan), puis à l'aide d'un "ruban" (Triangle Strip)
 - Dessinez un cube au lieu d'un triangle, avec une couleur par face, puis une sphère ou tout autre objet

 Il est possible (recommandé) de passer aux exercices 3.1 -> 3.3 sur les shaders pour les groupes travaillant sur les shaders

 
 Ex 1.3: 
 - modifier cette routine pour afficher votre scène d'au moins deux points de vues en même temps, en partageant 
 votre zone d'affichage en plusieurs zones via glViewport. Vous devrez rendre votre scène une fois par point de 
 vue. Ceci vous sera utile dans votre projet pour gérer des zones d'affichages différentes, pour débugger votre contenu
 ou afficher des barres d'outils indépendantes de votre transformation de point de vue
 *
 */
public class ManySquares
{
	private Point coords = new Point(100,200);
	private static int WIDTH = 800;
	private static int HEIGHT = 600;
	
	public void start(){
		initDisplay();
		glEnable(GL_CULL_FACE);

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
		glScalef(1f, 0.5f, 1f);
		glLoadIdentity();
		glScalef(1f, 0.5f, 1f);
	}
	
	private void initGL2(){
		glViewport(WIDTH/2,0,WIDTH/2, HEIGHT);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 800, 0, 600, 0, -400);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glScalef(1f, 0.5f, 1f);
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
		drawCubef(4*coords.getX(), (float)3.1*coords.getY(), -5, 200);
	}
	
	public static void main(String[] args){
        ManySquares app = new ManySquares();
        app.start();
    }
}

