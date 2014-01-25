package routines;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
	public static void setupShaders(Integer vsID, Integer fsID, Integer pID) {
		int errorCheckValue = GL11.glGetError();

		// Load the vertex shader
		vsID = loadShader("src/thequad/vertex.glsl", GL20.GL_VERTEX_SHADER);
		// Load the fragment shader
		fsID = loadShader("src/thequad/fragment.glsl", GL20.GL_FRAGMENT_SHADER);

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

	public static int loadShader(String filename, int type) {
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
			System.err.println("Could not compile shader.");
			System.exit(-1);
		}

		return shaderID;
	}

	public static void destroyShader(int pID, int vsID, int fsID){
		GL20.glUseProgram(0);
		GL20.glDetachShader(pID, vsID);
		GL20.glDetachShader(pID, fsID);

		GL20.glDeleteShader(vsID);
		GL20.glDeleteShader(fsID);
		GL20.glDeleteProgram(pID);
	}

}
