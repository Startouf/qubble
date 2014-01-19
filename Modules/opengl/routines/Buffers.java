package routines;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public final class Buffers
{
	//fast macro.
	//When coding is finished, should use the case-sensitive find and replace to replace FB by makeFLoatBuffer
	public static FloatBuffer FB(float [] values){
		return makeFloatBuffer(values);
	}
	
	public static FloatBuffer makeFloatBuffer(float[] values)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(values.length);
		buffer.put(values);
		buffer.flip();
		return buffer;
	}
}
