package routines;

import static org.lwjgl.opengl.GL11.*;
import static routines.Buffers.FB;

import java.nio.FloatBuffer;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import static routines.Buffers.*;

public final class Init
{
	public static int WIDTH = 800;
	public static int HEIGHT = 600;
	public static int program, vertexShader, fragmentShader;
	public static int xmin = 0, xmax = WIDTH, 
			ymin =0, ymax = HEIGHT, zmin = 300, zmax = -700;
	
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
	
	/**
	 * Initialise an othronormal projection on the full screen
	 * Base clipping planes defined by static [x|y|z][min|max]
	 */
	public static void initOrthoView(){  
		glViewport(0, 0, WIDTH, HEIGHT);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(xmin, xmax, ymin, ymax, zmin, zmax);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}
	
	/**
	 * Configurable overload
	 * @param edges = {xmin, xmax, ymin, ymax, zmin, zmax}
	 */
	public static void initOrthoView(double [] edges){  
		glViewport(0, 0, WIDTH, HEIGHT);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(edges[0], edges[1], edges[2], edges[3], edges[4], edges[5]);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}
	
	/**
	 * Orthonormal projection, uses scales y-axis by 0.5
	 */
	public static void initVerticalSplitScreen1(){  
		glViewport(0, 0, WIDTH/2, HEIGHT);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(xmin, xmax, ymin, ymax, zmin, zmax);
		glScalef(1f, 0.5f, 1f);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}
	
	/**
	 * See initVerticalSplitSceen1
	 */
	public static void initVerticalSplitScreen2(){  
		glViewport( WIDTH/2, 0, WIDTH, HEIGHT);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(xmin, xmax, ymin, ymax, zmin, zmax);
		glMatrixMode(GL_MODELVIEW);
		glScalef(1f, 0.5f, 1f);
		glLoadIdentity();
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
	
	public static void initShaders() {
        /*init openGL shaders*/
        program = ARBShaderObjects.glCreateProgramObjectARB();
        if (program==0) {
            System.out.println("Error: OpenGL shaders not supported");
            System.exit(0);
        }
        vertexShader=ARBShaderObjects.glCreateShaderObjectARB(ARBVertexShader.GL_VERTEX_SHADER_ARB);
        String vertexCode=""+
        "void main(void)" + 
        "{" + 
        "	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;" +  
        "}";
        
        ARBShaderObjects.glShaderSourceARB(vertexShader, vertexCode);
        ARBShaderObjects.glCompileShaderARB(vertexShader);
        if (ARBShaderObjects.glGetObjectParameteriARB(vertexShader, ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE)
        {
            System.out.println("Vertex shader not compiled: " + getLogInfo(vertexShader) );
        }
        
        fragmentShader=ARBShaderObjects.glCreateShaderObjectARB(ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
        String fragmentCode="" +
        "void main(void) {" +
        "	gl_FragColor = vec4 (0.0, 1.0, 0.0, 1.0);" +
        "}";
        
        ARBShaderObjects.glShaderSourceARB(fragmentShader, fragmentCode);
        ARBShaderObjects.glCompileShaderARB(fragmentShader);
        if (ARBShaderObjects.glGetObjectParameteriARB(fragmentShader, ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE)
        {
            System.out.println("Fragment shader not compiled: " + getLogInfo(fragmentShader) );
        }
        
        
        ARBShaderObjects.glAttachObjectARB(program, vertexShader);
        ARBShaderObjects.glAttachObjectARB(program, fragmentShader);
        ARBShaderObjects.glLinkProgramARB(program);
        ARBShaderObjects.glValidateProgramARB(program);
    }
	
	private static String getLogInfo(int obj) {
        return ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects.glGetObjectParameteriARB(obj, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB));
    }
}
