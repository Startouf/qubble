package more_ex;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static routines.Init.initDisplay;
import opengl.VBORoutines;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.TrueTypeFont;

import routines.DisplayLists;
import routines.Fonts;
import routines.IBO;
import routines.Init;
import routines.Squares;
import routines.Time;
import routines.VBO;
import sequencer.Qubble;

public class Beautiful {

	private int[][] crossIBOs;
	
	private void start(){
        initDisplay();
        loadVBOs();
    	initView();
        
        while(!Display.isCloseRequested()){   
        	glClear(GL_COLOR_BUFFER_BIT | 
					GL_DEPTH_BUFFER_BIT);
        	updateVBOs();
            render();
            Display.update();
            Display.sync(60);
        }
        
        Display.destroy();
    }
	
	private void render(){
		IBO.drawTriangles3f(crossIBOs);
	}
	
	private void updateVBOs(){
		
	}
	
	private void loadVBOs(){
		IBO.loadColoredCross2f(300f, 300f, 0f, 300f, new float[][]{
				{1f,1f,1f}, {1f,1f,0f},{1f,0f,1f},{0f,1f,1f},{1f,0f,0f},{0f,1f,0f},{0f,0f,1f},
				{1f,1f,1f}, {1f,1f,0f},{1f,0f,1f},{0f,1f,1f},{1f,0f,0f},{0f,1f,0f},
				{1f,1f,1f}, {1f,1f,0f},{1f,0f,1f},{0f,1f,1f},{1f,0f,0f},{0f,1f,0f}
		});
	}
	
	private void initView(){
		Init.initOrthoView(new double[]{0, 800, 600, 0, 10,-500});
	}
	
	public static void main(String[] args){
		Beautiful app = new Beautiful();
		app.start();
	}
}