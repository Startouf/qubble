package routines;

import static org.lwjgl.opengl.GL11.*;
import static routines.Buffers.FB;

import java.nio.FloatBuffer;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import static routines.Buffers.*;

public final class Init
{
	public static int WIDTH = 800;
	public static int HEIGHT = 600;
	
	public static void initDisplay(){
		try{
			Display.setDisplayMode(new DisplayMode(800,600));
			Display.create();
		} catch (LWJGLException e){
			e.printStackTrace();
		}
	}
	
	public static void initDisplay(int width, int height){
		try{
			Display.setDisplayMode(new DisplayMode(width,height));
			Display.create();
		} catch (LWJGLException e){
			e.printStackTrace();
		}
	}
	
	public static void initLighting(){
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
	
	public static void initManyThings(){
		GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glClearDepth(1.0f);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
        
        
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glColorMaterial(GL11.GL_FRONT_AND_BACK,GL11.GL_AMBIENT);
        
        GL11.glEnable(GL11.GL_LIGHT0);
    	GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, makeFloatBuffer(new float[]{ 0f, 0f, 1.0f, 0.0f } )  );
     	GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, makeFloatBuffer(new float[]{1f, 0f, 0f,1f}));       
        GL11.glEnable(GL11.GL_LIGHTING);
	}
}
