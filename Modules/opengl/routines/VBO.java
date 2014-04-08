package routines;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GLContext;

import static routines.Buffers.*;
import static routines.someMath.*;

public final class VBO
{
	/*****************************************
	 * VBO TOOLS
	 ************************************/
	
	/**
	 * Create a FloatBuffer from values and buffer the data to a VBO on the GPU
	 * Uses Static Draw parameter --> Not changed, used a lot
	 * @param values
	 * @return
	 */
	//Uses GL_STATIC_DRAW
	public static int loadStaticDrawVBO(float [] values)
	{
		//chargeons les données dans un FloatBuffer
		FloatBuffer verticesBuffer = FB(values);

		//creons un VBO dans la mémoire du GPU (pas encore de données associées))
		int vbo_id = createVBOID();

		//et copions les données dans la mémoire du GPU en spécifiant l'utilisation
		glBindBuffer(GL_ARRAY_BUFFER, vbo_id);
		glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);

		return vbo_id;
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
		FloatBuffer verticesBuffer = FB(values);

		//creons un VBO dans la mémoire du GPU (pas encore de données associées))
		int vbo_id = createVBOID();

		//et copions les données dans la mémoire du GPU en spécifiant l'utilisation
		glBindBuffer(GL_ARRAY_BUFFER, vbo_id);
		glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_DYNAMIC_DRAW);

		return vbo_id;
	}

	/**
	 * Overload : provide the VBO
	 * @param vbo_id
	 * @param values
	 */
	public static void loadDynamicDrawVBO(int vbo_id, float[] values)
	{
		//chargeons les données dans un FloatBuffer
		FloatBuffer verticesBuffer = FB(values);

		//et copions les données dans la mémoire du GPU en spécifiant l'utilisation
		glBindBuffer(GL_ARRAY_BUFFER, vbo_id);
		glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_DYNAMIC_DRAW);
	}

	//Shortcut
	public static int getVBO_ID(float[] values){
		return (loadStaticDrawVBO(values));
	}

	/**
	 * Will replace the values from the beginning of the VBO   
	 * @param vboID
	 * @param values
	 */
	public static void overwrite(int vboID, float[] values){
		overwrite(vboID, values, 0);
	}

	/**
	 * Will replace the values from 'offset' 
	 * @param vboID
	 * @param values
	 * @param offset
	 */
	public static void overwrite(int vboID, float[] values, int offset){
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer FB = Buffers.FB(values);
		GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, offset, FB); 
	}
	
	public static void destroy(int vboID){
		GL15.glDeleteBuffers(vboID);
	}
	
	/*****************************************
	 * LOAD VBO
	 *****************************************/

	/**
	 * drawArray Overload (not IBO)
	 * @param w width
	 * @param h height
	 * @param d side
	 * @param vertex_vbo_id
	 */
	public static void loadTriangleVBO(float w, float h, float d, Integer vertex_vbo_id) {
		//si nous avons déja crée le VBO, inutile de recommencer ...
		if (vertex_vbo_id != 0) return;

		//creons un VBO qui contient nos vertex - nous avons besoin de 3 sommets
		w/=2; h/=2; d/=2;
		float[] vertices = new float[] {
				-w, h, d,
				w, h, d,
				w, -h, d,
		};
		vertex_vbo_id = loadStaticDrawVBO(vertices);
	}



	/**
	 * Assumes drawCubeVBO method is used (nb_vertices known by the function)
	 * @param x
	 * @param y
	 * @param z
	 * @param s side
	 * @return VBO_ID
	 */
	public static int loadCubeVBOTriangles(float x, float y, float z, float s){
		/*			3V
		 * 	   ____________ 
		 * 	  / 	5	  /|
		 *   /___________/ |	
		 *   |  		|  |	
		 * 4>|    1     |2 | 
		 * 	 |  		| /
		 *   | _________|/
		 * 			6^	
		 */
		int vbo_id = loadStaticDrawVBO(new float[]{
				x,y,z,	x+s,y,z, 	x,y+s,z,	x,y+s,z,	x+s,y,z,	x+s,y+s,z,
				x+s,y,z,	x+s,y,z-s,	x+s,y+s,z,		x+s,y+s,z,	x+s,y,z-s,	x+s,y+s,z-s,
				x+s,y,z-s,	x,y,z-s,	x,y+s,z-s,	x,y+s,z-s,	x+s,y+s,z-s,	x+s,y,z-s,
				x,y,z,	x,y+s,z,	x,y+s,z-s,	x,y+s,z-s,	x,y,z-s,	x,y,z,
				x,y+s,z,	x+s,y+s,z,	x+s,y+s,z-s,	x+s,y+s,z-s,	x,y+s,z-s,	x,y+s,z,
				x,y,z,	x,y,z-s,	x+s,y,z-s,	x+s,y,z-s,	x+s,y,z,	x,y,z
		});
		return vbo_id;
	}

	/**
	 * Color overload : vertices, color 
	 * @param vertex_vbo_id
	 * @param color_vbo_id
	 */
	public static void drawCubeVBOTriangles(int vertex_vbo_id, int color_vbo_id){
		if (vertex_vbo_id==0) return;
		glEnableClientState(GL_VERTEX_ARRAY);
		glBindBuffer(GL_ARRAY_BUFFER, vertex_vbo_id);
		glVertexPointer(3, GL_FLOAT, 0, 0); //3D

//		if (color_vbo_id != 0) {
//			glEnableClientState(GL11.GL_COLOR_ARRAY);
//			glBindBuffer(GL_ARRAY_BUFFER, color_vbo_id);
//			glColorPointer(4, GL11.GL_FLOAT, 0, 0);
//		}

		glDrawArrays(GL11.GL_TRIANGLES, 0, 3*6*2); //count = number of vertices ! (not triangles!)
		glDisableClientState(GL11.GL_VERTEX_ARRAY);
		if (color_vbo_id != 0) GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
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
	
	public static void drawQuadStrips2fVBO(int vboID, int colorID, int vertices) {
		if (vboID==0) return;
		glEnableClientState(GL_VERTEX_ARRAY);
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glVertexPointer(2, GL_FLOAT, 0, 0); //2D

		if (colorID != 0) {
			glEnableClientState(GL11.GL_COLOR_ARRAY);
			glBindBuffer(GL_ARRAY_BUFFER, colorID);
			glColorPointer(3, GL11.GL_FLOAT, 0, 0);
		}

		glDrawArrays(GL11.GL_QUAD_STRIP, 0, vertices); 
		glDisableClientState(GL11.GL_VERTEX_ARRAY);
		if (colorID != 0) 
			GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
	}

	/**
	 * Interleaved vertices, color
	 * Using color4f !!
	 * @param vboID
	 */
	public static void drawColoredPoints2f(int vboID, int count) {
		if (vboID==0) return;
		glEnableClientState(GL_VERTEX_ARRAY);
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glVertexPointer(2, GL_FLOAT, (2+4)*4, 0); //2D -> stride of 6*sizeOf(float)

		glEnableClientState(GL11.GL_COLOR_ARRAY);
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glColorPointer(4, GL11.GL_FLOAT, (2+4)*4, 2*4);

		glDrawArrays(GL11.GL_POINTS, 0, 1); 
		glDisableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
	}
	
	//Interleave triangle vertices, color and normal
	// 3 vertices => 9 floats
	//Should be in someMath ?
	public static float[] interleave3f(float[][] vertices, float[]color){
		float[] n = getNormal(new float[] {vertices[0][0], vertices[0][1], vertices[0][2]},
				new float[] {vertices[1][0], vertices[1][1], vertices[1][2]},
				new float[] {vertices[2][0], vertices[2][1], vertices[2][2]});
		return(new float[] {
				vertices[0][0], vertices[0][1], vertices[0][2],
				vertices[1][0], vertices[1][1], vertices[1][2],
				vertices[2][0], vertices[2][1], vertices[2][2],
				color[0], color[1], color[2],
				n[0], n[1], n[3]});
	}
}
