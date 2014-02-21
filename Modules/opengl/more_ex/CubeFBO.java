package more_ex;

import static org.lwjgl.opengl.GL11.*;
import static routines.Init.HEIGHT;
import static routines.Init.WIDTH;
import static routines.Init.initDisplay;
import static routines.Init.initLighting;

import java.nio.FloatBuffer;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.UnicodeFont;

import routines.Buffers;
import routines.FBO;
import routines.Fonts;
import routines.IBO;
import routines.Squares;
import routines.VBO;

/**
 * 
 * @author Cyril
 * Exercice perso
 * Purpose : render some cubes within an FBO 
 * ... and then make a texture from it
 * 
 * Then, use this texture to draw a quad. It is possible to modify the vertices of this quad to tweak the image
 * (For example with the arrow keys)
 * Used for image registration (recalage d'images)
 * 
 * Aim in this class : stretch with numpad keys
 *
 */
public class CubeFBO
{
	private int FBO_WIDTH = 350, FBO_HEIGHT = 350;
	private int side = 200;
	private int[] FBO_IDs;
	private int[][] cubeIBO;
	private int[][] stretchableSquareIBO;
	private TrueTypeFont TNR; 
	private float pos = 100f;
	private float[] vertices;
	private boolean modified = false;
	
	private void start(){
        initDisplay();
        glEnable(GL_CULL_FACE);
        GL11.glShadeModel(GL11.GL_SMOOTH); 
        loadFonts();
        loadIBOs();
        FBO_IDs = FBO.makeFBO(FBO_WIDTH,FBO_HEIGHT);
        
        while(!Display.isCloseRequested()){   
        	initViewFBO();
            glClear(GL_COLOR_BUFFER_BIT | 
					GL_DEPTH_BUFFER_BIT);
        	viewTransform();
        	renderFBO();					//FBO ViewInit done inside renderFBO
            
            initView();
            glClear(GL_COLOR_BUFFER_BIT | 
					GL_DEPTH_BUFFER_BIT);
        	stretchWithNumPad();
        	updateVBO();
        	
            viewTransform();
            render();
            
            Display.update();
            Display.sync(60);
        }
        
        Display.destroy();
    }
	
	private void renderFBO(){
		FBO.bindFBO(FBO_IDs[0]);
		glPushMatrix();
		GL11.glTranslatef(100f,100f,0f);
		IBO.drawTriangles3f(cubeIBO);
		glPopMatrix();
		FBO.unbindFBO();
	}

	private void render(){   
		//Rendering a textured square with the FBO
		glColor3f(1f,1f,1f);
		glEnable(GL_TEXTURE_2D);
		//Squares.squareFromFan(100f, 100f, 200);	
		IBO.drawTexturedTriangles3f(stretchableSquareIBO[0], stretchableSquareIBO[1][0], FBO_IDs[1]);	
		glBindTexture(GL11.GL_TEXTURE_2D, 0);
		
		glDisable(GL_TEXTURE_2D);

		//Normal render without FBO :
		glPushMatrix();
		GL11.glTranslatef(400f,400f,0f);
		IBO.drawTriangles3f(cubeIBO);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		//Font not rendering ?
		Fonts.render(TNR, 350f, 150f, "The cube loaded in the FBO", Color.white);
		glPopMatrix();

	}
	
	private void updateVBO(){
		if (modified){
			VBO.overwrite(stretchableSquareIBO[0][0], vertices);
			modified = false;
		}
	}
	
	private void stretchWithNumPad(){
		while(Keyboard.next()){ //Will generate only one line per event
			if (Keyboard.getEventKeyState()){ //Key pressed
				modified = true;
				switch(Keyboard.getEventKey()){
				case Keyboard.KEY_NUMPAD1: //move bottom-left edge (0) to bottom-left
					vertices[0] -= 25f;
					vertices[1] -= 25f;
					break;
				case Keyboard.KEY_NUMPAD2: //move bottom edge (1) to bottom
					vertices[(3*1)+1] -= 25f;
					break;
				case Keyboard.KEY_NUMPAD3: //move bottom-right edge (2) to bottom-right
					vertices[3*2] += 25f;
					vertices[3*2+1] -= 25f;
					break;
				case Keyboard.KEY_NUMPAD4: //move left edge (3) to left
					vertices[3*3] -= 25f;
					break;
				// Center vertex doesn't move !
				case Keyboard.KEY_NUMPAD6: //move right edge (5) to right
					vertices[3*5] += 25f;
					break;
				case Keyboard.KEY_NUMPAD7: //move top-left edge (6) to top-left
					vertices[3*6] -= 25f;
					vertices[3*6+1] += 25f;
					break;
				case Keyboard.KEY_NUMPAD8: //move top edge (7) to top
					vertices[3*7+1] += 25f;
					break;
				case Keyboard.KEY_NUMPAD9: //move top-right edge (8) to top-right
					vertices[3*8] += 25f;
					vertices[3*8+1] += 25f;
					break;
				}
			}
		}
	}
	
	private void viewTransform(){
		GLU.gluLookAt(50f, 50f, 0, 35f, 35f, -50f, 0f, 1f, 0f);
	}
	
	private void loadIBOs(){
		cubeIBO = IBO.loadColoredCubeTriangles3f(0f, 0f, 0f, (float)side, 
				new float[][]{	{1f,0.25f,1f},	{1f,1f,0f},
								{0f,1f,1f},	{1f,0f,1f},
								{0f,0.5f,1f},	{1f,1f,0.5f}
		});
		
		//Original stretchable cube vertices positions
		float x=150f, y=150f, z=25f, s=200f;
		stretchableSquareIBO = IBO.loadStretchableSquareIBO2f(x, y, z, s);
		vertices = new float[] {x,y,z,	x+s/2,y,z,	x+s,y,z,	
				x,y+s/2,z,	x+s/2,y+s/2,z,	x+s,y+s/2,z,
				x,y+s,z,	x+s/2,y+s,z,	x+s,y+s,z};
	}
	
	private void loadFonts(){
		TNR = Fonts.TimesNewsRomanTTF();
	}

	private void initViewFBO(){    
		glViewport(0, 0, FBO_WIDTH, FBO_HEIGHT);
		glMatrixMode(GL11.GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, FBO_WIDTH, 0, FBO_HEIGHT, 300, -600);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}

	private void initView(){    
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