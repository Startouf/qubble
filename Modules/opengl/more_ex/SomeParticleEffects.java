package more_ex;

import static org.lwjgl.opengl.GL11.*;
import static routines.Init.HEIGHT;
import static routines.Init.WIDTH;
import static routines.Init.initDisplay;

import java.util.ArrayList;

import opengl.AnimationControllerInterface;
import opengl.VBORoutines;

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
import routines.Time;
import routines.VBO;
import sequencer.Qubble;
import wave.WaterWave;

public class SomeParticleEffects {

	private TrueTypeFont TTF_TNR;
	private int gridDL;
	private int cursorPosVBO, cursorColorVBO;
	private float [] cursorVertices;
	public static float cursorWidth = 10f;
	private ArrayList<AnimationControllerInterface> animations;
	private long startTime = Time.getTime();
	private boolean triggerAnim1 = false;
	
	private void start(){
        initDisplay();
        loadFonts();
        loadDisplayLists();
        loadVBOs();
    	initView();
        
        while(!Display.isCloseRequested()){   
        	glClear(GL_COLOR_BUFFER_BIT | 
					GL_DEPTH_BUFFER_BIT);
        	addAnimation();
        	loadAdditionalEffects();
        	updateVBOs();
            render();
            Display.update();
            Display.sync(60);
        }
        
        Display.destroy();
    }
	/**
	 * TEST some animations at a given time
	 */
	private void addAnimation(){
		//Play something at 3 sec
		if (Time.getTime() - startTime > 3000){
			WaterWave ww = new WaterWave(new Point(95, 95));
			animations.add(ww);
		}
	}
	
	private void addAnimation(){
		
	}
	
	private void render(){
		DisplayLists.renderList(gridDL);
		Squares.squareFromFan(95f, 95f, 30f);
		VBO.drawQuadsVBO(cursorPosVBO, cursorColorVBO, 4);
		
		for (AnimationControllerInterface anim : animations){
			anim.renderAnimation();
		}
	}
	
	private void updateVBOs(){
		float newPos = Time.uniformModulusTranslation(
				Qubble.TABLE_OFFSET_X, Qubble.TABLE_LENGTH, 1f/60f);
		cursorVertices[0] = newPos;
		cursorVertices[3] = newPos+cursorWidth;
		cursorVertices[6] = newPos+cursorWidth;
		cursorVertices[9] = newPos;
		VBO.overwrite(cursorPosVBO, cursorVertices);
	}
	
	private void loadVBOs(){
		cursorVertices = new float[]{
				Qubble.TABLE_OFFSET_X - cursorWidth/2, Qubble.TABLE_OFFSET_Y, 0,
				Qubble.TABLE_OFFSET_X + cursorWidth/2, Qubble.TABLE_OFFSET_Y, 0,
				Qubble.TABLE_OFFSET_X + cursorWidth/2, Qubble.TABLE_HEIGHT, 0,
				Qubble.TABLE_OFFSET_X - cursorWidth/2, Qubble.TABLE_HEIGHT, 0};
		cursorPosVBO = VBORoutines.loadDynamicDrawVBO(cursorVertices);
		cursorColorVBO = VBORoutines.loadStaticDrawVBO(new float[]{
				1f,1f,0f,	1f,0f,0f,	1f,1f,0f,	1f,0f,0f});
	}
	
	private void loadDisplayLists(){
		gridDL = DisplayLists.loadLabeledGrid(
				new float[]{50f, 700f, 50f, 500f, 0f,-1f}, new float[]{60f,60f,0f}, 	//Position of the grid and spacing
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
