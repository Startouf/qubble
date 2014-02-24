package opengl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
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
		FloatBuffer verticesBuffer = makeFloatBuffer(values);
		int vbo_id = createVBOID();

		glBindBuffer(GL_ARRAY_BUFFER, vbo_id);
		glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_DYNAMIC_DRAW);

		return vbo_id;
	}

	/**
	 * Create a FloatBuffer from values and buffer the data to a VBO on the GPU
	 * Uses Static Draw parameter --> Not changed, used a lot
	 * @param values
	 * @return
	 */
	public static int loadStaticDrawVBO(float [] values)
	{
		FloatBuffer verticesBuffer = FB(values);
		int vbo_id = createVBOID();

		glBindBuffer(GL_ARRAY_BUFFER, vbo_id);
		glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);

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
	public static void drawIBO(int[] iboIDs){
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
	 * Color overload : vertices, color 
	 * @param vertex_vbo_id
	 * @param color_vbo_id
	 */
	public static void drawVBO(int vboID, int colorID){
		if (vboID==0) return;
		glEnableClientState(GL_VERTEX_ARRAY);
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glVertexPointer(3, GL_FLOAT, 0, 0); //3D

		if (colorID != 0) {
			glEnableClientState(GL11.GL_COLOR_ARRAY);
			glBindBuffer(GL_ARRAY_BUFFER, colorID);
			glColorPointer(3, GL11.GL_FLOAT, 0, 0);
		}

		glDrawArrays(GL11.GL_TRIANGLES, 0, 3*6*2); //count = number of vertices ! (not triangles!)
		glDisableClientState(GL11.GL_VERTEX_ARRAY);
		if (colorID != 0) 
			GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	/**
	 * Color overload : vertices, color 
	 * @param vertex_vbo_id
	 * @param color_vbo_id
	 */
	public static void drawQuadsVBO(int vboID, int colorID, int vertices){
		if (vboID==0) return;
		glEnableClientState(GL_VERTEX_ARRAY);
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glVertexPointer(3, GL_FLOAT, 0, 0); //3D

		if (colorID != 0) {
			glEnableClientState(GL11.GL_COLOR_ARRAY);
			glBindBuffer(GL_ARRAY_BUFFER, colorID);
			glColorPointer(3, GL11.GL_FLOAT, 0, 0);
		}

		glDrawArrays(GL11.GL_QUADS, 0, vertices); //count = number of vertices ! (not triangles!)
		glDisableClientState(GL11.GL_VERTEX_ARRAY);
		if (colorID != 0) 
			GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	/**
	 * Shader overload
	 * Use the shader program, draw the triangle, and release the shader
	 * @param vboIDs = int[] {vertex_VBO_id, index_VBO_id, number of vertices}
	 * @param shader the compiled shader program
	 */
	public static void drawShaderIBO(int[] vboIDs, int shader) {
		GL20.glUseProgram(shader);
		drawIBO(vboIDs);
		GL20.glUseProgram(0);
	}
}
