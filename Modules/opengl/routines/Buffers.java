package routines;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GLContext;

public final class Buffers
{
	//fast macro.
	//When coding is finished, should use the case-sensitive find and replace to replace FB by makeFLoatBuffer
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
	
	//ARB VBOID and checking if it's possible
	public static int createARBVBOID() {
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
	
	//Easily switch between GL15 and ARB with this :
	public static int createVBOID() {
		return createGL15VBOID();
	}
}
