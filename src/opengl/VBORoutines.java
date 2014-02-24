package opengl;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glDisableClientState;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL11.glVertexPointer;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static routines.Buffers.FB;
import static routines.Buffers.createVBOID;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class VBORoutines
{
	/**************
	 * Tools
	 */
	
	/**
	 * Make a GL_Float (legacy with lwjgl)
	 * @param values
	 * @return
	 */
	public static FloatBuffer makeFloatBuffer(float[] values)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(values.length);
		buffer.put(values);
		buffer.flip();
		return buffer;
	}
	
	/**
	 * Create a FloatBuffer from values and buffer the data to a VBO on the GPU
	 * Uses Dynamic Draw parameter --> Changed often, used a lot
	 * @param values
	 * @return
	 */
	public static int loadDynamicDrawVBO(float [] values)
	{
		//chargeons les données dans un FloatBuffer
		FloatBuffer verticesBuffer = makeFloatBuffer(values);

		//creons un VBO dans la mémoire du GPU (pas encore de données associées))
		int vbo_id = createVBOID();

		//et copions les données dans la mémoire du GPU en spécifiant l'utilisation
		glBindBuffer(GL_ARRAY_BUFFER, vbo_id);
		glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_DYNAMIC_DRAW);

		return vbo_id;
	}
	
	/*****************
	 * Draw
	 */
	
	/**
	 * Base overload : only vertices
	 * Draw an IBO given its vertex ID, index ID and number of vertices
	 * @param iboIDs = int[] {vertex_VBO_id, index_VBO_id, number of vertices}
	 */
	public static void drawTriangles3f(int[] iboIDs){
		if (iboIDs == null){
			System.out.println("NULL IBO ID");
			return;
		}
		glEnableClientState(GL_VERTEX_ARRAY);
		glBindBuffer(GL_ARRAY_BUFFER, iboIDs[0]);
		glVertexPointer(3, GL_FLOAT, 0, 0);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboIDs[1]);
		glDrawElements(GL_TRIANGLES, iboIDs[2], GL_UNSIGNED_INT, 0);

		glDisableClientState(GL11.GL_VERTEX_ARRAY);
	}
	
	/**
	 * Shader overload
	 * Use the shader program, draw the triangle, and release the shader
	 * @param vboIDs = int[] {vertex_VBO_id, index_VBO_id, number of vertices}
	 * @param shader the compiled shader program
	 */
	public static void drawShaderIBO(int[] vboIDs, int shader) {
		GL20.glUseProgram(shader);
		drawTriangles3f(vboIDs);
		GL20.glUseProgram(0);
	}
}
