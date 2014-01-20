package routines;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotated;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static routines.Squares.drawCubef;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import org.lwjgl.opengl.GL11;

public class Transformations
{
	/**
	 * WARNING : surround by {glPushMatrix()} and {[method], glPopMatrix()}
	 * @param pos desired object position
	 * @param angle desired object angle (ccw rotation)
	 * @param offset distance to the center of the object 
	 * (if center is at x then put x and not -x)
	 */
	public static void putObjectAt(float[] pos, long angle, float[] offset){
		glTranslatef(pos[0]+offset[0], pos[1]+offset[1], pos[2]+offset[2]);
		glRotated(angle, 0.0, 1.0, 0.0);
		glTranslatef(-offset[0], -offset[1], -offset[2]);
	}
	/*
	 * Note : I originally wanted to make a routine that also took a method reference as parameter
	 * (So that only one method call would be needed to put an object somewhere,
	 * without the need to add glPushMatrix, glPopMatrix)
	 * Turns out to be difficult and not in the spirit of JAVA
	 * Anyway ideas :
	 * --> Java reflexion
	 * --> Callback interface
	 * --> ...
	 */
	
}
