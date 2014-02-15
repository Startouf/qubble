package ex2;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.Point;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.opengl.*;
import org.newdawn.slick.util.ResourceLoader;

import routines.Time;

import static routines.Buffers.*;
import static routines.Init.*;
import static routines.Squares.*;

/**
 * @author Cyril
 *TODO
 * Ex 2.1: 
     - chargez une image à l'aide de l'objet TextureLoader et dessinez l'image sous forme d'un rectangle
     !! vous ferez attention à ne pas déformer l'image
     pour l'utilisation de textures via les shaders, le tutoriel de lwjgl à ce sujet est très bien
 */
public class ManyTextures
{
	private Texture texture;
	private Point coords = new Point(200,200);
	private static int WIDTH = 800;
	private static int HEIGHT = 600;

	public void start(){
		initDisplay();
		glEnable(GL_CULL_FACE);
		glEnableClientState(GL_NORMAL_ARRAY);
		glEnableClientState(GL_VERTEX_ARRAY);
		initTextures();
		glEnable(GL_TEXTURE_2D);                         
        	// enable alpha blending
        	glEnable(GL_BLEND);
        	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		while (!Display.isCloseRequested()){
			glClear(GL_COLOR_BUFFER_BIT | 
					GL_DEPTH_BUFFER_BIT);
			initGL();
			
			viewTransform();

			renderGL();

			Display.update(); 
			Display.sync(60);
		}
		Display.destroy();
	}
	private void initTextures(){
		try {
			// load texture from PNG file
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("data/opengl/trollface.jpg"));
 
			System.out.println("Texture loaded: "+texture);
			System.out.println(">> Image width: "+texture.getImageWidth());
			System.out.println(">> Image height: "+texture.getImageHeight());
			System.out.println(">> Texture width: "+texture.getTextureWidth());
			System.out.println(">> Texture height: "+texture.getTextureHeight());
			System.out.println(">> Texture ID: "+texture.getTextureID());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void renderGL(){
		drawCubeWithTexture(coords.getX(), coords.getY(), -30f, 200f, texture);
	}

	private void viewTransform(){
		glTranslatef(+coords.getX()+100, +coords.getY()+100, -105);
		glRotated(Time.uniformRotation(), 0.0, 1.0, 0.0);
		glTranslatef(-coords.getX()-100, -coords.getY()-100, +105);
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
		ManyTextures app = new ManyTextures();
		app.start();
	}
}