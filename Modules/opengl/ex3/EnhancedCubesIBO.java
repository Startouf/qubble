package ex3;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.concurrent.Callable;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.Point;
import org.lwjgl.util.glu.GLU;

import routines.Time;
import static org.lwjgl.opengl.GL11.*;
import static routines.IBO.*;
import static routines.Init.*;
import static routines.Transformations.*;

public class EnhancedCubesIBO
{
	private Point coords = new Point(200,200);
	private int side = 200;
	private int[][] coolCubeIboIDs = null,  normalCubeIboIDs = null, 
			lightedCubeIboIDs = null, texturedCubeIboIDs = null, awesomeCubeIboIDs = null;
	
	private void start(){
        initDisplay();
        glEnable(GL_CULL_FACE);
		glEnableClientState(GL_NORMAL_ARRAY);
		glEnableClientState(GL_VERTEX_ARRAY);
        initLighting();
        loadIBOs();
        
        while(!Display.isCloseRequested()){   
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
	
	private void viewTransform(){
		GLU.gluLookAt(50f, 50f, 0, 35f, 35f, -50f, 0f, 1f, 0f);
	}
	
	private void loadIBOs(){
//		coolCubeIboIDs = loadColoredCubeIBOTriangles3f(0, 0, 0, 200, new float[]{
//				1f,0f,0f,	0f,1f,0f,	0f,0f,1f,	1f,1f,0f,
//				0f,1f,1f,	1f,0f,1f});
		lightedCubeIboIDs = loadLightedCubeIBOTriangles3f(0, 0, 0, 200, new float[]{
				1f,0f,0f,0f,1f,0f,0f,0f,1f,1f,1f,0f,
				0f,1f,1f,1f,0f,1f});
	}
	
	private void render(){   
//		glPushMatrix();
//		putObjectAt(new float[] {coords.getX(), coords.getY(), -200}, 
//				Time.uniformRotation(), new float[] {side, side, side});
//		drawIBOTriangles3f(coolCubeIboIDs);
//		glPopMatrix();
		drawIBOTriangles3f(lightedCubeIboIDs);
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
		EnhancedCubesIBO app = new EnhancedCubesIBO();
		app.start();
	}
}
