package routines;

import static org.lwjgl.opengl.GL11.*;

public final class Grids
{
	public static void drawInfifiteAxis(float offset){ //Not working ATM
		//x
		glBegin(GL_LINES);
		glVertex3f(Float.MIN_VALUE, offset, offset);
		glVertex3f(Float.MAX_VALUE, offset, offset);
		glEnd();
		//y
		glBegin(GL_LINE_STRIP);
		glVertex3f(offset,Float.MIN_VALUE,offset);
		glVertex3f(offset,Float.MAX_VALUE,offset);
		glEnd();
		//z
		glBegin(GL_LINES);
		glVertex3f(offset,offset,Float.MIN_VALUE);
		glVertex3f(offset,offset,Float.MAX_VALUE);
		glEnd();
	}
	
	public static void drawFiniteAxis(float length, float offset){ //Not Working ATM
		glColor3f(1f,1f,1f);
		//x
		glBegin(GL_LINES);
		glVertex3f(offset, offset, offset);
		glVertex3f(length, offset, offset);
		glEnd();
		
		glBegin(GL_LINES);
		glVertex3f(offset,offset,offset);
		glVertex3f(offset,length,offset);
		glEnd();
		
		glBegin(GL_LINES);
		glVertex3f(offset,offset,offset);
		glVertex3f(offset,offset,length);
		glEnd();
	}
	
	/**
	 * Draw a grid in the given space
	 * @param area the cube in which to draw the grid 
	 * {xmin, xmax, ymin ymax, zmin, zmax} min < max otherwise nothing
	 * @param spacing {x_spacing, y_spacing, z_spacing}
	 * setting a spacing to 0 cancels grid in this dimension but rest works
	 */
	public static void drawGrid3f(float[] area, float[] spacing){
		glBegin(GL_LINES);
		if(area == null || spacing == null)
			return;
		int imax=1, jmax =1, kmax =1;
		if(spacing[0]!=0f && area[0]<area[1]){
			imax = (int) Math.ceil((area[1]-area[0])/spacing[0]);
		}
		if(spacing[1]!=0f && area[2]<area[3]){
			jmax = (int) Math.ceil((area[1]-area[0])/spacing[0]);
		}
		if(spacing[2]!=0f && area[4]<area[5]){
			kmax = (int) Math.ceil((area[1]-area[0])/spacing[0]);
		}
		for (int i=0; i<imax; i++){
			for (int j=0; j<jmax; j++){
				for (int k=0; k<kmax; k++){
				//grid lines  // to Ox    on (xOy) at plane z
				glVertex3f(area[0],area[2]+j*spacing[1],area[4]+k*spacing[2]);
				glVertex3f(area[1],area[2]+j*spacing[1],area[4]+k*spacing[2]);
				
				// grid lines // to Oy
				glVertex3f(area[0]+i*spacing[0], area[2], area[4]+k*spacing[2]);
				glVertex3f(area[0]+i*spacing[0], area[3], area[4]+k*spacing[2]);
				
				// to Oz
				glVertex3f(area[0]+i*spacing[0], area[3]+j*spacing[1], area[4]);
				glVertex3f(area[0]+i*spacing[0], area[3]+j*spacing[1], area[5]);
				}
			}
		}
		glEnd();
	}
}
