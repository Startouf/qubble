package opengl;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import more_ex.GridWithLabels;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.Point;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.TextureImpl;

import explosion.PixelSpray;
import qubject.AnimationInterface;
import qubject.QRInterface;
import routines.Time;
import routines.VBO;
import sequencer.Qubble;
import sequencer.QubbleInterface;
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
	
	private final ArrayList<Dimension> occupiedTiles = new ArrayList<Dimension>();	//OLD : to remove ASAP
	
	private final Hashtable<QRInterface, QubjectTracker> trackers;
	
	/**
	 * Volatile because those booleans are changed by other Threads
	 */
	public volatile boolean isPlaying = false, hasStarted = false;
	/**
	 * Note : dt is always computed even when it's paused
	 * (The only thing that matters is whether we update the animations with dt or not :) )
	 * 
	 */
	private long lastFrameTime;
	private Float cursorPos = new Float(Qubble.TABLE_OFFSET_X);
	private final QubbleInterface qubble;

	public ProjectorOutput(QubbleInterface qubble) {
		this.qubble = qubble;
		this.trackers = new Hashtable<QRInterface, QubjectTracker>();
		initTrackers();
	}

	private void initTrackers(){
		for(QRInterface qubject : qubble.getAllQubjects()){
			trackers.put(qubject, new QubjectTracker(qubject));
		}
	}
	
	public void start(int width, int height){
		lastFrameTime = Sys.getTime();
        InitRoutines.initDisplayOnSecondDevice(width, height);
    	InitRoutines.initView(width, height);
    	loadResources();
    	
    	//TODO : add another closeRequested boolean check for external change (project closed...)
        while(!Display.isCloseRequested()){   
        	loadNewAnims();
        	glClear(GL_COLOR_BUFFER_BIT | 
					GL_DEPTH_BUFFER_BIT);
        	update();
            render();
            Display.update();
            Display.sync(30);
        }
        destroy();
        Display.destroy();
    }

	private void loadResources() {
		loadFonts();
        loadDisplayLists();
        loadCursorVBOs();
        QubjectTracker.loadShader();
	}

	@Override
	public void highlightQubject(Point qubjectPos) {
		Dimension tile = new Dimension(Qubble.getTile(qubjectPos), qubjectPos.getY());
		Iterator<Dimension> iter = occupiedTiles.iterator();
		while(iter.hasNext()){
			Dimension dim = iter.next();
			if (dim.width == tile.width && dim.height == tile.height){
				//If the tile is already highlighted, remove it
				iter.remove();
				return;
			}
		}
		
		//If not found, then highlight the tile
		occupiedTiles.add(tile);
	}

	/**
	 * Used by Sequencer thread (asynchronous)
	 */
	@Override
	public void triggerEffect(Point qubjectCoords, AnimationInterface anim) {
		//get the controller for the animation
		AnimationControllerInterface controller;
		
		try {
			Class<?> clazz = anim.getAnimationControllerClass();
			Constructor<?> cstr= clazz.getConstructor(Point.class); 
			controller = (AnimationControllerInterface) 
					cstr.newInstance(qubjectCoords);
			
			//Will be moved to activeAnimations during the load() method
			synchronized(needsToBeLoaded){
				needsToBeLoaded.add(controller);
			}
			
			return;
			
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void triggerOtherEffect(AnimationInterface anim) {
		//TODO
	}

	/**
	 * Rendu des routines de base (Curseur, grille)
	 * Rendu des animations de activeAnimations
	 */
	private void render(){
		//Grid
		if (showGrid){
			//Note : comment the below line to make a nice effect with the grid
			GL11.glColor3f(1f, 1f, 1f);
			//Below line sucks but no other fix found to 
			glBindTexture(GL_TEXTURE_2D, 1);
			BaseRoutines.renderList(gridDL);
		}
		
		//Highlight tiles where qubjects are present
		GL11.glColor3f(0.5f, 0f, 0f);
		synchronized(occupiedTiles){
			for (Dimension dim : occupiedTiles){
				BaseRoutines.HighlightTile(dim);
			}

		}
		
		//Cursor 
		//TODO : Curseur stylé avec shader
		VBORoutines.drawQuadsVBO(cursorPosVBO, cursorColorVBO, 4);
		
		//Note : toggle the if(isPlaying) to show frozen animations
//		if(hasStarted){
			//Render animations
			for (AnimationControllerInterface anim : activeAnimations){
				anim.renderAnimation();
			}
//		}
			
		//Trackers
		QubjectTracker.useShader();
		for(QubjectTracker tracker : trackers.values()){
			tracker.renderStatusInstant();
		}
		GL20.glUseProgram(0);
	}

	/**
	 * Mise à jour des VBO de base (curseur)
	 * et mise à jour de la liste d'animations  
	 */
	private void update(){
		//Idea : whether the Qubble is Paused or not : compute dt !!!
		//So that there is no problem of "removing  the 
		
		float dt = BaseRoutines.getDt(lastFrameTime);
		lastFrameTime = Sys.getTime();
		
		if(isPlaying)
		{
			//Update cursor
			cursorPos = BaseRoutines.updateCursor(cursorPos, cursorVertices, cursorPosVBO, dt);
		
			//Update animations
			updateAnimations(dt);
		}
		
		//trackers
		for (QubjectTracker tracker : trackers.values()){
			tracker.update(dt);
		}
		
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
						new float[]{Qubble.SPACING_X,Qubble.SPACING_Y,0f}, 	
				new int[]{2,2,2}, new float[]{1f/Qubble.TABLE_LENGTH*(Qubble.LOOP_MS/1000f),1f/Qubble.TABLE_HEIGHT*100f,1f}, 
				new String[]{"Time", "Effect"}, fontTNR);			 
	}

	/**
	 * IMPORTANT : Relâcher les ressources GPU
	 * (Le Garbage Collector s'occuper de Java mais PAS du GPU)
	 * (à moins de terminer la VM)
	 */
	private void destroy(){
		//Si appelé par un autre thread, ne pas oublier d'arreter le thread OpenGL !!!!!
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

	@Override
	public void run() {
		start(Qubble.TABLE_LENGTH+2*Qubble.TABLE_OFFSET_X, Qubble.TABLE_HEIGHT+2*Qubble.TABLE_OFFSET_Y);
	}
	
	@Override
	public void toggleGrid() {
		showGrid = !showGrid;
	}

	@Override
	public void terminate() {
		//TODO : use boolean to request close (2x bool :one to check user didn't click on X, on to check user didn't close project)
	}

	/**
	 * Méthode appelée par Qubble/Le séquenceur pour mettre en pause
	 * DEPUIS UN AUTRE THREAD
	 */
	@Override
	public void playPause() {
		hasStarted = true;
		isPlaying = !isPlaying;
	}

	@Override
	public void trackQubject(QRInterface qubject) {
		trackers.get(qubject).setActive(true);
	}

	@Override
	public void stopTrackingQubject(QRInterface qubject) {
		trackers.get(qubject).setActive(false);
	}
}
