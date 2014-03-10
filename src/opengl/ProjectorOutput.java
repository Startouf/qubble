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
	 * TODO : synchronization read/write ?
	 */
	private final ArrayList<AnimationControllerInterface> activeAnimations 
		= new ArrayList<AnimationControllerInterface>(20); //Nombre empirique
	private final ArrayList<AnimationControllerInterface> needsToBeLoaded
		= new ArrayList<AnimationControllerInterface>(5);  //Nombre Empirique
	/**
	 * TODO : partager avec Qubble/Séquenceur avec le mot clé volatile ?
	 */
	private final ArrayList<Dimension> occupiedTiles = new ArrayList<Dimension>();
	
	//Other
	public volatile boolean playPause = true;
	/**
	 * Time spent during playPause
	 * (must be substracted to DT before updating animations
	 * 
	 */
	private float DTPause = 0f;
	private long lastFrameTime = Sys.getTime();

	private void debug(){
		triggerQubject(new Point(400,400));
		needsToBeLoaded.add(new PixelExplosion(new Point (400,400)));
		
		triggerQubject(new Point(600, 300));
		activeAnimations.add(new WaterWave(new Point(595,325)));
	}
	
	public void start(int width, int height){
        InitRoutines.initDisplay(width, height);
        loadFonts();
        loadDisplayLists();
    	InitRoutines.initView(width, height);
    	loadVBOs();
        
    	//TODO : add another closeRequested boolean check for external change (project closed...)
        while(!Display.isCloseRequested()){   
        	loadNewAnims();
        	glClear(GL_COLOR_BUFFER_BIT | 
					GL_DEPTH_BUFFER_BIT);
        	isPlaying();
        	updateVBOs();
            render();
            Display.update();
            Display.sync(60);
        }
        destroy();
        Display.destroy();
    }

	@Override
	public void toggleGrid() {
		showGrid = !showGrid;
	}

	@Override
	public void triggerQubject(Point qubjectPos) {
		Dimension tile = getTile(qubjectPos);
		for (Dimension dim : occupiedTiles){
			if (dim.width == tile.width && dim.height == tile.height){
				occupiedTiles.remove(dim);
				return;
			}
		}
		occupiedTiles.add(tile);
	}

	//TODO : move to Qubble
	public Dimension getTile(org.lwjgl.util.Point pos){
		return new Dimension((int)((pos.getX()-Qubble.TABLE_OFFSET_X)/Qubble.SPACING_X),
				(int)((pos.getY()-Qubble.TABLE_OFFSET_Y)/Qubble.SPACING_Y));
	}

	@Override
	public void triggerEffect(Point qubjectCoords, AnimationInterface anim) {
		//get the controller for the animation
		//TODO : Reflection to load the class from .class file
		AnimationControllerInterface controller;
//		controller = new WaterWave(qubjectCoords);
//		URLClassLoader classLoader = URLClassLoader.newInstance(
//				new URL[] { anim.getAnimationControllerDotClass().toURI().toURL() });
//		Class<?> cls = Class.forName("anim..class", true, classLoader); // Should print "hello".
//		Object instance = cls.ne // Should print "world".
//		System.out.println(instance);
//		//load entities for the object (sync with openGL thread)
		
		//TODO : temp method
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
	 * Vérifie si Qubble/Le séquenceur ont demandé à mettre en pause
	 */
	public void isPlaying(){
		if (playPause = false){
			//Save the DT that should've been used
			long stop = Sys.getTime(); 
			try {
				this.wait();
			} catch (InterruptedException e) {
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
		if (t.isInterrupted()){
			playPause = true;
			t.interrupt();
		}
		else{
			//Synchronisation done with volatile keyword
			playPause = !playPause;
		}
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
		//Update cursor
		BaseRoutines.updateCursor(cursorVertices, cursorPosVBO);
		
		//Update animations
		//TODO Check if only one dt computation is enough for all the animations !!
		updateAnimations(BaseRoutines.getDt(lastFrameTime)-DTPause);
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
				activeAnimations.add(anim);
			}
			needsToBeLoaded.clear();
		}
	}

	/**
	 * Chargement des VBO de base (curseur)
	 */
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
	
	private void loadFonts(){
		fontTNR = BaseRoutines.TimesNewsRomanTTF();
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
		//TODO delete la displayList
		
		//Destroy des animations
		for (AnimationControllerInterface anim : activeAnimations){
			anim.destroy();
			//Le garbage collector devrait se charger du reste
		}
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

	@Override
	public void terminate() {
		//TODO : ass boolean to request close
	}
}
