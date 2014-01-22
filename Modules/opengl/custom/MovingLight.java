package custom;

import static org.lwjgl.opengl.GL11.*;
import static routines.Buffers.FB;
import static routines.Init.*;
import static routines.IBO.*;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.Point;

import routines.Grids;
import routines.IBO;
import routines.Init;
import routines.Squares;
import routines.Transformations;
import static routines.Time.*;

public class MovingLight
{
	/*		 /y		Light ¤ --> moves along x axis
	 * 		/_____________|_________________
	 * 	   /			  |				  /|
	 * 	  /				 _|_			 / |
	 * 	 /				 / \			/  |
	 * 	/______________________________/__________\x
	 *  |							  |			  /
	 *  |							  |
	 *  |							  |
	 */
	//Light is at positive z => shadows of cubes to the (x,y,0) plane
	private final float lightz = 100;
	private FloatBuffer lightPosition;
	private int[][][] qubjects = new int[10][][];
	private Point[] qubjectPositions;
	private float[][] colors = new float[][]{
			{1f,0f,0f}, {0f,1f,0f}, {1f,0f,0f}, {0f,1f,0f}, {0f,0f,1f}, {0f,0f,1f}};

	public void start(){
		initDisplay();
		glEnable(GL_CULL_FACE);
		
		Init.initOrthoView(new double[]{-5,WIDTH, -5, HEIGHT, +300d, -10d});
		loadQubjectVBOs();
		selectStartingPositions();
		
		while (!Display.isCloseRequested()){
			glClear(GL_COLOR_BUFFER_BIT | 
					GL_DEPTH_BUFFER_BIT);
			initCursorLighting();
						
			moveLight();
			
			renderTable();
			renderCubes();
			
			Display.update(); 
			Display.sync(FPS);
		}
		Display.destroy();
	}
	
	private void moveLight(){
		lightPosition = FB(new float[]{uniformModulusTranslation(0f, (float)WIDTH, 1f/10f), 
				HEIGHT/2, lightz, 0f });
	}
	
	private void renderTable(){
		//Should be a display list : 
		Grids.drawGrid3f(new float[]{0f,WIDTH,0f,HEIGHT,0f,0f}, new float[]{50f,50f,50f});
	}
	
	private void renderCubes(){
		for (int i=0; i<qubjects.length; i++){
			IBO.drawIBOTriangles3f(qubjects[i]);
		}
	}
	
	private void loadQubjectVBOs(){
		for (int i=0; i<qubjects.length; i++){
			qubjects[i]=IBO.loadLightedCubeIBOTriangles3f(0f, 0f, 0f, 0f, colors);
		}
	}
	
	private void initCursorLighting(){
		FloatBuffer mat_specular = FB(new float[] { 1f, 1f, 1f, 1f });
		Float mat_shininess = 50f;
		lightPosition = FB(new float[]{ 0f, HEIGHT/2, lightz, 0f });
		glClearColor (0f, 0f, 0f, 0f);
		glShadeModel (GL_SMOOTH);

		glMaterial(GL_FRONT, GL_SPECULAR, mat_specular);
		glMaterialf(GL_FRONT, GL_SHININESS, mat_shininess);
		glLight(GL_LIGHT0, GL_POSITION, lightPosition);

		glEnable(GL_LIGHTING);
		glEnable(GL_LIGHT0);
		glEnable(GL_DEPTH_TEST);
	}
	
	private void selectStartingPositions(){
		qubjectPositions = new Point[qubjects.length];
		for (int i=0; i<qubjects.length; i++){
			qubjectPositions[i] = new Point(100*i, 100*i);
		}
	}
	
	public static void main(String[] args){
		MovingLight app = new MovingLight();
		app.start();
	}
}
