package routines;

import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.glEnable;

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
	
	/**
	 * Times New Roman (from JAVA awt) with antialisaing, colored white
	 * @return TureTypeFont (slick)
	 */
	public static UnicodeFont TimesNewRoman(){
		loadFontEssentials();
		UnicodeFont font = new UnicodeFont(new Font("Times New Roman", Font.BOLD, 24));
		font.getEffects().add(new ColorEffect(java.awt.Color.white));
	    font.addAsciiGlyphs();
	    try {
	        font.loadGlyphs();
	    } catch (SlickException ex) {
	       // Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
	    }
	    return font;
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

	/**
	 * NOTE : you can directly print front using yourFront.drawString() !
	 * BUT : can only draw if CULL_FACE DISABLED
	 * @param font
	 * @param x
	 * @param y
	 * @param color
	 */
	public static void render(UnicodeFont font, float x, float y, String text, Color color) {
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		font.drawString(x, y, text, color);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}   

	/**
	 * Optimized for multiple consecutive font rendering
	 * (Avoid Enabling/Disabling GL_Attributes)
	 * Encapsulate all renderMultiple by glDisable-> Enable GL_DEPTH_TEST
	 * At the end, glDisable(GL_TEXTURE_2D)
	 * @param font
	 * @param x
	 * @param y
	 * @param text
	 * @param color
	 */
	public static void renderMultiple(UnicodeFont font, float x, float y, String text, Color color){
		font.drawString(x, y, text, color);
	}
	
	public static void loadFontEssentials(){
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}
}