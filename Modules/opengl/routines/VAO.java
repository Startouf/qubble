package routines;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static routines.Buffers.FB;
import static routines.Buffers.createVBOID;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class VAO
{
	/**
	 * Load a VAO with non-interleaved VBOs (each with 3 floats)
	 * @param VBOs
	 * @return
	 */
	public static int loadVAO3f(int[] VBOs)
	{
		int VAOID = createVBOID(), i=0;
		GL30.glBindVertexArray(VAOID);
		for (int vboID : VBOs){
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
			GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		}
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
		return VAOID;
	}

	//Shortcut
	public static int getVAOID(int[] VBOs){
		return (loadVAO3f(VBOs));
	}
	
	public static int createVAOID(){
		return GL30.glGenVertexArrays();
	}
	
	/**
	 * Draw a non-interleaved VAO (each VBO with 3 floats)
	 * For shaders, use Overload
	 * @param vaoID
	 * @param vertexCount
	 * @param attributes number of attributes (vertex, etc...)
	 */
	public static void drawVAO(int vaoID, int vertexCount, int attributes){
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

		// Bind to the VAO that has all the information about the quad vertices
		GL30.glBindVertexArray(vaoID);
		for (int i=0; i<attributes;i++){
			GL20.glEnableVertexAttribArray(i);
		}
		// Draw the vertices
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertexCount);

		// Put everything back to default (deselect)
		for (int i=0; i<attributes;i++){
			GL20.glDisableVertexAttribArray(i);
		}
		GL30.glBindVertexArray(0);
	}
}
