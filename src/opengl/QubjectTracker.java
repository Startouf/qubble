package opengl;

import java.awt.Image;
import java.io.IOException;

import org.lwjgl.Sys;
import org.lwjgl.opengl.GL20;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import calibration.Calibrate;

import qubject.QRInterface;
import qubject.Qubject;
import routines.Fonts;
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
	private static final double OFFSET = 30d;
	private static final double RADIUS = 5d;
	private static final int TESSELATION = 500; 
	private static final boolean USE_SHADER = false;
	private static final float PULSE_FREQ = 1f;
	
	//Shader : one per tracker
	private int[] shader;
	private int sourceAddress, maxRadiusAddress, minRadiusAddress, colorNearAddress, colorFarAddress;
	
	private final QRInterface qubject;
	private Texture footprintImage;
	private float lastTimeMoved = 0f;
	private boolean active = false;
	private boolean footprint = true;
	private static String SHADER_PATH = "data/animations/shaders/";
	private float playingTime = 0f;

	public QubjectTracker(QRInterface q) {
		this.qubject = q;
	}
	
	public void update(float dt){
		this.lastTimeMoved += dt;
		this.playingTime -= dt;
	}
	
	/**
	 * Shows that the qubject has been detected
	 */
	public void renderStatus(){
		if(!active && !footprint){
			return;
		}
		glColor4f(0f,0f,0f,1f);
		
		float x = qubject.getCoords().getX(), y=qubject.getCoords().getY();
		if(x <= 0 || y <= 0){
			return;
		}
		
		if (USE_SHADER){
			GL20.glUseProgram(shader[2]);
			GL20.glUniform2f(sourceAddress, (float)x+Qubject.SIZE/2, (float)y+Qubject.SIZE/2); 
		}
		
		double cos, sin, theta;
		glBegin(GL_TRIANGLE_STRIP);
		for (int i=0; i<=TESSELATION; i++){
			theta = i*2*Math.PI/(double)TESSELATION;
			cos = Math.cos(theta);
			sin = Math.sin(theta);
			if(footprint){
				glColor4f(0.9f, 0.1f, 0.1f, 0.5f);
			} else{
				if(USE_SHADER){
					GL20.glUniform4f(colorNearAddress, 0.1f, 0.5f, 0.1f, 0.8f);
				}
				glColor4f(0.4f, 0.9f, 0.4f, 0.3f);
			}
			glVertex3d(x+(OFFSET+Qubject.SIZE/2d)*cos, y+(OFFSET+Qubject.SIZE/2d)*sin, -2d);
			if(footprint){
				glColor4f(0.9f, 0.1f, 0.1f, 0.5f);
			} else{
				if(USE_SHADER){
					GL20.glUniform4f(colorNearAddress, 0.1f, 1.0f, 0.1f, 0.8f);
				}
				glColor4f(0.4f, 1.0f, 0.1f, 0.3f);
			}
			glVertex3d(x+(OFFSET+RADIUS+Qubject.SIZE/2d)*cos, y+(OFFSET+RADIUS+Qubject.SIZE/2d)*sin, -2d);
		}
		glEnd();
		
		if (playingTime >= 0){
			//Todo : shade
		}
		
		if(USE_SHADER){
			GL20.glUseProgram(0);
		}
	}
	
	/**
	 * Hide the area under the qubject so that it's easier to detect movement
	 */
	public void renderShadow(){
		double cos, sin, theta;
		float x = qubject.getCoords().getX(), y=qubject.getCoords().getY();
		if(x <= 0 || y <= 0){
			return;
		}
		
		glBegin(GL_TRIANGLE_FAN);
		if(footprint){
			glColor4f(1f,0.8f,0.8f,1f);
		} else{
			glColor4f(1f,1f,1f,0.8f);
		}
			glVertex3f(x,y,-5f);
			for (int i=0; i<=TESSELATION; i++){
				theta = i*2*Math.PI/(double)TESSELATION;
				cos = Math.cos(theta);
				sin = Math.sin(theta);
				glColor4f(0.8f,1f,0.8f,0.8f);
				glVertex3f((float)(x+(Qubject.SIZE/2f+OFFSET)*cos), (float)(y+(Qubject.SIZE/2f+OFFSET)*sin), -5f);
				glColor4f(0.8f,1f,0.8f,0.8f);
				glVertex3f((float)(x+(Qubject.SIZE/2f+OFFSET)*cos), (float)(y+(Qubject.SIZE/2f+OFFSET)*sin), -5f);
			}
		glEnd();
		
		//Inner white circle to help image detection
//		glBegin(GL_TRIANGLE_FAN);
//		glColor4f(1f,1f,1f,1f);
//		glVertex3f(x,y,-6f);
//		for (int i=0; i<=TESSELATION; i++){
//			theta = i*2*Math.PI/(double)TESSELATION;
//			cos = Math.cos(theta);
//			sin = Math.sin(theta);
//			glColor4f(1f,1f,1f,1f);
//			glVertex3f((float)(x+(Qubject.SIZE/2f)*cos), (float)(y+(Qubject.SIZE/2f)*sin), -6f);
//			glColor4f(1f,1f,1f,1f);
//			glVertex3f((float)(x+(Qubject.SIZE/2f)*cos), (float)(y+(Qubject.SIZE/2f)*sin), -6f);
//		}
//		glEnd();
	}
	
	public void setActive(boolean active){
		lastTimeMoved = 0f;
		this.footprint = false;
		this.active = active;
	}
	
	public void writeFootprintLabel(TrueTypeFont f){
		if (footprint && qubject.getCoords().getX() > 0){
			Fonts.renderMultiple(f, qubject.getCoords().getX(), qubject.getCoords().getY() + Qubject.SIZE, ((Qubject)qubject).getName(), Color.black);
		}
	}
	
	public void showFootPrint(boolean show){
		footprint = show;
	}
	
	public void loadShader(){
		if(!USE_SHADER)
			return;
		
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
