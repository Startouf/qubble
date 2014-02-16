package routines;

import static org.lwjgl.opengl.EXTFramebufferObject.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL11;

public class FBO {
	/**
	 * Width|Height overload
	 * Make a FBO and attach a texture, and performs completeness check
	 * The FBO is not binded at the end
	 * @param width of the FBO
	 * @param height of the FBO
	 * @return a validated FBO_ID 
	 */
	public static int makeFBO(int width, int height){
		return makeFBO(makeTextureForFBO(width, height));
	}
	
	/**
	 * texture ID overload. 
	 * Make a FBO and attach a texture, and performs completeness check
	 * The FBO is not binded at the end
	 * @param texture_ID
	 * @return a valid FBO_ID
	 */
	public static int makeFBO(int texture_ID){
		int FBO_ID = makeFBOBuffer();
		attachTexture(texture_ID, FBO_ID);
		checkFBO(FBO_ID);
		return FBO_ID;
	}

	public static int makeFBOBuffer(){
		return glGenFramebuffersEXT();
	}
	
	public static int makeTextureForFBO(int width, int height){
		int colorTextureID = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, colorTextureID);									// Bind the colorbuffer texture
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);				// make it linear filterd
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0,GL_RGBA, GL_INT, (java.nio.ByteBuffer) null);	// Create the texture data
		glFramebufferTexture2DEXT(GL_FRAMEBUFFER_EXT,GL_COLOR_ATTACHMENT0_EXT,GL_TEXTURE_2D, colorTextureID, 0);
		return colorTextureID;
	}
	
	public static void attachTexture(int textureID, int FBO_ID){
		glBindFramebufferEXT( GL_FRAMEBUFFER_EXT, FBO_ID );
		glFramebufferTexture2DEXT( GL_FRAMEBUFFER_EXT, GL_COLOR_ATTACHMENT0_EXT,
		                GL_TEXTURE_2D, textureID, 0);
		glBindFramebufferEXT( GL_FRAMEBUFFER_EXT, 0 );
	}
	
	public static void checkFBO(int FBO_ID){
		int framebuffer = glCheckFramebufferStatusEXT( GL_FRAMEBUFFER_EXT ); 
		switch ( framebuffer ) {
			case GL_FRAMEBUFFER_COMPLETE_EXT:
				break;
			case GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT_EXT:
				throw new RuntimeException( "FrameBuffer: " + FBO_ID
						+ ", has caused a GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT_EXT exception" );
			case GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT_EXT:
				throw new RuntimeException( "FrameBuffer: " + FBO_ID
						+ ", has caused a GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT_EXT exception" );
			case GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS_EXT:
				throw new RuntimeException( "FrameBuffer: " + FBO_ID
						+ ", has caused a GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS_EXT exception" );
			case GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER_EXT:
				throw new RuntimeException( "FrameBuffer: " + FBO_ID
						+ ", has caused a GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER_EXT exception" );
			case GL_FRAMEBUFFER_INCOMPLETE_FORMATS_EXT:
				throw new RuntimeException( "FrameBuffer: " + FBO_ID
						+ ", has caused a GL_FRAMEBUFFER_INCOMPLETE_FORMATS_EXT exception" );
			case GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER_EXT:
				throw new RuntimeException( "FrameBuffer: " + FBO_ID
						+ ", has caused a GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER_EXT exception" );
			default:
				throw new RuntimeException( "Unexpected reply from glCheckFramebufferStatusEXT: " + framebuffer );
		}
	}
	/**
	 * Bind a FBO with a viewPort of the size of the texture
	 * (0,0, textureWidth, textureHeight)
	 * And clear color buffer
	 */
	public static void bindFBO(int FBO_ID, int textureWidth, int textureHeight){
		glBindFramebufferEXT( GL_FRAMEBUFFER_EXT, FBO_ID );
		GL11.glPushAttrib(GL11.GL_VIEWPORT_BIT);
		GL11.glViewport( 0, 0, textureWidth, textureHeight );
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	}
	
	public static void unbindFBO(){
		glBindFramebufferEXT( GL_FRAMEBUFFER_EXT, 0);
		GL11.glPopAttrib();
	}
}
