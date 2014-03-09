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
import static routines.VBO.*;
import static routines.Init.*;
/**
 * Ex 3.1 GLBaseModule : en utilisant un VBO
 * En utilisant un VBO non indexé, il faut préciser à chaque tous les sommets pour tous les triangles
 * (Donc un sommet est dupliqué 6 fois (2 fois par face)
 * la suite de l'exercice est dans la classe CubeIBO
 * @author Cyril
 *
 */
public class CubeVBO
{
	private Point coords = new Point(300,300);
	private static int WIDTH = 800;
	private static int HEIGHT = 600;
	private int cubeVBO;
	
	private void start(){
        initDisplay();
        glEnable(GL_CULL_FACE);
		glEnableClientState(GL_VERTEX_ARRAY);
        
        loadVBOs();
        
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
		glTranslatef(+coords.getX(), +coords.getY(), -200);
		glRotated(Time.uniformRotation(), 0.0, 1.0, 0.0);
		glTranslatef(-100, -100, +100);
	}
	
	private void loadVBOs(){
		cubeVBO = loadCubeVBOTriangles(0, 0, 0, 200);
	}
	
	private void render(){    
        drawCubeVBOTriangles(cubeVBO, 0);
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
		CubeVBO app = new CubeVBO();
		app.start();
	}
}
