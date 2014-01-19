package routines;

import java.nio.IntBuffer;

import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import static routines.VBO.*;
import static routines.Buffers.*;
import static routines.someMath.*;
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
		if (vertices == null || indices == null){
			System.out.println("NULL IBO ID");
		}

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
		 * 	  7____________ 6
		 * 	  / 		  /|
		 * 3 /___________/ |	
		 *   |  	   2|  |	
		 *   |	4		|  |5 
		 * 	 |  		| /
		 *   |__________|/
		 * 	0			1			
		 */
		float[] vertices = {x,y,z,	x+s,y,z,	x+s,y+s,z,	x,y+s,z,
				x,y,z-s,	x+s,y,z-s,	x+s,y+s,z-s,	x,y+s,z-s};
		int[] indices = { 0,1,3,	3,1,2,	1,5,2,	2,5,6,	6,5,4,	4,7,6,	0,3,4,	4,3,7,	
				3,2,6,	6,7,3};
		return getIBO_IDs(vertices, indices);
	}

	/**
	 * Colored cube (interleaved)
	 * Colors sent for each side as float[][]
	 * Orders for the sides : front - right - back - left - top - bottom
	 * @param x
	 * @param y
	 * @param z
	 * @param s side
	 * @param color
	 * @return
	 */
	public static int[][] loadColoredCubeIBOTriangles3f(float x, float y, float z, float s, float [] colors){
		float[] vertices = {x,y,z,	x+s,y,z,	x+s,y+s,z,	x,y+s,z,
				x,y,z-s,	x+s,y,z-s,	x+s,y+s,z-s,	x,y+s,z-s};
		int[] vtx_indices = { 0,1,3,	3,1,2,	1,5,2,	2,5,6,	6,5,4,	4,7,6,	0,3,4,	4,3,7,	
				3,2,6,	6,7,3,	0,5,1,	0,4,5};
		return new int[][] {getIBO_IDs(vertices, vtx_indices), new int[]{getVBO_ID(colors)}, null};
	}

	public static int[][] loadLightedCubeIBOTriangles3f(float x, float y, float z, float s, float [] colors){
		float[] vertexCoords = {x,y,z,	x+s,y,z,	x+s,y+s,z,	x,y+s,z,
				x,y,z-s,	x+s,y,z-s,	x+s,y+s,z-s,	x,y+s,z-s};
		int[] vtx_indices = { 0,1,3,	3,1,2,	1,5,2,	2,5,6,	6,5,4,	4,7,6,	0,3,4,	4,3,7,	
				3,2,6,	6,7,3,	0,5,1,	0,4,5};
		float[] normals = new float[2*6*3];
		float[] currentNormal = new float[3];

		//get Normals should be moved to someMath
		for (int i=0; i<6;i++){
			currentNormal = getNormal(getVertexFromCoords3f(vertexCoords, i),
					getVertexFromCoords3f(vertexCoords, i),
					getVertexFromCoords3f(vertexCoords, i));
			mapVectorToCoords3f(currentNormal, normals, 2*i);
			mapVectorToCoords3f(currentNormal, normals, 2*i+1);
		}
		return new int[][] {getIBO_IDs(vertexCoords, vtx_indices), null, new int[] {getVBO_ID(normals)}};
	}

	/**
	 * Simple overload : only vertices
	 * Draw an IBO given its vertex ID, index ID and number of vertices
	 * @param IDs = int[] {vertex_VBO_id, index_VBO_id, number of vertices}
	 */
	public static void drawIBOTriangles3f(int[] vboIDs){

		if (vboIDs == null){
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

	/**
	 * Colors and Normals Overload (Not interleaved)
	 * @param vboIDs = {{vtx_IboIDs}, {color_VBOID}, {normals_VBO}}
	 * Send null if you do not want to specify either color or normals
	 */
	public static void drawIBOTriangles3f(int[][] vboIDs){

		if (vboIDs == null || vboIDs[0] == null){
			System.out.println("NULL IBO ID");
			return;
		}
		if (vboIDs[1] != null){
			glEnableClientState(GL_COLOR_ARRAY);
			glBindBuffer(GL_ARRAY_BUFFER, vboIDs[1][0]);
			glColorPointer(3, GL_FLOAT, 0, 0);
		}
		if(vboIDs[2] != null){
			glEnableClientState(GL_NORMAL_ARRAY);
			glBindBuffer(GL_ARRAY_BUFFER, vboIDs[2][0]);
			glNormalPointer(GL_FLOAT, 0, 0); //no size parameter
		}

		glEnableClientState(GL_VERTEX_ARRAY);
		glBindBuffer(GL_ARRAY_BUFFER, vboIDs[0][0]);
		glVertexPointer(3, GL_FLOAT, 0, 0);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboIDs[0][1]);
		glDrawElements(GL_TRIANGLES, vboIDs[0][2], GL_UNSIGNED_INT, 0);

		glDisableClientState(GL_VERTEX_ARRAY);
		if (vboIDs[1] != null){
			glDisableClientState(GL_COLOR_ARRAY);
		}
		if (vboIDs[2] != null){
			glDisableClientState(GL_NORMAL_ARRAY);
		}
	}
}
