package routines;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import static routines.Buffers.*;
import static routines.someMath.*;

public final class VBO
{
	public static int createVBOID() {
		if (GLContext.getCapabilities().GL_ARB_vertex_buffer_object) {
			IntBuffer buffer = BufferUtils.createIntBuffer(1);
			ARBVertexBufferObject.glGenBuffersARB(buffer);
			return buffer.get(0);
		}
		return 0;
		//Note : could be achieved by returning GL15.glGenBuffers();
	}

	//Uses GL_STATIC_DRAW
	public static int load_float_vbo(float [] values)
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

	public static int load_index_vbo(int [] values, Integer nb_indices)
	{
		//chargeons les données dans un IntBuffer
		IntBuffer indicesBuffer = BufferUtils.createIntBuffer(values.length);
		indicesBuffer.put(values);
		indicesBuffer.flip(); //NE PAS OUBLIER CETTE LIGNE!! ELLE PEUT CRASHER VOTRE JavaVM

		//creons un VBO dans la mémoire du GPU (pas encore de données associées))
		int vbo_id = createVBOID();

		//et copions les données dans la mémoire du GPU
		if(GLContext.getCapabilities().GL_ARB_vertex_buffer_object) {
			ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB, vbo_id);
			ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB, indicesBuffer, ARBVertexBufferObject.GL_STATIC_DRAW_ARB);
		} else {
			System.out.println("VBOs NOT SUPPORTED !!");
		}

		//souvenons nous du nombre d'index dans notre VBO:
		nb_indices = values.length;      
		return vbo_id;
	}
	
	//drawArray Overload (not IBO)
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
	      vertex_vbo_id = load_float_vbo(vertices);
	    }
	
	//drawArrayElements Overload  (IBO)
	public static void loadTriangleVBO(float w, float h, float d, Integer vertex_vbo_id, Integer index_vbo_id, Integer nb_indices) {
	      //nous avons déja crée le VBO, inutile de recommencer ...
	      if (vertex_vbo_id != 0) return;

	      //creons un VBO qui contient nos vertex - nous avons besoin de 3 sommets
	      w/=2;
	      h/=2;
	      d/=2;
	      float[] vertices = new float[] {
	        -w, h, d,
	        w, h, d,
	        w, -h, d,
	      };
	      vertex_vbo_id = load_float_vbo(vertices);
	      
	      //creons un VBO qui contient les index des sommets dans les deux triangles de notre cube
	      int[] indices = new int[]
	      {
	        0,1,2
	      };
	      index_vbo_id = load_index_vbo(indices, nb_indices);

	      System.out.println("VBOs Setup - Vertex ID " + vertex_vbo_id + " - index ID " + index_vbo_id + " - nb indices " + nb_indices);
	    }
	
	//Indexed overload
	public static void drawTriangleVBO(int vertex_vbo_id, int index_vbo_id, int color_vbo_id, int nb_indices) { //TODO : add params for ID
		if (vertex_vbo_id==0) return;
		if (index_vbo_id==0) return;

//		if (use_shader) {
//			ARBShaderObjects.glUseProgramObjectARB(program);
//		}

		glEnableClientState(GL11.GL_VERTEX_ARRAY);
		ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, vertex_vbo_id);
		glVertexPointer(3, GL11.GL_FLOAT, 0, 0);

		if (color_vbo_id != 0) {
			glEnableClientState(GL11.GL_COLOR_ARRAY);
			ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, color_vbo_id);
			glColorPointer(4, GL11.GL_FLOAT, 0, 0);
		}

		//attachons le buffer d'indices comme le buffer 'ELEMENT_ARRAY', i.e. celui utilisé pour glDrawElements
		ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB, index_vbo_id);

		glDrawElements(GL11.GL_TRIANGLES, nb_indices, GL11.GL_UNSIGNED_INT, 0);

		glDisableClientState(GL11.GL_VERTEX_ARRAY);
		if (color_vbo_id != 0) GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);

//		if (use_shader) {
//			ARBShaderObjects.glUseProgramObjectARB(0);
//		}
	}

	//basic overload : only vertices. Not working ?
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
		int vbo_id = load_float_vbo(new float[]{
				x,y,z,	x+s,y,z, 	x,y+s,z,	x,y+s,z,	x,y+s,z,	x,y,z,
				x+s,y,z,	x+s,y+s,z+s,	x+s,y+s,z,		x+s,y+s,z,	x+s,y,z+s,	x+s,y+s,z+s,
				x+s,y,z+s,	x,y,z+s,	x,y+s,z+s,	x,y+s,z+s,	x+s,y+s,z+s,	x+s,y,z+s,
				x,y,z,	x,y+s,z,	x,y+s,z+s,	x,y+s,z+s,	x,y,z+s,	x,y,z,
				x,y+s,z,	x+s,y+s,z,	x+s,y+s,z+s,	x+s,y+s,z+s,	x,y+s,z+s,	x,y+s,z+s,
				x,y,z,	x,y,z+s,	x+s,y,z+s,	x+s,y,z+s,	x+s,y,z,	x,y,z
		});
		return vbo_id;
	}
	
	//advanced overload : vertices, color and normal interleaved
	
	//Non-indexed overload, only vertices
	public static void drawCubeVBOTriangles(int vertex_vbo_id, int color_vbo_id){
		if (vertex_vbo_id==0) return;
		glEnableClientState(GL_VERTEX_ARRAY);
		glBindBuffer(GL_ARRAY_BUFFER, vertex_vbo_id);
		glVertexPointer(3, GL_FLOAT, 0, 0); //3D

		if (color_vbo_id != 0) {
			glEnableClientState(GL11.GL_COLOR_ARRAY);
			glBindBuffer(GL_ARRAY_BUFFER, color_vbo_id);
			glColorPointer(4, GL11.GL_FLOAT, 0, 0);
		}

		glDrawArrays(GL11.GL_TRIANGLES, 0, 6*2); //elements = number of triangles or vertices ?

		glDisableClientState(GL11.GL_VERTEX_ARRAY);
		if (color_vbo_id != 0) GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	//Interleave triangle vertices, color and normal
	// 3 vertices => 9 floats
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
