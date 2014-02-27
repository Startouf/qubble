package opengl;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Dimension;
import java.util.ArrayList;

import more_ex.GridWithLabels;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.Point;
import org.newdawn.slick.TrueTypeFont;

import qubject.AnimationInterface;
import routines.Time;
import routines.VBO;
import sequencer.Qubble;

public class ProjectorOutput implements ImageInterface, Runnable {
	
	private int gridDL;
	private TrueTypeFont font;
	
	//Grid
	private int cursorShaderID; 
	private int cursorPosVBO, cursorColorVBO;
	private float[] cursorVertices;
	private boolean showGrid = true;
	
	//Animations
	//TODO : implement some sort of sync with read/write ?
	private final ArrayList<AnimationControllerInterface> animationController 
		= new ArrayList<AnimationControllerInterface>();
	
	private final ArrayList<Dimension> occupiedTiles = new ArrayList<Dimension>();

	public void start(int width, int height){
        InitRoutines.initDisplay(width, height);
        loadFonts();
        loadDisplayLists();
    	InitRoutines.initView(width, height);
    	loadVBOs();
        
        while(!Display.isCloseRequested()){   
        	glClear(GL_COLOR_BUFFER_BIT | 
					GL_DEPTH_BUFFER_BIT);
        	updateVBOs();
            render();
            Display.update();
            Display.sync(60);
        }
        destroyShaders();
        Display.destroy();
    }

	@Override
	public void ShowGrid() {
		showGrid = !showGrid;
	}

	@Override
	public void triggerQubject(Point qubjectPos) {
		Dimension tile = getTile(qubjectPos);
		for (Dimension dim : occupiedTiles){
			if (dim.width == tile.width && dim.height == tile.height){
				occupiedTiles.remove(dim);
			}
			else{
				occupiedTiles.add(tile);
			}
		}
	}

	public Dimension getTile(Point pos){
		return new Dimension((int)((pos.getX()-Qubble.TABLE_OFFSET_X)/Qubble.SPACING_X),
				(int)((pos.getY()-Qubble.TABLE_OFFSET_Y)/Qubble.SPACING_Y));
	}
	@Override
	public void triggerEffect(java.awt.Point qubjectCoords, AnimationInterface anim) {
		//load entities for the object
		
		//add the object to the render list
		
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
		if (showGrid)
			BaseRoutines.renderList(gridDL);
		
		//Cursor currently drawn as a Quad VBO
		//TODO : Curseur stylé avec shader
		VBORoutines.drawQuadsVBO(cursorPosVBO, cursorColorVBO, 4);
		
		//Highlight tiles where qubjects are present
		for (Dimension dim : occupiedTiles){
			//TODO Highlight the occupiedTile with a nice Shader :)
		}

		
	}

	private void updateVBOs(){
		BaseRoutines.updateCursor(cursorVertices, cursorPosVBO);
	}
	
	private void loadVBOs(){
		cursorVertices = new float[]{
				//TODO : fix, and do not use static (Projector output should be instanciated with a qubble
				Qubble.TABLE_OFFSET_X, Qubble.TABLE_OFFSET_Y, 0,
				Qubble.TABLE_OFFSET_X +Qubble.CURSOR_WIDTH, 2*Qubble.TABLE_OFFSET_Y, 0,
				Qubble.TABLE_OFFSET_X +Qubble.CURSOR_WIDTH, Qubble.TABLE_HEIGHT+Qubble.TABLE_OFFSET_Y, 0,
				Qubble.TABLE_OFFSET_X, Qubble.TABLE_HEIGHT+Qubble.TABLE_OFFSET_Y, 0};
		cursorPosVBO = VBORoutines.loadDynamicDrawVBO(cursorVertices);
		cursorColorVBO = VBORoutines.loadStaticDrawVBO(new float[]{
				1f,1f,0f,	1f,0f,0f,	1f,1f,0f,	1f,0f,0f});
	}
	
	private void loadDisplayLists(){
		gridDL = BaseRoutines.loadLabeledGrid(
				//TODO : check
				new float[]{
						Qubble.TABLE_OFFSET_X, Qubble.TABLE_LENGTH+Qubble.TABLE_OFFSET_X, 
						Qubble.TABLE_OFFSET_Y, Qubble.TABLE_HEIGHT+Qubble.TABLE_OFFSET_Y, 0f,-1f}, 
						new float[]{Qubble.SPACING_X,60f,0f}, 	
						//TODO : using TEST_PERIOD here
				new int[]{2,2,2}, new float[]{1f/Qubble.TABLE_LENGTH*Qubble.TEST_PERIOD,1f/Qubble.TABLE_HEIGHT*100f,1f}, 
				new String[]{"Time", "Effect"}, font);			 
	}
	
	private void loadFonts(){
		font = BaseRoutines.TimesNewsRomanTTF();
	}
	
	private void destroyShaders(){
		//TODO
	}

	//main used as a test
	public static void main(String[] args){
		ProjectorOutput app = new ProjectorOutput();
		app.start(Qubble.TABLE_LENGTH+2*Qubble.TABLE_OFFSET_X, Qubble.TABLE_HEIGHT+2*Qubble.TABLE_OFFSET_Y);
	}

	@Override
	public void run() {
		start(Qubble.TABLE_LENGTH+2*Qubble.TABLE_OFFSET_X, Qubble.TABLE_HEIGHT+2*Qubble.TABLE_OFFSET_Y);
	}
}
