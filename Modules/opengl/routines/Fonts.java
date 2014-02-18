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
	 * @param font
	 * @param x
	 * @param y
	 * @param color
	 */
	public static void render(UnicodeFont font, float x, float y, String text, Color color) {
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL_CULL_FACE);
		font.drawString(x, y, text, color);
		//TODO : check first if the params had been enabled
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL_CULL_FACE);
	}    
}