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

	//Shortcut
	public static int getVBO_ID(float[] values){
		return (load_float_vbo(values));
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
		int vbo_id = load_float_vbo(new float[]{
				x,y,z,	x+s,y,z, 	x,y+s,z,	x,y+s,z,	x+s,y,z,	x+s,y+s,z,
				x+s,y,z,	x+s,y,z-s,	x+s,y+s,z,		x+s,y+s,z,	x+s,y,z-s,	x+s,y+s,z-s,
				x+s,y,z-s,	x,y,z-s,	x,y+s,z-s,	x,y+s,z-s,	x+s,y+s,z-s,	x+s,y,z-s,
				x,y,z,	x,y+s,z,	x,y+s,z-s,	x,y+s,z-s,	x,y,z-s,	x,y,z,
				x,y+s,z,	x+s,y+s,z,	x+s,y+s,z-s,	x+s,y+s,z-s,	x,y+s,z-s,	x,y+s,z,
				x,y,z,	x,y,z-s,	x+s,y,z-s,	x+s,y,z-s,	x+s,y,z,	x,y,z
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
