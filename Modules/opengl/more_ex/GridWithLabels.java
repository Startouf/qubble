package more_ex;

import static org.lwjgl.opengl.GL11.*;
import static routines.Init.HEIGHT;
import static routines.Init.WIDTH;
import static routines.Init.initDisplay;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;
import org.lwjgl.util.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.UnicodeFont;

import routines.DisplayLists;
import routines.Fonts;
import routines.Grids;
import routines.Init;
import routines.Squares;

public class GridWithLabels {

	private TrueTypeFont TTF_TNR;
	private int gridDL;
	
	private void start(){
        initDisplay();
        loadFonts();
        loadDisplayLists();
    	initView();
        
        while(!Display.isCloseRequested()){   
        	glClear(GL_COLOR_BUFFER_BIT | 
					GL_DEPTH_BUFFER_BIT);
  
            render();
            Display.update();
            Display.sync(60);
        }
        
        Display.destroy();
    }
	
	private void render(){
		glColor3f(1f,1f,1f);
		DisplayLists.renderList(gridDL);
		Squares.squareFromFan(95f, 95f, 30f);
	}
	
	private void loadDisplayLists(){
		gridDL = DisplayLists.loadLabeledGrid(
				new float[]{50f, 700f, 50f, 500f, 0f,-1f}, new float[]{40f,40f,0f}, 	//Position of the grid and spacing
				new int[]{2,2,2}, new float[]{1f,1f,1f}, new String[]{"Time", "Effect"}, TTF_TNR);			//Labels : spacing between labels, multiplier for labels, axisName, font
	}
	
	private void loadFonts(){
		TTF_TNR = Fonts.TimesNewsRomanTTF();
	}
	
	private void initView(){
		Init.initOrthoView(new double[]{0, 800, 600, 0, 10,-500});
	}
	
	public static void main(String[] args){
		GridWithLabels app = new GridWithLabels();
		app.start();
	}
}
