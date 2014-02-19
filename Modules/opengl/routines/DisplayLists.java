package routines;

import static org.lwjgl.opengl.GL11.*;
import static routines.Buffers.*;

import org.newdawn.slick.UnicodeFont;

public class DisplayLists
{
	/**
	 * Draw a list
	 * @param list
	 */
	public static void renderList(int list)
	{
	   glCallList(list);
	   glFlush(); // ?
	}
	
	/*****************************
	 * LOAD DISPLAY LISTS
	 *****************************/
	
	/**
	 * Loads a torus 
	 */
	public static int loadTorusDisplayList(){
	   int theTorus = glGenLists (1);
	   glNewList(theTorus, GL_COMPILE);
	   torus(8, 25);
	   glEndList();
	   return theTorus;
	}
	/**
	 * A Display List that draws a grid in the given space
	 * @param area the cube in which to draw the grid 
	 * {xmin, xmax, ymin ymax, zmin, zmax} min < max otherwise nothing
	 * @param spacing {x_spacing, y_spacing, z_spacing}
	 * setting a spacing to 0 cancels grid in this dimension but rest works
	 * @return DisplayList ID
	 */
	public static int loadGrid(float[] area, float[] spacing){
		int gridList = glGenLists(1);
		glNewList(gridList, GL_COMPILE);
		Grids.drawGrid3f(area, spacing);
		glEndList();
		return gridList;
	}
	
	/**
	 * Loads a DisplayList that draws a grid in the given space with labels for axis
	 * @param area the cube in which to draw the grid 
	 * {xmin, xmax, ymin ymax, zmin, zmax} min < max otherwise nothing
	 * @param spacing {x_spacing, y_spacing, z_spacing}
	 * setting a spacing to 0 cancels grid in this dimension but rest works
	 * @param labelSpacing show label every [int] tick. Set 0 to never show labels {x,y,z}
	 * @param labelMultiplier multiply every label (pixel) by (shows an int) {x,y,z}
	 * @param axisName : a name for the axis {x, y, z}
	 * @param font : a loaded Unicode font 
	 */
	public static int loadLabeledGrid(float[] area, float[] spacing, int[] labelSpacing, float[] labelMultiplier, String[] axisName, UnicodeFont font ){
		int gridList = glGenLists(1);
		glNewList(gridList, GL_COMPILE);
		Grids.drawGrid3fWithLabels(area, spacing, labelSpacing, labelMultiplier, axisName, font);
		glEndList();
		return gridList;
	}

	/*****************************
	 * SOME ROUTINES
	 *****************************/
	
	/**
	 * Draw a Torus
	 * @param numc
	 * @param numt
	 */
	static void torus(int numc, int numt)
	{
	   int i, j, k;
	   double s, t, x, y, z, twopi;

	   twopi = 2 * (double)Math.PI;
	   for (i = 0; i < numc; i++) {
	      glBegin(GL_QUAD_STRIP);
	      for (j = 0; j <= numt; j++) {
	         for (k = 1; k >= 0; k--) {
	            s = (i + k) % numc + 0.5;
	            t = j % numt;

	            x = (1+.1*Math.cos(s*twopi/numc))*Math.cos(t*twopi/numt);
	            y = (1+.1*Math.cos(s*twopi/numc))*Math.sin(t*twopi/numt);
	            z = .1 * Math.sin(s * twopi / numc);
	            glVertex3f((float)x, (float)y, (float)z);
	         }
	      }
	      glEnd();
	   }
	}
}
