package routines;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Textures {
	
	public static int makeTextureBuffer(){
		return GL11.glGenTextures();
	}
	
	public static Texture loadTexture(String file){
		Texture texture = null;
		try {
			// load texture from PNG file
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(file));
 
			System.out.println("Texture loaded: "+texture);
			System.out.println(">> Image width: "+texture.getImageWidth());
			System.out.println(">> Image height: "+texture.getImageHeight());
			System.out.println(">> Texture width: "+texture.getTextureWidth());
			System.out.println(">> Texture height: "+texture.getTextureHeight());
			System.out.println(">> Texture ID: "+texture.getTextureID());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return texture;
	}
}
