package routines;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL11;
public class GridRoutines
{
	public static void drawAxis(){
		glColor3f(1f,1f,1f);
		//x
		glBegin(GL_LINES);
		glVertex3f(Float.MIN_VALUE, 0, 0);
		glVertex3f(Float.MAX_VALUE, 0, 0);
		glEnd();
		//y
		glBegin(GL_LINES);
		glVertex3f(0,Float.MIN_VALUE,0);
		glVertex3f(0,Float.MAX_VALUE,0);
		glEnd();
		//z
		glBegin(GL_LINES);
		glVertex3f(0,0,Float.MIN_VALUE);
		glVertex3f(0,0,Float.MAX_VALUE);
		glEnd();
	}
	
	public static void drawBasis(){
		glColor3f(1f,1f,1f);
		//x
		glBegin(GL_LINES);
		glVertex3f(0, 0, 0);
		glVertex3f(100, 0, 0);
		glEnd();
		
		glBegin(GL_LINES);
		glVertex3f(0,0,0);
		glVertex3f(0,100,0);
		glEnd();
		
		glBegin(GL_LINES);
		glVertex3f(0,0,0);
		glVertex3f(0,0,100);
		glEnd();
	}
}
