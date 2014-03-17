package debug;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Dimension;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;

import more_ex.GridWithLabels;

import opengl.AnimationControllerInterface;
import opengl.BaseRoutines;
import opengl.InitRoutines;
import opengl.OutputImageInterface;
import opengl.VBORoutines;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.Point;
import org.newdawn.slick.TrueTypeFont;

import explosion.PixelExplosion;

import qubject.AnimationInterface;
import routines.Time;
import routines.VBO;
import sequencer.Qubble;
import wave.WaterWave;

public class AnimationDebug implements OutputImageInterface {
	
	private int gridDL;
	private TrueTypeFont fontTNR;
	
	//Grid
	private int cursorShaderID; 
	private int cursorPosVBO, cursorColorVBO;
	private float cursorPos = 0f;
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
	public boolean playPause = true;
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
        debug();
    	//TODO : add another closeRequested boolean check for external change (project closed...)
        while(!Display.isCloseRequested()){   
        	isPlaying();
        	loadNewAnims();
        	glClear(GL_COLOR_BUFFER_BIT | 
					GL_DEPTH_BUFFER_BIT);
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

	public Dimension getTile(org.lwjgl.util.Point pos){
		return new Dimension((int)((pos.getX()-Qubble.TABLE_OFFSET_X)/Qubble.SPACING_X),
				(int)((pos.getY()-Qubble.TABLE_OFFSET_Y)/Qubble.SPACING_Y));
	}
	
	@Override
	public void triggerEffect(Point qubjectCoords, AnimationInterface anim) {
		//get the controller for the animation
		AnimationControllerInterface controller;
//		try {
//			controller = AnimationClassLoader.compileAndLoadAnimation(anim.getFile());
//			//load entities for the object
//			needsToBeLoaded.add(controller);
//			
//			//add the object to the render list
//			activeAnimations.add(controller);
//			
//		} catch (CannotCompileAnimationException e) {
//			e.printStackTrace();
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (InstantiationException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		}
	}

	@Override
	public void triggerOtherEffect(AnimationInterface anim) {
		
	}

	/**
	 * Vérifie si Qubble/Le séquenceur ont demandé à mettre en pause
	 */
	public void isPlaying(){
		if (playPause = false){
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
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
			playPause = false;
		}
	}

	/**
	 * Rendu des routines de base (Curseur, grille)
	 * Rendu des animations de activeAnimations
	 */
	private void render(){
		//Grid
		if (showGrid){
			glColor3f(1f,1f,1f);
			BaseRoutines.renderList(gridDL);
		}

		//Cursor 
		//TODO : Curseur stylé avec shader
		VBORoutines.drawQuadsVBO(cursorPosVBO, cursorColorVBO, 4);
		
		//Highlight tiles where qubjects are present
		glColor3f(0.5f,0.5f,0f);
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
		float dt = BaseRoutines.getDt(lastFrameTime);
		
		//Update cursor
		BaseRoutines.updateCursor(cursorPos, cursorVertices, cursorPosVBO, dt);
		
		//Update animations
		updateAnimations(dt);
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
		for (AnimationControllerInterface anim : needsToBeLoaded){
			anim.load();
			activeAnimations.add(anim);
		}
		needsToBeLoaded.clear();
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
				new int[]{2,2,2}, new float[]{1f/Qubble.TABLE_LENGTH*Qubble.TEST_PERIOD_SEC,1f/Qubble.TABLE_HEIGHT*100f,1f}, 
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
		AnimationDebug app = new AnimationDebug();
		app.start(Qubble.TABLE_LENGTH+2*Qubble.TABLE_OFFSET_X, Qubble.TABLE_HEIGHT+2*Qubble.TABLE_OFFSET_Y);
	}

//	@Override
//	public void run() {
//		start(Qubble.TABLE_LENGTH+2*Qubble.TABLE_OFFSET_X, Qubble.TABLE_HEIGHT+2*Qubble.TABLE_OFFSET_Y);
//	}

	@Override
	public void terminate() {
		//TODO : ass boolean to request close
	}
}