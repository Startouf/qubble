package opengl;

import org.lwjgl.Sys;

import qubject.QRInterface;
import qubject.Qubject;

import static org.lwjgl.opengl.GL11.*;

public class QubjectTracker {
	/**
	 * Ideas :
	 * 		-O Show a ring around the qubject to show its status
	 * 		-O Change ring color for a "shadow" 
	 * 			(If the qubject is not on the table but we want to show its latest position)
	 * 		-X Show a movement detection effect (ex : temporarily show an arrow or make contours brighter)
	 * 		-X Glowing effect on the ring
	 */
	private static final double OFFSET = 25d;
	private static final int TESSELATION = 25; 
	private final QRInterface qubject;
	private float lastTimeMoved = 0f;
	private boolean active = false;
	private boolean shadow = false;

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
		
		double x = qubject.getCoords().getX(), y=qubject.getCoords().getY();
		double cos, sin, theta;
		
		//Outer circle :
		glBegin(GL_TRIANGLE_FAN);
		glColor4f(0.1f, 0.5f, 0.1f, 0.8f);
		glVertex2d(x,y);
		
		if(shadow){
			glColor4f(0.9f, 0.1f, 0.1f, 1f);
		} else{
			glColor4f(0.1f, 1.0f, 0.1f, 0.8f);
		}
		for (int i=0; i<=TESSELATION; i++){
			theta = i*2*Math.PI/(double)TESSELATION;
			cos = Math.cos(i);
			sin = Math.sin(i);
			glVertex2d(x+OFFSET+Qubject.SIZE/2d*cos, y+OFFSET+Qubject.SIZE/2d*sin);
		}
		glEnd();
		
		//hide the qubject :
		glBegin(GL_TRIANGLE_FAN);
		glColor4f(0f, 0f, 0f, 1f);
		glVertex2d(x,y);
		for (int i=0; i<=TESSELATION; i++){
			theta = i*2*Math.PI/(double)TESSELATION;
			cos = Math.cos(i);
			sin = Math.sin(i);
			glVertex2d(x+Qubject.SIZE/2d*cos, y+Qubject.SIZE/2d*sin);
		}
	}
	
	public void setActive(boolean active){
		lastTimeMoved = 0f;
		this.active = active;
	}
}
