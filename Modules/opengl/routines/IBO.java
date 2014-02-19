package routines;

import java.nio.IntBuffer;

import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
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

	/**
	 * Returns an IBO
	 * @param vertices VertexCoords
	 * @param indices index indexes
	 * @return int {float VBO ID, index IBO ID, number of indexes}
	 */
	public static int[] getIBO_IDs(float[] vertices, int[] indices){
		if (vertices == null || indices == null){
			System.out.println("NULL IBO ID");
		}

		return new int[] {loadStaticDrawVBO(vertices), load_index_vbo(indices), indices.length};
	}
	
	/***********************************
	 * LOAD PREDEFINED IBOs
	 **********************************/

	/**
	 * Pasted from GLBaseModule (Jean Le Feuvre)
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

	/**
	 * Load an IBO cube
	 * @param x
	 * @param y
	 * @param z
	 * @param s side
	 * @return IBO IDs = int[] {Vertex VBO, index IBO, number of indexes}
	 */
	public static int[] loadCubeTriangles3f(float x, float y, float z, float s){
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
				3,2,6,	6,7,3,	0,5,1,	0,4,5};
		return getIBO_IDs(vertices, indices);
	}

	/**
	 * Colored cube IBO (not lighted, not interleaved)
	 * Colors sent for each side as float[][]
	 * Orders for the sides : front - right - back - left - top - bottom
	 * @param x
	 * @param y
	 * @param z
	 * @param s side
	 * @param color
	 * @return { {Vertex VBO, index IBO, # of indexes}, {color VBO}, null }
	 */
	public static int[][] loadColoredCubeTriangles3f(float x, float y, float z, float s, float [][] colors){
		//TODO Not working as it should
		float[] vertexCoords = {x,y,z,	x+s,y,z,	x+s,y+s,z,	x,y+s,z,
				x,y,z-s,	x+s,y,z-s,	x+s,y+s,z-s,	x,y+s,z-s};
		int[] vtx_indices = { 0,1,3,	3,1,2,	1,5,2,	2,5,6,	6,5,4,	4,7,6,	0,3,4,	4,3,7,	
				3,2,6,	6,7,3,	0,5,1,	0,4,5};
		float[] colorsCoords = new float[6*2*3*3]; //Expecting one color3f per vertex ?
		for (int i=0; i<6;i++){ //For each face
			for (int j=0; j<6; j++){ //For each vertex of the 2 triangles making a face
				mapVectorToCoords3f(colors[i], colorsCoords, i*6+j);
			}
		}
		return new int[][] {getIBO_IDs(vertexCoords, vtx_indices), new int[]{getVBO_ID(colorsCoords)}, null};
	}

	/**
	 * Cube IBO with color and normals (not interleaved)
	 * @param x
	 * @param y
	 * @param z
	 * @param s
	 * @param colors
	 * @return
	 */
	public static int[][] loadLightedCubeTriangles3f(float x, float y, float z, float s, float [][] colors){
		//TODO not working as intended
		float[] vertexCoords = {x,y,z,	x+s,y,z,	x+s,y+s,z,	x,y+s,z,
				x,y,z-s,	x+s,y,z-s,	x+s,y+s,z-s,	x,y+s,z-s};
		int[] vtx_indices = { 0,1,3,	3,1,2,	1,5,2,	2,5,6,	6,5,4,	4,7,6,	0,3,4,	4,3,7,	
				3,2,6,	6,7,3,	0,5,1,	0,4,5}; // total = 3*2*6 = 6*6 (=>last index 35)
		//expecting per-triangle normals ? or per-Vertex ?
		float[] normalsCoords = new float[6*3*2];
		float[] colorsCoords = new float[6*3*2];
		float[] currentNormal = new float[3];

		//following should be moved to someMath ?
		for (int i=0; i<6;i++){
			currentNormal = getNormal(getVertexFromCoords3f(vertexCoords, vtx_indices[6*i]),
					getVertexFromCoords3f(vertexCoords, vtx_indices[6*i+1]),
					getVertexFromCoords3f(vertexCoords, vtx_indices[6*i+2]));
				mapVectorToCoords3f(currentNormal, normalsCoords, 2*i);
				mapVectorToCoords3f(colors[i], colorsCoords, 2*i);
				mapVectorToCoords3f(currentNormal, normalsCoords, 2*i+1);
				mapVectorToCoords3f(colors[i], colorsCoords, 2*i+1);
		}
	return new int[][] {getIBO_IDs(vertexCoords, vtx_indices), 
			new int[] {getVBO_ID(colorsCoords)}, new int[] {getVBO_ID(normalsCoords)}};
	}


	/**
	 * Made to be used with a FBO rendered to texture.
	 * Composed of 4 triangles 
	 * 		8 
	 * 7 _______9
	 *	|  /|\  |
	 * 	| / | \ |
	 * 4|/_5|__\|6
	 * 	|\  |  /|
	 * 	| \ | / |
	 * 1|__\|/__|3
	 * 		2
	 * @param x
	 * @param y
	 * @param z
	 * @param s
	 * @return
	 */
	public static int[] loadStretchableSquareIBO2f(float x, float y, float z, float s){
		float[] vertices = {x,y,z,	x+s/2,y,z,	x+s,y,z,	
				x,y+s/2,z,	x+s/2,y+s/2,z,	x+s,y+s/2,z,
				x,y+s,z,	x+s/2,y+s,z,	x+s,y+s,z};
		int[] indices = {1,2,4,	2,5,4,	2,6,5,	2,3,6,	4,8,7,	4,5,8,	5,6,8,	6,9,8};
		return getIBO_IDs(vertices, indices);
	}
	
	
	/********************
	 * DRAW IBOs
	 ********************/

	/**
	 * Base overload : only vertices
	 * Draw an IBO given its vertex ID, index ID and number of vertices
	 * @param IDs = int[] {vertex_VBO_id, index_VBO_id, number of vertices}
	 */
	public static void drawTriangles3f(int[] vboIDs){
		if (vboIDs == null){
			System.out.println("NULL IBO ID");
			return;
		}
		glEnableClientState(GL_VERTEX_ARRAY);
		glBindBuffer(GL_ARRAY_BUFFER, vboIDs[0]);
		glVertexPointer(3, GL_FLOAT, 0, 0);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboIDs[1]);
		glDrawElements(GL_TRIANGLES, vboIDs[2], GL_UNSIGNED_INT, 0);

		glDisableClientState(GL11.GL_VERTEX_ARRAY);
	}
	

	/**
	 * Shader overload
	 * Use the shader program, draw the triangle, and release the shader
	 * @param vboIDs = int[] {vertex_VBO_id, index_VBO_id, number of vertices}
	 * @param shader the compiled shader program
	 */
	public static void drawTriangles3f(int[] vboIDs, int shader) {
		GL20.glUseProgram(shader);
		drawTriangles3f(vboIDs);
		GL20.glUseProgram(0);
	}

	/**
	 * Colors and Normals Overload (Not interleaved)
	 * @param vboIDs = {{vtx_IboIDs}, {color_VBOID}, {normals_VBO}}
	 * Send null if you do not want to specify either color or normals or both
	 */
	public static void drawTriangles3f(int[][] vboIDs){

		if (vboIDs == null || vboIDs[0] == null){
			System.out.println("NULL IBO ID");
			return;
		}
		if (vboIDs[1] != null){
			glEnableClientState(GL_COLOR_ARRAY);
			glBindBuffer(GL_ARRAY_BUFFER, vboIDs[1][0]);
			glColorPointer(3, GL_FLOAT, 0, 0);
		}
		if (vboIDs[2] != null){
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

	public static void drawTexturedTriangles3f(int[] iboIDs, int textureVBO){
		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		glBindBuffer(GL_ARRAY_BUFFER, textureVBO);
		GL11.glTexCoordPointer(2, GL_FLOAT, 0, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, iboIDs[0]);
		glVertexPointer(3, GL_FLOAT, 0, 0);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboIDs[1]);
		glDrawElements(GL_TRIANGLES, iboIDs[2], GL_UNSIGNED_INT, 0);
	}
}
