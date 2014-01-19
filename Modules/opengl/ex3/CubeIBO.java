package ex3;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

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

public class CubeIBO
{
	private Point coords = new Point(200,200);
	private static int WIDTH = 800;
	private static int HEIGHT = 600;
	private int[] CubeIboIDs = null;
	
	private void start(){
        initDisplay();
        glEnable(GL_CULL_FACE);
		glEnableClientState(GL_NORMAL_ARRAY);
		glEnableClientState(GL_VERTEX_ARRAY);
        
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
		glTranslatef(+coords.getX()+100, +coords.getY()+130, -200);
		glRotated(Time.uniformRotation(), 0.0, 1.0, 0.0);
		glTranslatef(-100, -100, +100);
	}
	
	private void loadIBOs(){
		CubeIboIDs = loadCubeIBOTriangles3f(0, 0, 0, 200);
	}
	
	private void render(){    
        drawIBOTriangles3f(CubeIboIDs);
	}

	private void initGL(){    
		glViewport(0, 0, WIDTH, HEIGHT);
		glMatrixMode(GL11.GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 800, 0, 600, 300, -600);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		GLU.gluLookAt(50f, 50f, 0, 35f, 35f, -50f, 0f, 1f, 0f);
	}
	
	public static void main(String[] args){
		CubeIBO app = new CubeIBO();
		app.start();
	}
}