package routines;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GLContext;
/**
 * 
 * @author Cyril
 * Class used to create and manipulate [type]Buffers (Legacy GL_[type] )
 * Shortcuts for manipulating position and rotation buffers 
 *
 */
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

	/**
	 * Update a Buffer WITHOUT CHANGING ITS LENGTH
	 * @param FB
	 */
	public static void update(FloatBuffer FB, float[] values){
		FB.rewind();
		FB.put(values);
		FB.flip();
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

	/**
	 * 
	 * @return A 4x4 Identity Matrix
	 */
	public static FloatBuffer IdentityMatrix(){
		return FB(new float[]{	1f,0f,0f,0f,
								0f,1f,0f,0f,
								0f,0f,1f,0f,
								0f,0f,0f,1f,});
	}
	
	public static FloatBuffer vec3(){
		FloatBuffer FB = BufferUtils.createFloatBuffer(3);
		FB.flip();
		return FB;
	}

	/**
	 * Sets the x,y,z position in a (Position) FloatBuffer 4x4Matrix
	 * (Positions 12,13 and 14)
	 * @param FB the floatBuffer
	 * @param x
	 * @param y
	 * @param z
	 */
	public static void setPos(FloatBuffer FB, float x, float y, float z){
		FB.put(12,x);
		FB.put(13,y);
		FB.put(14,z);
		FB.flip(); //Not sure this has to be done
	}

	/**
	 * Sets the rotation in a (Rotation) FloatBuffer 4x4Matrix
	 * @param FB the FloatBuffer
	 * @param deg Angle in degrees
	 */
	public static void setZRot(FloatBuffer FB, float deg){
		float cos = (float)Math.cos(deg);
		float sin = (float)Math.sin(deg);
		FB.put(0,cos);
		FB.put(1,sin);
		FB.put(4,-sin);
		FB.put(4,cos);
		FB.flip(); //Not sure this has to be done
	}
}
