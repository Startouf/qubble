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
	public static int loadStaticDrawIBO(int [] values) 
	{
		IntBuffer indicesBuffer = IB(values); 
		int vbo_id = createVBOID();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbo_id);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
		return vbo_id;
	}

	/**
	 * Return a vertex VBO with its IBO and length 
	 * Data STATIC_DRAW --> Read often, wrote only once
	 * @param vertices VertexCoords
	 * @param indices index indexes
	 * @return int[] {float VBO ID, index IBO ID, number of indexes}
	 */
	public static int[] getStaticDrawIBO_IDs(float[] vertices, int[] indices){
		if (vertices == null || indices == null){
			System.out.println("NULL IBO ID");
		}

		return new int[] {loadStaticDrawVBO(vertices), loadStaticDrawIBO(indices), indices.length};
	}

	/**
	 * Return a vertex VBO with its IBO and length 
	 * Data DYNAMIC_DRAW --> Read often, wrote often
	 * (Only applies to the VBO, not the IBO)
	 * @param vertices VertexCoords
	 * @param indices index indexes
	 * @return int[] {float VBO ID, index IBO ID, number of indexes}
	 */
	public static int[] getDynamicDrawIBO_IDs(float[] vertices, int[] indices){
		if (vertices == null || indices == null){
			System.out.println("NULL IBO ID");
		}

		return new int[] {loadDynamicDrawVBO(vertices), loadStaticDrawIBO(indices), indices.length};
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
		return getStaticDrawIBO_IDs(vertices, indices);
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
		return getStaticDrawIBO_IDs(vertices, indices);
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
		float[] colorsCoords = new float[6*3]; //Expecting one color3f per indexed_vertex
		for (int i=0; i<6;i++){ //For each face
				mapVectorToCoords3f(colors[i], colorsCoords, i);
		}
		return new int[][] {getStaticDrawIBO_IDs(vertexCoords, vtx_indices), new int[]{getVBO_ID(colorsCoords)}, null};
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
		//expecting per-Vertex normals and colors
		float[] normalsCoords = new float[6*3];
		float[] colorsCoords = new float[6*3];
		float[] currentNormal = new float[3];

		//following should be moved to someMath ?
		for (int i=0; i<6;i++){
			currentNormal = getNormal(getVertexFromCoords3f(vertexCoords, i),
					getVertexFromCoords3f(vertexCoords, i+1),
					getVertexFromCoords3f(vertexCoords, i+2));
				mapVectorToCoords3f(currentNormal, normalsCoords, i);
				mapVectorToCoords3f(colors[i], colorsCoords, i);
		}
	return new int[][] {getStaticDrawIBO_IDs(vertexCoords, vtx_indices), 
			new int[] {getVBO_ID(colorsCoords)}, new int[] {getVBO_ID(normalsCoords)}};
	}

	/**
	 * A nice looking cross
	 * @param x
	 * @param y
	 * @param z
	 * @param s
	 * @param colors 17 colors are needed. Using color3f
	 * @return
	 */
	public static int[][] loadColoredCross2f(float x, float y, float z, float s, float[][] colors){
		float[] vertices= new float[]{
			    0.0f, 0.0f, 0.0f, 1.0f,1.0f, 1.0f, 1.0f, 1.0f,
			    // Top
			     -0.2f, 0.8f, 0.0f, 1.0f,0.0f, 1.0f, 0.0f, 1.0f,
			     0.2f, 0.8f, 0.0f, 1.0f,0.0f, 0.0f, 1.0f, 1.0f,
			    0.0f, 0.8f, 0.0f, 1.0f,0.0f, 1.0f, 1.0f, 1.0f,
			    0.0f, 1.0f, 0.0f, 1.0f,1.0f, 0.0f, 0.0f, 1.0f,
			    // Bottom
			      -0.2f, -0.8f, 0.0f, 1.0f,0.0f, 0.0f, 1.0f, 1.0f,
			    0.2f, -0.8f, 0.0f, 1.0f,0.0f, 1.0f, 0.0f, 1.0f,
			    0.0f, -0.8f, 0.0f, 1.0f,0.0f, 1.0f, 1.0f, 1.0f,
			    0.0f, -1.0f, 0.0f, 1.0f,1.0f, 0.0f, 0.0f, 1.0f,
			    // Left
			    -0.8f, -0.2f, 0.0f, 1.0f,0.0f, 1.0f, 0.0f, 1.0f,
			    -0.8f, 0.2f, 0.0f, 1.0f,0.0f, 0.0f, 1.0f, 1.0f,
			    -0.8f, 0.0f, 0.0f, 1.0f,0.0f, 1.0f, 1.0f, 1.0f,
			    -1.0f, 0.0f, 0.0f, 1.0f,1.0f, 0.0f, 0.0f, 1.0f,
			    // Right
			    0.8f, -0.2f, 0.0f, 1.0f,0.0f, 0.0f, 1.0f, 1.0f,
			    0.8f, 0.2f, 0.0f, 1.0f,0.0f, 1.0f, 0.0f, 1.0f,
			    0.8f, 0.0f, 0.0f, 1.0f,0.0f, 1.0f, 1.0f, 1.0f,
			    1.0f, 0.0f, 0.0f, 1.0f,1.0f, 0.0f, 0.0f, 1.0f};
		
		int[] indices = new int[]{
			    // Top
			    0, 1, 3,    0, 3, 2,3, 1, 4,3, 4, 2,
			    // Bottom
			    0, 5, 7,0, 7, 6,7, 5, 8,7, 8, 6,
			    // Left
			    0, 9, 11,0,	 11, 10,11, 9, 12,	11, 12, 10,
			    // Right
			    0, 13, 15,	0, 15, 14,	15, 13, 16,	15, 16, 14
			};
		float[] colorsCoords = new float[17*3];
		//following should be moved to someMath ?
		for (int i=0; i<17;i++){
			mapVectorToCoords3f(colors[i], colorsCoords, i);
		}
		return new int[][] {getStaticDrawIBO_IDs(vertices, indices), 
				new int[] {getVBO_ID(colorsCoords)}, null};
	}

	/**
	 * Made to be used with a FBO rendered to texture.
	 * Composed of 4 triangles 
	 * 		7 
	 * 6 _______8
	 *	|  /|\  |
	 * 	| / | \ |
	 * 3|/_4|__\|5
	 * 	|\  |  /|
	 * 	| \ | / |
	 * 0|__\|/__|2
	 * 		1
	 * @param x
	 * @param y
	 * @param z z-plane
	 * @param s side
	 * @return int[][] {{IBO_ID, indices}, {TexCoordsVBO_ID} }
	 */
	public static int[][] loadStretchableSquareIBO2f(float x, float y, float z, float s){
		float[] vertices = {x,y,z,	x+s/2,y,z,	x+s,y,z,	
				x,y+s/2,z,	x+s/2,y+s/2,z,	x+s,y+s/2,z,
				x,y+s,z,	x+s/2,y+s,z,	x+s,y+s,z};
		float[] texCoords = {0f,0f,	0.5f,0f,	1f,0f,	
							0f,0.5f,	0.5f,0.5f,	1f,0.5f,
							0f,1f,	0.5f,1f,	1f,1f};
		int[] indices = {0,1,3,	1,4,3,	1,5,4,	1,2,5,	3,7,6,	3,4,7,	4,5,7,	5,8,7};
		return new int[][] {getDynamicDrawIBO_IDs(vertices, indices), new int[]{VBO.getVBO_ID(texCoords)}};
	}
	
	
	/********************
	 * DRAW IBOs
	 ********************/

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
	public static void drawTriangles3f(int[] vboIDs, int shader) {
		GL20.glUseProgram(shader);
		drawTriangles3f(vboIDs);
		GL20.glUseProgram(0);
	}

	/**
	 * Colors and Normals Overload (Not interleaved)
	 * @param vboIDs = {{vtx_VBO, IBO, indices}, {color_VBOID}, {normals_VBO}}
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
			glNormalPointer(GL_FLOAT, 0, 0); //no size parameter: normals are always 3D vectors
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

	/**
	 * 
	 * @param iboIDs {vtx_VBO, IBO, indices}
	 * @param textureVBO	texVBO_ID
	 */
	public static void drawTexturedTriangles3f(int[] iboIDs, int textureVBO, int texture){
		glEnableClientState(GL_VERTEX_ARRAY);
		glBindBuffer(GL_ARRAY_BUFFER, iboIDs[0]);
		glVertexPointer(3, GL_FLOAT, 0, 0);
		
		glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		glBindTexture(GL11.GL_TEXTURE_2D, texture);
		glBindBuffer(GL_ARRAY_BUFFER, textureVBO);
		GL11.glTexCoordPointer(2, GL_FLOAT, 0, 0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboIDs[1]);
		glDrawElements(GL_TRIANGLES, iboIDs[2], GL_UNSIGNED_INT, 0);
	}
}
