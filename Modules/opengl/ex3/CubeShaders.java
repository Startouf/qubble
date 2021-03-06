package ex3;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.Point;
import org.lwjgl.util.glu.GLU;

import routines.Buffers;
import routines.IBO;
import routines.Init;
import routines.Shaders;
import routines.Time;
import routines.UserInputs;
import static org.lwjgl.opengl.GL11.*;
import static routines.IBO.*;
import static routines.Init.*;
/**
 * @author Cyril
 * Ex 3.3 et 3.4 : Cube with shaders
 * 
 * Note : the poor rendering of the back face (the side is drawn very badly)
 * MIGHT be explained here http://www.opengl.org/archives/resources/faq/technical/depthbuffer.htm 
 * (See 12.070)
 *
 */
public class CubeShaders
{
	private Point coords = new Point(200,200);
	private static int WIDTH = 800;
	private static int HEIGHT = 600;
	private int[] CubeIboIDs = null;
	private int[] shader;
	private static String SHADER_PATH = "data/opengl/shaders/";
	private float rotation =0;
	private float colorRed =0;
	private int colorRedAddress, rotationAddress, positionAddress;
	
	private void start(){
        Init.initDisplay();
        glEnable(GL_CULL_FACE);
		glEnableClientState(GL_VERTEX_ARRAY);
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LEQUAL);
		glShadeModel(GL_SMOOTH);
		//Note : either use shader here OR just before rendering (if different shaders are used, better doing that during render() )
		//In this class, it would be better to use the shader here
        loadShaders();
        loadIBOs();

        while(!Display.isCloseRequested()){   
        	glClear(GL_COLOR_BUFFER_BIT | 
					GL_DEPTH_BUFFER_BIT);
        	setView();
        	
        	updateVariables(); //Increment Red component of colors with arrow keys
            updateShaders(); //Send this info to the shader uniform variables
            viewTransform(); //Note : doing the translation AFTER the rotation done by the VertexShader

            render(); //Using shader overload
            Display.update();
            Display.sync(60);
        }
        
        destroyShaders();
        Display.destroy();
    }
	
	private void updateVariables(){
		rotation = (float)Time.uniformRotation();
		colorRed = UserInputs.incrementWithArrowKeys(colorRed, 0.05f);
		colorRed = Math.abs(colorRed) % 1f;
		System.out.println("rotation = "+ rotation);
		System.out.println("colorRed = " +colorRed);
	}

	private void updateShaders(){
		//NOTE : This is a bad code : the GPU recalculates the modelview matrix for every vertex !
		//(Only done for the exercise, please do not copy paste)
		
		//Note 2 : will only affect the ACTIVE shader ! 
		GL20.glUseProgram(shader[2]);
		GL20.glUniform1f(rotationAddress, rotation); 
		GL20.glUniform1f(colorRedAddress, colorRed);
		GL20.glUseProgram(0);
	}
	
	private void viewTransform(){
		glTranslatef(+coords.getX()+100, +coords.getY()+130, -200);
	}
	
	private void render(){    
        IBO.drawTriangles3f(CubeIboIDs, shader[2]);
	}

	private void loadIBOs(){
		CubeIboIDs = IBO.loadCubeTriangles3f(-100, -100, -100, 200);
	}

	private void loadShaders(){
		String[] attrib = new String[]{"position", "colorRed"};
		shader = Shaders.loadShadersGL(SHADER_PATH + "RotatingCube.vp", SHADER_PATH + "ChangeRed.fp", attrib);
		//Must get the Uniform locations only once ?
		rotationAddress = GL20.glGetUniformLocation(shader[2], "rotation"); 
		colorRedAddress = GL20.glGetUniformLocation(shader[2], "colorRed");
		positionAddress = GL20.glGetUniformLocation(shader[2],  "pos");
		
		if (rotationAddress == 0 || colorRedAddress == 0 || positionAddress ==0){
			System.err.println("Could not get Uniform locations");
			System.err.println("rotationAddress : " +rotationAddress);
			System.err.println("colorRedAddress : " +colorRedAddress);
			System.err.println("positionAddress : " +positionAddress);
		}
	}

	private void setView(){    
		glViewport(0, 0, WIDTH, HEIGHT);
		glMatrixMode(GL11.GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 800, 0, 600, 300, -600);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		GLU.gluLookAt(50f, 50f, 0, 35f, 35f, -50f, 0f, 1f, 0f);
	}
	
	private void destroyShaders(){
		Shaders.destroyShader(shader[2], shader[0], shader[1]);
	}

	public static void main(String[] args){
		CubeShaders app = new CubeShaders();
		app.start();
	}
}