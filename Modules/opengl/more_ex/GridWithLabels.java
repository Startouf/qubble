package more_ex;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static routines.Init.initDisplay;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.UnicodeFont;

import routines.Fonts;
import routines.Init;

public class GridWithLabels {

	private UnicodeFont TNW;
	
	private void start(){
        initDisplay();
        glEnable(GL_CULL_FACE);
        loadFonts();
                
        while(!Display.isCloseRequested()){   
        	glClear(GL_COLOR_BUFFER_BIT | 
					GL_DEPTH_BUFFER_BIT);
        	initGL();
  
            render();
            Display.update();
            Display.sync(60);
        }
        
        Display.destroy();
    }
	
	private void render(){
		Fonts.render(TNW, 50, 50, "testing Fonts", Color.white);
	}
	
	private void loadFonts(){
		TNW = Fonts.TimesNewRoman();
	}
	
	private void initGL(){
		Init.initOrthoView(new double[]{0, 800, 0, 600, 10,-500});
	}
	
	public static void main(String[] args){
		GridWithLabels app = new GridWithLabels();
		app.start();
	}
}
