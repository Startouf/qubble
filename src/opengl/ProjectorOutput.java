package opengl;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Point;

import more_ex.GridWithLabels;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.TrueTypeFont;

import qubject.AnimationInterface;
import routines.Time;
import sequencer.Qubble;

public class ProjectorOutput implements ImageInterface {
	
	private int gridDL;
	private TrueTypeFont font;
	private int cursorShaderID; 
	private int[] cursorIBO;

	private void start(int width, int height){
        InitRoutines.initDisplay(width, height);
        loadFonts();
        loadDisplayLists();
    	InitRoutines.initView(width, height);
        
        while(!Display.isCloseRequested()){   
        	glClear(GL_COLOR_BUFFER_BIT | 
					GL_DEPTH_BUFFER_BIT);
            render();
            Display.update();
            Display.sync(60);
        }
        
        Display.destroy();
    }

	@Override
	public void ShowGrid(float spacing) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void triggerQubject(Point qubject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void triggerEffect(Point qubjectCoords, AnimationInterface anim) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void triggerOtherEffect(AnimationInterface anim) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playPause() {
		// TODO Auto-generated method stub
		
	}
	
	private void render(){
		//TODO : add uniformModulusTranslation to BaseRoutines
		glColor3f(1f,1f,1f);
		BaseRoutines.renderList(gridDL);
		BaseRoutines.renderCursor(Time.uniformModulusTranslation(
				Qubble.TABLE_OFFSET_X, Qubble.TABLE_LENGTH, 1f/60f), cursorIBO, cursorShaderID);
	}
	
	private void loadDisplayLists(){
		gridDL = BaseRoutines.loadLabeledGrid(
				new float[]{50f, 700f, 50f, 500f, 0f,-1f}, new float[]{40f,40f,0f}, 	//Position of the grid and spacing
				new int[]{2,2,2}, new float[]{1f,1f,1f}, new String[]{"Time", "Effect"}, font);			//Labels : spacing between labels, multiplier for labels, axisName, font
	}
	
	private void loadFonts(){
		font = BaseRoutines.TimesNewsRomanTTF();
	}
	
	public static void main(String[] args){
		ProjectorOutput app = new ProjectorOutput();
		app.start(800, 600);
	}
}
