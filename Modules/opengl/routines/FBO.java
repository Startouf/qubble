package routines;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import static org.lwjgl.opengl.GL14.*;

import org.lwjgl.opengl.GL30;

import static org.lwjgl.opengl.GL30.*;

public class FBO {
	/**
	 * Width|Height overload
	 * Make a FBO and attach a texture, and performs completeness check
	 * The FBO is not binded at the end
	 * Has a Color Buffer and a Depth Buffer
	 * @param width of the FBO
	 * @param height of the FBO
	 * @return {a validated FBO_ID, texture_ID} 
	 */
	public static int[] makeFBO(int width, int height){
		int FBO_ID = makeFBOBuffer();
		glBindFramebuffer(GL_FRAMEBUFFER, FBO_ID);
		int texture_ID = makeTextureForFBO(width, height);
		attachTexture(texture_ID, FBO_ID);
		checkFBO(FBO_ID);
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		return new int[]{FBO_ID, texture_ID};
	}

	public static int makeFBOBuffer(){
		return glGenFramebuffers();
	}
	
	public static int makeTextureForFBO(int width, int height){
		int textureID = GL11.glGenTextures();
		int depthRenderBufferID = GL30.glGenRenderbuffers();
		
		//Color Buffer
		glBindTexture(GL_TEXTURE_2D, textureID);									// Bind the colorbuffer texture
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);				// make it linear filterd
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0,GL_RGBA, GL_INT, (java.nio.ByteBuffer) null);	// Create the texture data
		glFramebufferTexture2D(GL30.GL_FRAMEBUFFER,GL30.GL_COLOR_ATTACHMENT0,GL_TEXTURE_2D, textureID, 0);// bind it to the FBO

		// initialize depth renderbuffer
		glBindRenderbuffer(GL_RENDERBUFFER, depthRenderBufferID);				// bind the depth renderbuffer
		glRenderbufferStorage(GL_RENDERBUFFER, GL14.GL_DEPTH_COMPONENT24, width, height);	// get the data space for it
		glFramebufferRenderbuffer(GL_FRAMEBUFFER,GL_DEPTH_ATTACHMENT,GL_RENDERBUFFER, depthRenderBufferID); // bind it to the FBO
		
		glBindRenderbuffer(GL_RENDERBUFFER, 0);
		return textureID;
	}
	
	public static void attachTexture(int textureID, int FBO_ID){
		glBindFramebuffer( GL_FRAMEBUFFER, FBO_ID );
		glFramebufferTexture2D( GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0,
		                GL_TEXTURE_2D, textureID, 0);
		glBindFramebuffer( GL_FRAMEBUFFER, 0 );
	}
	
	public static void checkFBO(int FBO_ID){
		int framebuffer = glCheckFramebufferStatus( GL_FRAMEBUFFER ); 
		switch ( framebuffer ) {
			case GL_FRAMEBUFFER_COMPLETE:
				break;
			case GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT:
				throw new RuntimeException( "FrameBuffer: " + FBO_ID
						+ ", has caused a GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT exception" );
			case GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT:
				throw new RuntimeException( "FrameBuffer: " + FBO_ID
						+ ", has caused a GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT exception" );
				//BAD NAME 
//			case GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS:
//				throw new RuntimeException( "FrameBuffer: " + FBO_ID
//						+ ", has caused a GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS exception" );
			case GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER:
				throw new RuntimeException( "FrameBuffer: " + FBO_ID
						+ ", has caused a GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER exception" );
				//BAD NAME
//			case GL_FRAMEBUFFER_INCOMPLETE_FORMATS:
//				throw new RuntimeException( "FrameBuffer: " + FBO_ID
//						+ ", has caused a GL_FRAMEBUFFER_INCOMPLETE_FORMATS exception" );
			case GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER:
				throw new RuntimeException( "FrameBuffer: " + FBO_ID
						+ ", has caused a GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER exception" );
			default:
				throw new RuntimeException( "Unexpected reply from glCheckFramebufferStatusEXT: " + framebuffer );
		}
	}
	/**
	 * Bind a FBO
	 */
	public static void bindFBO(int FBO_ID){
		glBindTexture(GL_TEXTURE_2D, 0);
		glBindFramebuffer( GL_FRAMEBUFFER, FBO_ID );	
	}
	
	public static void unbindFBO(){
		glBindFramebuffer( GL_FRAMEBUFFER, 0);
	}
}
