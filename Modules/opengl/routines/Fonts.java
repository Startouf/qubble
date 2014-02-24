package routines;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Font;
import java.io.InputStream;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.util.ResourceLoader;

public class Fonts {
	
	public static void loadFontEssentials(){
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	/**
	 * TrueTypeFont Times New Roman with antialisaing
	 * @return TrueTypeFont TNR(slick)
	 */
	public static TrueTypeFont TimesNewsRomanTTF(){
		loadFontEssentials();
		return(new TrueTypeFont(new Font("Times New Roman", Font.BOLD, 24), true));
	}

	public static TrueTypeFont customFont(String path) {
		loadFontEssentials();
		// load font from a .ttf file
		try {
			InputStream inputStream	= ResourceLoader.getResourceAsStream(path);

			Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont2 = awtFont2.deriveFont(24f); // set font size
			return(new TrueTypeFont(awtFont2, false));

		} catch (Exception e) {
			e.printStackTrace();
		}	
		return null;
	}

	public static void render(TrueTypeFont font, float x, float y, String text, Color color) {
		//TODO : Use glPushAttrib instead
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL_CULL_FACE);
		font.drawString(x, y, text, color);
		GL11.glEnable(GL_CULL_FACE);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
	/**
	 * Render a TrueTypeFont UpsideDown (swaps the view to Ortho (ymin =600, ymax = 0)
	 * @param font
	 * @param x
	 * @param y
	 * @param text
	 * @param color
	 */
	public static void renderUpsideDown(TrueTypeFont font, float x, float y, String text, Color color) {
		//TODO : Use glPushAttrib instead
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL_CULL_FACE);
		
		glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPushMatrix();
		glLoadIdentity();
		glOrtho(0, 800, 600, 00, 300, -600);
		font.drawString(x, y, text, color);
		GL11.glPopMatrix();
		glMatrixMode(GL_MODELVIEW);
		
		GL11.glEnable(GL_CULL_FACE);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}   
	/**
	 * Optimized for multiple consecutive font rendering
	 * (Avoid Enabling/Disabling GL_Attributes)
	 * Encapsulate all renderMultiple by glDisable-> Enable (GL_DEPTH_TEST|GL_CULLING), Enable->Disable GL_TEXTURE_2D
	 * At the end, glDisable(GL_TEXTURE_2D)
	 * @param font
	 * @param x
	 * @param y
	 * @param text
	 * @param color
	 */
	public static void renderMultiple(TrueTypeFont font, float x, float y, String text, Color color){
		font.drawString(x, y, text, color);
	}

}