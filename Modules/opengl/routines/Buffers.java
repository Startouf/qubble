package routines;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GLContext;

public final class Buffers
{
	/**
	 * Fast Macro that makes a float buffer
	 * @param values
	 * @return
	 */
	public static FloatBuffer FB(float [] values){ 
		return makeFloatBuffer(values);
	}
	
	public static IntBuffer IB(int [] values){
		return makeIntBuffer(values);
	}
	
	public static FloatBuffer makeFloatBuffer(float[] values)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(values.length);
		buffer.put(values);
		buffer.flip();
		return buffer;
	}
	
	public static IntBuffer makeIntBuffer(int[] values){
		IntBuffer buffer = BufferUtils.createIntBuffer(values.length);
		buffer.put(values);
		buffer.flip();
		return buffer;
	}
	
	public static int createARBVBOID() {
		//ARB VBO_ID and checking if it's possible
		if (GLContext.getCapabilities().GL_ARB_vertex_buffer_object) {
			IntBuffer buffer = BufferUtils.createIntBuffer(1);
			ARBVertexBufferObject.glGenBuffersARB(buffer);
			return buffer.get(0);
		}
		return 0;
	}
	
	//GL15 VBOID
	public static int createGL15VBOID() {
		return GL15.glGenBuffers();
	}
	
	/**
	 * Uses either GL or ARB
	 * (currently GL)
	 * @return VBO ID
	 */
	public static int createVBOID() {
		return createGL15VBOID();
	}
}
