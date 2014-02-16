package more_ex;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glViewport;
import static routines.IBO.drawIBOTriangles3f;
import static routines.Init.HEIGHT;
import static routines.Init.WIDTH;
import static routines.Init.initDisplay;
import static routines.Init.initLighting;
import static routines.Transformations.putObjectAt;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.opengl.Texture;

import routines.FBO;
import routines.IBO;
import routines.Textures;
import routines.Time;

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
	private Point coords = new Point(200,200);
	private float z = -100;
	private int side = 200;
	private int[][] texturedCubeIboIDs = null;
	private final String Texture_path = "/data/opengl/testFBO.jpg";
	private Texture texture;
	private int FBO_ID;
	
	private void start(){
        initDisplay();
        glEnable(GL_CULL_FACE);
        initLighting();
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        loadIBOs();
        FBO_ID = FBO.makeFBO(side,side);
        
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
		//TODO
	}
	
	private void render(){   
		//TODO
	}
	
	private void viewTransform(){
		GLU.gluLookAt(50f, 50f, 0, 35f, 35f, -50f, 0f, 1f, 0f);
	}
	
	private void loadIBOs(){
		texturedCubeIboIDs = IBO.loadFBOCubeQuads3f(0, 0, 0, side, FBO_ID);
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