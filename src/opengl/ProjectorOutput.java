package opengl;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Iterator;

import more_ex.GridWithLabels;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.Point;
import org.newdawn.slick.TrueTypeFont;

import explosion.PixelExplosion;
import qubject.AnimationInterface;
import routines.Time;
import routines.VBO;
import sequencer.Qubble;
import wave.WaterWave;

public class ProjectorOutput implements OutputImageInterface, Runnable {
	
	private int gridDL;
	private TrueTypeFont fontTNR;
	
	//Grid
	private int cursorShaderID; 
	private int cursorPosVBO, cursorColorVBO;
	private float[] cursorVertices;
	private boolean showGrid = true;
	
	//Animations
	/**
	 * Liste des animations actives
	 * Utilisé par : render, updateVBOs (qui vérifie aussi si l'animation s'est terminée ou pas)
	 */
	private final ArrayList<AnimationControllerInterface> activeAnimations 
		= new ArrayList<AnimationControllerInterface>(20); //Nombre empirique
	private final ArrayList<AnimationControllerInterface> needsToBeLoaded
		= new ArrayList<AnimationControllerInterface>(5);  //Nombre Empirique
	/**
	 * TODO : move to Qubble/Séquenceur ?
	 */
	private final ArrayList<Dimension> occupiedTiles = new ArrayList<Dimension>();
	
	//Other
	public volatile boolean isPlaying = true, hasStarted = false;
	/**
	 * Time spent during playPause
	 * (must be substracted to DT before updating animations
	 * 
	 */
	private float DTPause = 0f;
	private long lastFrameTime = Sys.getTime();
	private Float cursorPos;

	private void debug(){
		
	}
	
	public void start(int width, int height){
        InitRoutines.initDisplay(width, height);
        loadFonts();
        loadDisplayLists();
    	InitRoutines.initView(width, height);
    	loadCursorVBOs();
        
    	//TODO : add another closeRequested boolean check for external change (project closed...)
        while(!Display.isCloseRequested()){   
        	loadNewAnims();
        	glClear(GL_COLOR_BUFFER_BIT | 
					GL_DEPTH_BUFFER_BIT);
        	isPlaying();
        	updateVBOs();
            render();
            Display.update();
            Display.sync(30);
        }
        destroy();
        Display.destroy();
    }

	@Override
	public void triggerQubject(Point qubjectPos) {
		Dimension tile = Qubble.getTile(qubjectPos);
		for (Dimension dim : occupiedTiles){
			if (dim.width == tile.width && dim.height == tile.height){
				occupiedTiles.remove(dim);
				return;
			}
		}
		occupiedTiles.add(tile);
	}

	@Override
	public void triggerEffect(Point qubjectCoords, AnimationInterface anim) {
		//get the controller for the animation
		//TODO : Reflection to load the class from CLASS Object provided by the animationInterface
		AnimationControllerInterface controller;
		
		//Dummy load
		if (anim.getName().equals("Water wave"))
			controller = new WaterWave(qubjectCoords);
		else
			controller = new PixelExplosion(qubjectCoords);
		
		synchronized(needsToBeLoaded){
			needsToBeLoaded.add(controller);
		}
		//Will be moved to activeAnimations during the load() method
	}

	@Override
	public void triggerOtherEffect(AnimationInterface anim) {
		
	}

	/**
	 * Rendu des routines de base (Curseur, grille)
	 * Rendu des animations de activeAnimations
	 */
	private void render(){
		//Grid
		GL11.glColor3f(1f, 1f, 1f);
		if (showGrid)
			BaseRoutines.renderList(gridDL);
		
		//Cursor 
		//TODO : Curseur stylé avec shader
		VBORoutines.drawQuadsVBO(cursorPosVBO, cursorColorVBO, 4);
		
		//Highlight tiles where qubjects are present
		GL11.glColor3f(0.8f, 0f, 0f);
		for (Dimension dim : occupiedTiles){
			BaseRoutines.HighlightTile(dim);
		}
		
		//Render animations
		for (AnimationControllerInterface anim : activeAnimations){
			anim.renderAnimation();
		}
	}

	/**
	 * Mise à jour des VBO de base (curseur)
	 * et mise à jour de la liste d'animations  
	 */
	private void updateVBOs(){
		float dt = BaseRoutines.getDt(lastFrameTime)-DTPause;
		
		//Update cursor
		if(isPlaying)
			BaseRoutines.updateCursor(cursorPos, cursorVertices, cursorPosVBO, dt);
		
		//Update animations
		updateAnimations(dt);
		DTPause = 0f;
		lastFrameTime = Sys.getTime();
	}

