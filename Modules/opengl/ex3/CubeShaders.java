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
 */
public class CubeShaders
{
	private Point coords = new Point(200,200);
	private static int WIDTH = 800;
	private static int HEIGHT = 600;
	private int[] CubeIboIDs = null;
	private int[] shader;
	private static String SHADER_PATH = "data/opengl/shaders/";
	private int rotation =0;
	private float colorRed =0;
	private int colorRedAddress, rotationAddress, positionAddress;
	
	private void start(){
        initDisplay();
        glEnable(GL_CULL_FACE);
		glEnableClientState(GL_VERTEX_ARRAY);
		//Note : either use shader here OR just before rendering (if different shaders are used, better doing that during render() )
		//In this class, it would be better to use the shader here
        loadShaders();
        loadIBOs();
        
        setObjectPosition();
        
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
		rotation = (int)Time.uniformRotation();
		colorRed = UserInputs.incrementWithArrowKeys(colorRed, 5f);
		colorRed = Math.abs(colorRed) % 255;
	}

	private void updateShaders(){
		//NOTE : This is a bad code : the GPU recalculates the modelview matrix for every vertex !
		//(Only done for the exercise, please do not copy paste)
		GL20.glUniform1i(rotationAddress, rotation); 
		GL20.glUniform1i(colorRedAddress, (int)colorRed);
	}
	
	private void viewTransform(){
		glTranslatef(+coords.getX()+100, +coords.getY()+130, -200);
	}
	
	private void render(){    
        IBO.drawTriangles3f(CubeIboIDs, shader[2]);
	}
	
	private void setObjectPosition(){
		FloatBuffer posMat = Buffers.IdentityMatrix();
		Buffers.setPos(posMat, 100, 100, -100);
		GL20.glUniformMatrix4(positionAddress, false, posMat);
	}

	private void loadIBOs(){
		CubeIboIDs = loadCubeTriangles3f(-100, -100, -100, 200);
	}

	private void loadShaders(){
		String[] attrib = new String[]{"position", "colorRed"};
		shader = Shaders.loadShadersGL(SHADER_PATH + "RotatingCube.vp", SHADER_PATH + "ChangeRed.fp", attrib);
		//Must get the Uniform locations only once ?
		rotationAddress = GL20.glGetUniformLocation(shader[2], "rotation"); 
		colorRedAddress = GL20.glGetUniformLocation(shader[2], "colorRed");
		positionAddress = GL20.glGetUniformLocation(shader[2],  "pos");
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