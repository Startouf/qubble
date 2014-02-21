package routines;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

public final class Grids
{
	public static int[] labelOffset = new int[] {7,7,7};
	public static float arrowLenght = 25f, arrowWidth = 5f;
	
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
	 * Draws a grid in the given space. The grid might be smaller floor(imax*spacing)
	 * @param area the cube in which to draw the grid 
	 * {xmin, xmax, ymin ymax, zmin, zmax} min < max otherwise nothing
	 * @param cellSpacing {x_spacing, y_spacing, z_spacing}
	 * setting a spacing to 0 cancels grid in this dimension but rest works
	 */
	public static void drawGrid3f(float[] area, float[] cellSpacing){
		glBegin(GL_LINES);
		if(area == null || cellSpacing == null)
			return;
		int imax=1, jmax =1, kmax =1;
		float offsetx =0, offsety =0, offsetz =0;
		if(cellSpacing[0]!=0f && area[0]<area[1]){
			imax = (int) Math.floor((area[1]-area[0])/cellSpacing[0]);
			offsetx = area[1]-area[0]-imax*cellSpacing[0];
		}
		if(cellSpacing[1]!=0f && area[2]<area[3]){
			jmax = (int) Math.floor((area[3]-area[2])/cellSpacing[1]);
			offsety = area[3]-area[2]-jmax*cellSpacing[1];
		}
		if(cellSpacing[2]!=0f && area[4]<area[5]){
			kmax = (int) Math.floor((area[5]-area[4])/cellSpacing[2]);
			offsetz = area[5]-area[4]-kmax*cellSpacing[2];
		}
		for (int i=0; i<=imax; i++){
			for (int j=0; j<=jmax; j++){
				for (int k=0; k<=kmax; k++){
				//grid lines  // to Ox    on (xOy) at plane z
				glVertex3f(area[0],area[2]+j*cellSpacing[1],area[4]+k*cellSpacing[2]);
				glVertex3f(area[1]-offsetx,area[2]+j*cellSpacing[1],area[4]+k*cellSpacing[2]);
				
				// grid lines // to Oy
				glVertex3f(area[0]+i*cellSpacing[0], area[2], area[4]+k*cellSpacing[2]);
				glVertex3f(area[0]+i*cellSpacing[0], area[3]-offsety, area[4]+k*cellSpacing[2]);
				
				// to Oz
				glVertex3f(area[0]+i*cellSpacing[0], area[3]+j*cellSpacing[1], area[4]);
				glVertex3f(area[0]+i*cellSpacing[0], area[3]+j*cellSpacing[1], area[5]-offsetz);
				}
			}
		}
		renderArrows2f(area);
		glEnd();
	}

	/**
	 * Draws a grid in the given space with labels for axis
	 * @param area the cube in which to draw the grid 
	 * {xmin, xmax, ymin ymax, zmin, zmax} min < max otherwise nothing
	 * @param cellSpacing {x_spacing, y_spacing, z_spacing}
	 * setting a spacing to 0 cancels grid in this dimension but rest works
	 * @param labelSpacing show label every [int] tick. Set 0 to never show labels {x,y,z}
	 * @param labelMultiplier multiply every label (pixel) by (shows an int) {x,y,z}
	 * @param axisName : a name for the axis {x, y, z}
	 * @param font : a loaded Unicode font 
	 */
	public static void drawGrid2fWithLabels(float[] area, float[] cellSpacing, int[] labelSpacing, float[] labelMultiplier, String[] axisName, TrueTypeFont font){
		drawGrid3f(area, cellSpacing);
		//TODO : replace by glPush Attrib /glPop
		GL11.glDisable(GL_DEPTH_TEST);
		GL11.glEnable(GL_TEXTURE_2D);
		renderTickLabels2f(area, cellSpacing, labelSpacing, labelMultiplier, axisName, font);
		renderMultipleLabel2f(area[1], area[2] + arrowWidth, font, axisName[0]);	//x axis name
		renderMultipleLabel2f(area[0] + arrowWidth, area[3], font, axisName[1]);	//y axis name
		GL11.glDisable(GL_TEXTURE_2D);
		GL11.glEnable(GL_DEPTH_TEST);
	}

