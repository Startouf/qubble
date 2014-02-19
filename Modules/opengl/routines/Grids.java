package routines;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.UnicodeFont;

public final class Grids
{
	public static int[] labelOffset = new int[] {20,20,20};
	
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
	 * Draws a grid in the given space
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
		float offsetx =0, offsety =0, offsetz =0;
		if(spacing[0]!=0f && area[0]<area[1]){
			imax = (int) Math.ceil((area[1]-area[0])/spacing[0]);
			offsetx = area[1]-area[0]-imax*spacing[0];
		}
		if(spacing[1]!=0f && area[2]<area[3]){
			jmax = (int) Math.ceil((area[3]-area[2])/spacing[1]);
			offsety = area[3]-area[2]-jmax*spacing[1];
		}
		if(spacing[2]!=0f && area[4]<area[5]){
			kmax = (int) Math.ceil((area[5]-area[4])/spacing[2]);
			offsetz = area[5]-area[4]-kmax*spacing[2];
		}
		for (int i=0; i<=imax; i++){
			for (int j=0; j<=jmax; j++){
				for (int k=0; k<=kmax; k++){
				//grid lines  // to Ox    on (xOy) at plane z
				glVertex3f(area[0],area[2]+j*spacing[1],area[4]+k*spacing[2]);
				glVertex3f(area[1]-offsetx,area[2]+j*spacing[1],area[4]+k*spacing[2]);
				
				// grid lines // to Oy
				glVertex3f(area[0]+i*spacing[0], area[2], area[4]+k*spacing[2]);
				glVertex3f(area[0]+i*spacing[0], area[3]-offsety, area[4]+k*spacing[2]);
				
				// to Oz
				glVertex3f(area[0]+i*spacing[0], area[3]+j*spacing[1], area[4]);
				glVertex3f(area[0]+i*spacing[0], area[3]+j*spacing[1], area[5]-offsetz);
				}
			}
		}
		glEnd();
	}

	/**
	 * Draws a grid in the given space with labels for axis
	 * @param area the cube in which to draw the grid 
	 * {xmin, xmax, ymin ymax, zmin, zmax} min < max otherwise nothing
	 * @param spacing {x_spacing, y_spacing, z_spacing}
	 * setting a spacing to 0 cancels grid in this dimension but rest works
	 * @param labelSpacing show label every [int] tick. Set 0 to never show labels {x,y,z}
	 * @param labelMultiplier multiply every label (pixel) by (shows an int) {x,y,z}
	 * @param axisName : a name for the axis {x, y, z}
	 * @param font : a loaded Unicode font 
	 */
	public static void drawGrid3fWithLabels(float[] area, float[] spacing, int[] labelSpacing, float[] labelMultiplier, String[] axisName, UnicodeFont font){
		drawGrid3f(area, spacing);

		GL11.glDisable(GL_DEPTH_TEST);
		renderLabels3f(area, spacing, labelSpacing, labelMultiplier, axisName, font);
		renderLabel2f(area[1]+labelOffset[0], area[2], font, axisName[0]);	//x axis name
		renderLabel2f(area[0], area[3]+labelOffset[1], font, axisName[1]);	//y axis name
		//renderLabel(area[1]+labelOffset[0], area[1], font, axisName[2]);	//z axis name not supported by slick
		GL11.glEnable(GL_DEPTH_TEST);
		GL11.glDisable(GL_TEXTURE_2D);
	}

	public static void renderLabels3f(float[] area, float[] spacing, int[] labelSpacing, float[] labelMultiplier, String[] axisName, UnicodeFont font){
		if (spacing[0] != 0 && labelSpacing[0] != 0){ 	//x-axis tick-labels
			int imax = (int) Math.floor(spacing[0]/(float)labelSpacing[0]);
			for (int i=0; i<imax;i++){
				renderMultipleLabel2f(area[0]+spacing[0]*i-labelOffset[0], area[2]-labelOffset[1], font, (Float.toString(i*spacing[0]*labelMultiplier[0])));
			}
		}
		if (spacing[1] != 0 && labelSpacing[1] != 0){ 	//y-axis tick-labels
			int jmax = (int) Math.floor(spacing[1]/(float)labelSpacing[1]);
			for (int j=0; j<jmax;j++){
				renderMultipleLabel2f(area[0]-labelOffset[0], area[2]+spacing[2]*j-labelOffset[1], font, (Float.toString(j*spacing[1]*labelMultiplier[1])));
			}
		}
		//Slick Fonts do not handle z-axis rendering
//		if (spacing[2] != 0 && labelSpacing[2] != 0){ 
//			int imax = (int) Math.floor(spacing[2]/(float)labelSpacing[2]);
//			for (int i=0; i<imax;i++){
//				renderLabel2f(area[0]+spacing[0]*i, area[2], font, (Float.toString(i*spacing[0]*labelMultiplier[0])));
//			}
//		}
	}

	/**
	 * Multiple label overload (Avoids changing GL Attributes each time)
	 * @param x
	 * @param y
	 * @param font
	 * @param text
	 */
	public static void renderMultipleLabel2f(float x, float y, UnicodeFont font, String text){
		Fonts.renderMultiple(font, x, y, text, Color.white);
	}

	/**
	 * Single label Overload
	 * @param x
	 * @param y
	 * @param font
	 * @param text
	 */
	public static void renderLabel2f(float x, float y, UnicodeFont font, String text){
		Fonts.render(font, x, y, text, Color.white);
	}
}
