package routines;

import java.nio.IntBuffer;

import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import static routines.VBO.*;
import static routines.Buffers.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class IBO
{
	public static int load_index_vbo(int [] values) 
	{
		IntBuffer indicesBuffer = IB(values); 
		int vbo_id = createVBOID();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbo_id);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
		return vbo_id;
	}
	
	public static int[] getIBO_IDs(float[] vertices, int[] indices){
		return new int[] {load_float_vbo(vertices), load_index_vbo(indices), indices.length};
	}

	/**
	 * @param w
	 * @param h
	 * @param d
	 * @return	verticesVBO_id, indexVBO_id, nbr_indices
	 */
	public static int[] loadTriangleVBO(float w, float h, float d) {
		//creons un VBO qui contient nos vertex - nous avons besoin de 3 sommets
		w/=2;
		h/=2;
		d/=2;
		float[] vertices = new float[] {
				-w, h, d,
				w, h, d,
				w, -h, d,
		};

		//creons un VBO qui contient les index des sommets dans les deux triangles de notre cube
		int[] indices = new int[]
				{
				0,1,2
				};
		return getIBO_IDs(vertices, indices);
	}
	
	public static int[] loadCubeIBOTriangles3f(float x, float y, float z, float s){
		/*			
		 * 	  8____________ 7
		 * 	  / 		  /|
		 *   /___________/ |	
		 *   |4  	   3|  |	
		 *   |			|  |6 
		 * 	 |  		| /
		 *   |__________|/
		 * 	1			2			
		 */
		float[] vertices = {x,y,z,	x+s,y,z,	x+s,y+s,z,	x,y+s,z,
							x,y,z-s,	x+s,y,z-s,	x+s,y+s,z-s,	x,y+s,z-s};
		int[] indices = { 1,2,4,	4,2,3,	2,6,3,	3,6,7,	7,6,5,	5,8,7,	1,4,5,	5,4,8,	
							4,3,7,	7,8,4};
		return getIBO_IDs(vertices, indices);
	}
	
	/**
	 * Simple overload : only vertices
	 * Draw an IBO given its vertex ID, index ID and number of vertices
	 * @param IDs = int[] {vertex_VBO_id, index_VBO_id, number of vertices}
	 */
	public static void drawIBOTriangles3f(int[] vboIDs){

		if (vboIDs == null){
			System.out.println("NULL IBO ID");
			return;
		}
		
	      glEnableClientState(GL11.GL_VERTEX_ARRAY);
	      glBindBuffer(GL_ARRAY_BUFFER, vboIDs[0]);
	      glVertexPointer(3, GL11.GL_FLOAT, 0, 0);

	      //attachons le buffer d'indices comme le buffer 'ELEMENT_ARRAY', i.e. celui utilisé pour glDrawElements
	      glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboIDs[1]);
	  		
	      glDrawElements(GL_TRIANGLES, vboIDs[2], GL_UNSIGNED_INT, 0);

	      glDisableClientState(GL11.GL_VERTEX_ARRAY);
	}
}