	/**
	 * Render the tick-labels along the axis
	 * @param area
	 * @param cellSpacing
	 * @param labelSpacing the number of tick-labels to render (render one label every labelSpacing[x|y])
	 * @param labelMultiplier multiply every tick-label by labelMultiply[x|y]
	 * @param axisName 
	 * @param font a loaded TTF Font
	 */
	public static void renderTickLabels2f(float[] area, float[] cellSpacing, int[] labelSpacing, float[] labelMultiplier, String[] axisName, TrueTypeFont font){
		float fontOffsetX;
		float fontOffsetY;
		String label;
		if (cellSpacing[0] != 0 && labelSpacing[0] != 0){ 	//x-axis tick-labels
			int imax = (int) Math.floor((area[1]-area[0])/cellSpacing[0]);
			//Y-offset is the same for all the x labels
			fontOffsetY = font.getHeight();
			for (int i=0; i<=imax;i+=labelSpacing[0]){
				//Compute fontOffsetX
				label = Integer.toString((int)(i*cellSpacing[0]*labelMultiplier[0]));
				fontOffsetX = font.getWidth(label)/2;
				//render the labels
				renderMultipleLabel2f(area[0]+cellSpacing[0]*i-fontOffsetX, area[2]-fontOffsetY, font, (label));
			}
		}
		if (cellSpacing[1] != 0 && labelSpacing[1] != 0){ 	//y-axis tick-labels
			int jmax = (int) Math.floor((area[3]-area[2])/cellSpacing[1]);
			fontOffsetY = font.getHeight()/2;
			for (int j=0; j<=jmax;j+=labelSpacing[1]){
				//Compute fontOffsetX. Unlike the X axis, some more space must be added (for now arbitrary)
				//TODO maybe a more accurate computation could be used. For example using font.getHeight()*something
				label = Integer.toString((int)(j*cellSpacing[1]*labelMultiplier[1]));
				fontOffsetX = font.getWidth(label) + labelOffset[0];
				//render the labels
				renderMultipleLabel2f(area[0]-fontOffsetX, area[2]+j*cellSpacing[1]-fontOffsetY, font, (label));
			}
		}
	}

	/**
	 * Multiple label overload (Avoids changing GL Attributes each time)
	 * @param x
	 * @param y
	 * @param font
	 * @param text
	 */
	public static void renderMultipleLabel2f(float x, float y, TrueTypeFont font, String text){
		Fonts.renderMultiple(font, x, y, text, Color.white);
	}

	/**
	 * Single label Overload
	 * @param x
	 * @param y
	 * @param font
	 * @param text
	 */
	public static void renderLabel2f(float x, float y, TrueTypeFont font, String text){
		Fonts.renderMultiple(font, x, y, text, Color.white);
	}

	/**
	 * WARNING : CALL INSIDE A glBEGIN____glEnd
	 * The size of the arrow is arbitrary
	 * @param area
	 */
	public static void renderArrows2f(float[] area){
		glVertex3f(area[0], area[2], area[4]);
		glVertex3f(area[1] + arrowLenght, area[2], area[4]);
		
		glVertex3f(area[1]+ arrowLenght/2, area[2]-arrowWidth, area[4]);
		glVertex3f(area[1] + arrowLenght, area[2], area[4]);
		
		glVertex3f(area[1]+ arrowLenght/2, area[2]+arrowWidth, area[4]);
		glVertex3f(area[1] + arrowLenght, area[2], area[4]);
		
		glVertex3f(area[0], area[2], area[4]);
		glVertex3f(area[0], area[3] + arrowLenght, area[4]);
		
		glVertex3f(area[0]+arrowWidth, area[3]+ arrowLenght/2, area[4]);
		glVertex3f(area[0], area[3] + arrowLenght, area[4]);
		
		glVertex3f(area[0]-arrowWidth, area[3]+ arrowLenght/2, area[4]);
		glVertex3f(area[0], area[3] + arrowLenght, area[4]);
	}
}
