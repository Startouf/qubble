package routines;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.glu.GLU;
/**
 * 
 * @author Cyril
 * Just pasted the example of lwjgl
 *
 */

public class Shaders
{
	/**
	 * Demo Shader by Jean Le Feuvre
	 * @param vsID
	 * @param fsID
	 * @param pID
	 */
	public static void setupDemoShaders(Integer vsID, Integer fsID, Integer pID) {
		int errorCheckValue = GL11.glGetError();

		// Load the vertex shader
		vsID = loadShaderGL("src/thequad/vertex.glsl", GL20.GL_VERTEX_SHADER);
		// Load the fragment shader
		fsID = loadShaderGL("src/thequad/fragment.glsl", GL20.GL_FRAGMENT_SHADER);

		// Create a new shader program that links both shaders
		pID = GL20.glCreateProgram();
		GL20.glAttachShader(pID, vsID);
		GL20.glAttachShader(pID, fsID);

		// Position information will be attribute 0
		GL20.glBindAttribLocation(pID, 0, "in_Position");
		// Color information will be attribute 1
		GL20.glBindAttribLocation(pID, 1, "in_Color");

		GL20.glLinkProgram(pID);
		GL20.glValidateProgram(pID);

		errorCheckValue = GL11.glGetError();
		if (errorCheckValue != GL11.GL_NO_ERROR) {
			System.out.println("ERROR - Could not create the shaders:" + GLU.gluErrorString(errorCheckValue));
			System.exit(-1);
		}
	}

	/**
	 * Load a Shader using GL20
	 * @param vertexShaderPath
	 * @param fragmentShaderPath
	 * @param attrib Name of the attributes that are used by the shader (in_Color, in_...)
	 * @return int[] {vertex Shader ID, fragment Shader ID, program ID}
	 */
	public static int[] loadShadersGL(String vertexShaderPath, String fragmentShaderPath, String[] attrib) {
		int errorCheckValue = GL11.glGetError();
		int vsID, fsID, pID;
		// Load the vertex shader
		vsID = loadShaderGL(vertexShaderPath, GL20.GL_VERTEX_SHADER);
		// Load the fragment shader
		fsID = loadShaderGL(fragmentShaderPath, GL20.GL_FRAGMENT_SHADER);

		// Create a new shader program that links both shaders
		pID = GL20.glCreateProgram();
		GL20.glAttachShader(pID, vsID);
		GL20.glAttachShader(pID, fsID);

		for (int i=0; i<attrib.length;i++){
			GL20.glBindAttribLocation(pID, i, attrib[i]);
		}

		GL20.glLinkProgram(pID);
		GL20.glValidateProgram(pID);

		errorCheckValue = GL11.glGetError();
		if (errorCheckValue != GL11.GL_NO_ERROR) {
			System.out.println("ERROR - Could not create the shaders:" + GLU.gluErrorString(errorCheckValue));
			System.exit(-1);
		}
		return new int[]{vsID, fsID, pID};
	}

	/**
	 * Load and compile a shader from a file (with a fileReader, not an inputStream !)
	 * Prints error related to Shader Compiling
	 * @param filename
	 * @param type
	 * @return
	 */
	private static int loadShaderGL(String filename, int type) {
		StringBuilder shaderSource = new StringBuilder();
		int shaderID = 0;

		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = reader.readLine()) != null) {
				shaderSource.append(line).append("\n");
			}
			reader.close();
		} catch (IOException e) {
			System.err.println("Could not read file.");
			e.printStackTrace();
			System.exit(-1);
		}

		shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);

		if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.err.println("Could not compile shader " + filename);
			System.err.println( getLogInfo(shaderID));
			System.exit(-1);
		}

		return shaderID;
	}
	
	public static void attachShader(int programID){
		GL20.glUseProgram(programID);
	}
	
	public static void detachShader(){
		GL20.glUseProgram(0);
	}

	/**
	 * Destroy a Shader. WARNING : pID first !!
	 * @param pID
	 * @param vsID
	 * @param fsID
	 */
	public static void destroyShader(int pID, int vsID, int fsID){
		GL20.glUseProgram(0);
		GL20.glDetachShader(pID, vsID);
		GL20.glDetachShader(pID, fsID);

		GL20.glDeleteShader(vsID);
		GL20.glDeleteShader(fsID);
		GL20.glDeleteProgram(pID);
	}

	private static String getLogInfo(int obj) {
        return GL20.glGetShaderInfoLog(obj, GL20.glGetShaderi(obj, GL20.GL_SHADER_SOURCE_LENGTH));
    }
}
