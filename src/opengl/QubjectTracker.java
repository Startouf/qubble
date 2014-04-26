package opengl;

import org.lwjgl.Sys;
import org.lwjgl.opengl.GL20;

import qubject.QRInterface;
import qubject.Qubject;
import routines.Shaders;

import static org.lwjgl.opengl.GL11.*;

public class QubjectTracker {
	/**
	 * Ideas :
	 * 		-O Show a ring around the qubject to show its status
	 * 		-O Change ring color for a "shadow" Qubject
	 * 			(If the qubject is not on the table but we want to show its latest position)
	 * 		-X Show a movement detection effect (ex : temporarily show an arrow or make contours brighter)
	 * 		-O Gradation
	 * 		-O Shader
	 * 		-X Optimise with VBOs
	 */
	private static final double OFFSET = 20d;
	private static final double RADIUS = 5d;
	private static final int TESSELATION = 500; 
	
	//Shader : one per tracker
	private int[] shader;
	private int sourceAddress, maxRadiusAddress, minRadiusAddress, colorNearAddress, colorFarAddress;
	
	private final QRInterface qubject;
	private float lastTimeMoved = 0f;
	private boolean active = false;
	private boolean shadow = false;
	private static String SHADER_PATH = "data/animations/shaders/";

	public QubjectTracker(QRInterface q) {
		this.qubject = q;
	}
	
	public void update(float dt){
		this.lastTimeMoved += dt;
	}
	
	/**
	 * Currently draws 2 full circles : a colored one and a black one
	 * 2 Purposes :
	 * 	* Make sure no animation is projected where the QR-code is
	 * 	* Show the user that the Qubject has been recognized
	 */
	public void renderStatusInstant(){
		if(!active && !shadow){
			return;
		}
		
		float x = qubject.getCoords().getX(), y=qubject.getCoords().getY();
		
//		GL20.glUseProgram(shader[2]);
//		GL20.glUniform2f(sourceAddress, (float)x+Qubject.SIZE/2, (float)y+Qubject.SIZE/2); 
		
		double cos, sin, theta;
		glBegin(GL_TRIANGLE_STRIP);
		for (int i=0; i<=TESSELATION; i++){
			theta = i*2*Math.PI/(double)TESSELATION;
			cos = Math.cos(i);
			sin = Math.sin(i);
			if(shadow){
				glColor4f(0.9f, 0.1f, 0.1f, 1f);
			} else{
				GL20.glUniform4f(colorNearAddress, 0.1f, 0.5f, 0.1f, 0.8f);
				glColor4f(0.1f, 0.5f, 0.1f, 0.3f);
			}
			glVertex3d(x+(OFFSET+Qubject.SIZE/2d)*cos, y+(OFFSET+Qubject.SIZE/2d)*sin, -2d);
			if(shadow){
				glColor4f(0.9f, 0.1f, 0.1f, 1f);
			} else{
				GL20.glUniform4f(colorNearAddress, 0.1f, 1.0f, 0.1f, 0.8f);
				glColor4f(0.1f, 1.0f, 0.1f, 0.3f);
			}
			glVertex3d(x+(OFFSET+RADIUS+Qubject.SIZE/2d)*cos, y+(OFFSET+RADIUS+Qubject.SIZE/2d)*sin, -2d);
		}
		glEnd();
		GL20.glUseProgram(0);
		
		glColor4f(0f,0f,0f,1f);
		glBegin(GL_QUADS);
		glVertex3f(x-Qubject.SIZE/2f, y-Qubject.SIZE/2f, -2f);
		glVertex3f(x+Qubject.SIZE/2f, y-Qubject.SIZE/2f, -2f);
		glVertex3f(x+Qubject.SIZE/2f, y+Qubject.SIZE/2f, -2f);
		glVertex3f(x-Qubject.SIZE/2f, y+Qubject.SIZE/2f, -2f);
		glEnd();
		
		
	}
	
	public void setActive(boolean active){
		lastTimeMoved = 0f;
		this.active = active;
	}
	
	public void loadShader(){
		String[] attrib = new String[]{"source", "maxRadius", "minRadius", "colorNear", "colorFar"};
		shader = Shaders.loadShadersGL(SHADER_PATH + "lazyVertex.vp", SHADER_PATH + "gradation.fp", attrib);
		GL20.glUseProgram(shader[2]);
		sourceAddress = GL20.glGetUniformLocation(shader[2], "source");
		
		maxRadiusAddress = GL20.glGetUniformLocation(shader[2], "maxRadius");
		GL20.glUniform1f(maxRadiusAddress, (float) (Qubject.SIZE+OFFSET+RADIUS)); 
		
		minRadiusAddress = GL20.glGetUniformLocation(shader[2], "minRadius");
		GL20.glUniform1f(minRadiusAddress, (float) (Qubject.SIZE+OFFSET)); 
		
		colorNearAddress = GL20.glGetUniformLocation(shader[2], "colorNear");
		GL20.glUniform4f(colorNearAddress, 0.1f, 0.5f, 0.1f, 0.8f);
		
		colorFarAddress = GL20.glGetUniformLocation(shader[2],  "colorFar");
		GL20.glUniform4f(colorFarAddress, 0.1f, 1.0f, 0.1f, 0.8f);
		GL20.glUseProgram(0);
	}
	
}