	/**
	 * Mise à jour de l'animation
	 * La méthode vérifie si l'application est terminée. 
	 * Si c'est le cas, elle demande sa destruction (libération rez GPU) 
	 * ...puis l'enlève de la liste activeAnimations  
	 * @param dt
	 */
	private void updateAnimations(float dt){
		//Update Animations (Check whether they are running or not)
		Iterator<AnimationControllerInterface> iter = activeAnimations.iterator();
		//No need to sync cause activeAnimations only called in openGL Thread
		while(iter.hasNext()){
			AnimationControllerInterface anim = iter.next();
			if (anim.updateAnimation(dt) == false)
				iter.remove();
		}
	}

	/**
	 * Chargement des nouvelles animations depuis la liste needsToBeLoaded
	 * ...Puis cette liste est vidée
	 */
	private void loadNewAnims(){
		synchronized(needsToBeLoaded){
			for (AnimationControllerInterface anim : needsToBeLoaded){
				anim.load();
				//No need to sync cause activeAnimations only called in openGL Thread
				activeAnimations.add(anim);
			}
			needsToBeLoaded.clear();
		}
	}

	/**
	 * Chargement des VBO de base (curseur)
	 */
	private void loadCursorVBOs(){
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

	/**
	 * Chargement des DisplayList de base (Grid)
	 */
	private void loadDisplayLists(){
		gridDL = BaseRoutines.loadLabeledGrid(
				//TODO : check
				new float[]{
						Qubble.TABLE_OFFSET_X, Qubble.TABLE_LENGTH+Qubble.TABLE_OFFSET_X, 
						Qubble.TABLE_OFFSET_Y, Qubble.TABLE_HEIGHT+Qubble.TABLE_OFFSET_Y, 0f,-1f}, 
						new float[]{Qubble.SPACING_X,60f,0f}, 	
						//TODO : using TEST_PERIOD here
				new int[]{2,2,2}, new float[]{1f/Qubble.TABLE_LENGTH*Qubble.TEST_PERIOD,1f/Qubble.TABLE_HEIGHT*100f,1f}, 
				new String[]{"Time", "Effect"}, fontTNR);			 
	}

	/**
	 * IMPORTANT : Relâcher les ressources GPU
	 * (Le Garbage Collector s'occuper de Java mais PAS du GPU)
	 * (à moins de terminer la VM)
	 */
	private void destroy(){
		//Si appelé par un autre thread, ne pas oublier de mettre le thread en pause !!!!!
		//RQ : Avec le Garbage Collector, 
		
		//Destroy des objets de base
		GL15.glDeleteBuffers(cursorPosVBO);
		GL15.glDeleteBuffers(cursorColorVBO);
		GL11.glDeleteLists(gridDL, 1);//TODO delete la displayList
		
		//Destroy des animations
		for (AnimationControllerInterface anim : activeAnimations){
			anim.destroy();
			//Le garbage collector devrait se charger du reste
		}
	}
	
	private void loadFonts(){
		fontTNR = BaseRoutines.TimesNewsRomanTTF();
	}

	//main used as a test
	public static void main(String[] args){
		ProjectorOutput app = new ProjectorOutput();
		app.start(Qubble.TABLE_LENGTH+2*Qubble.TABLE_OFFSET_X, Qubble.TABLE_HEIGHT+2*Qubble.TABLE_OFFSET_Y);
	}

	@Override
	public void run() {
		isPlaying = true;
		start(Qubble.TABLE_LENGTH+2*Qubble.TABLE_OFFSET_X, Qubble.TABLE_HEIGHT+2*Qubble.TABLE_OFFSET_Y);
	}
	
	@Override
	public void toggleGrid() {
		showGrid = !showGrid;
	}

	@Override
	public void terminate() {
		//TODO : use boolean to request close
	}
	
	/**
	 * Vérifie si Qubble/Le séquenceur ont demandé à mettre en pause
	 * DEPUIS LE THREAD OPENGL
	 */
	public void isPlaying(){
		if (isPlaying = false && hasStarted){
			//Save the DT that should've been used
			long stop = Sys.getTime(); 
			try {
				this.wait();
			} catch (InterruptedException e) {//clear the interrupt flag
				e.printStackTrace();
			}
			DTPause = (float)((Sys.getTime()-stop)*1000/Sys.getTimerResolution());
		}
	}

	/**
	 * Méthode appelée par Qubble/Le séquenceur pour mettre en pause
	 * DEPUIS UN AUTRE THREAD
	 */
	@Override
	public void playPause(Thread t) {
		if(hasStarted){
			//Synchronisation done with volatile keyword
			isPlaying = !isPlaying;
			
			//TODO : handle both play and pause with an Interruption by adding throws PlayPause exception everywhere ???
			// http://docs.oracle.com/javase/tutorial/essential/concurrency/interrupt.html
		}
	}
}
