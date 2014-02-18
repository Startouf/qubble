package more_ex;

import static org.lwjgl.opengl.GL11.*;
import static routines.Init.HEIGHT;
import static routines.Init.WIDTH;
import static routines.Init.initDisplay;
import static routines.Init.initLighting;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.UnicodeFont;

import routines.FBO;
import routines.Fonts;
import routines.IBO;
import routines.Squares;

/**
 * 
 * @author Cyril
 * Exercice perso
 * Purpose : render some cubes (With whatever way : VBO, IBO, shader...)
 * ... and then make a FBO from those
 * 
 * Then, it's possible to alter this FBO (stretch, compress...)
 * (For example with the arrow keys)
 *
 */
public class CubeFBO
{
	private int FBO_WIDTH = 600, FBO_HEIGHT = 600;
	private int side = 200;
	private int[] FBO_IDs;
	private int[][] cubeIBO;
	private UnicodeFont TNR; 
	
	private void start(){
        initDisplay();
        glEnable(GL_CULL_FACE);
        initLighting();
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        loadFonts();
        loadIBOs();
        FBO_IDs = FBO.makeFBO(FBO_WIDTH,FBO_HEIGHT);
        
        while(!Display.isCloseRequested()){   
            renderFBO();					//Includes ViewPort for FBO and glClear
            
            glClear(GL_COLOR_BUFFER_BIT | 
					GL_DEPTH_BUFFER_BIT);
        	initGL();
            viewTransform();
            render();
            
            Display.update();
            Display.sync(60);
        }
        
        Display.destroy();
    }
	
	private void renderFBO(){
		FBO.bindFBO(FBO_IDs[0], FBO_WIDTH, FBO_HEIGHT);
		IBO.drawTriangles3f(cubeIBO);
		FBO.unbindFBO();
	}
	
	private void render(){   
		glPushMatrix();
		GL11.glTranslatef(400f,400f,0f);
		IBO.drawTriangles3f(cubeIBO);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_SMOOTH); 
		Color.white.bind();
		TNR.drawString(350f, 350f, "The cube loaded in the FBO", Color.yellow);
		glPopMatrix();
		
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL11.GL_TEXTURE_2D, FBO_IDs[1]);
		Squares.square3DWithTexture(new float[]{0f, 0f, 0f},
									new float[]{200f, 0f, 0f},
									new float[]{200f, 200f, 0f},
									new float[]{0f, 200f, 0f});														

		glDisable(GL_TEXTURE_2D);
		glFlush ();
	}
	
	private void viewTransform(){
		GLU.gluLookAt(50f, 50f, 0, 35f, 35f, -50f, 0f, 1f, 0f);
	}
	
	private void loadIBOs(){
		cubeIBO = IBO.loadColoredCubeTriangles3f(0f, 0f, 0f, (float)side, 
				new float[][]{	{0f,1f,1f},	{1f,1f,0f},
								{0f,1f,1f},	{1f,1f,0f},
								{0f,1f,1f},	{1f,1f,0f}
		});
	}
	
	private void loadFonts(){
		TNR = Fonts.TimesNewRoman();
	}

	private void initGL(){    
		glViewport(0, 0, WIDTH, HEIGHT);
		glMatrixMode(GL11.GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 800, 0, 600, 300, -600);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}
	
	public static void main(String[] args){
		CubeFBO app = new CubeFBO();
		app.start();
	}
}