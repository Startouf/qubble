package opengl;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Dimension;
import java.awt.Font;

import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import routines.Fonts;
import routines.Grids;
import routines.Time;
import routines.VBO;
import sequencer.Qubble;

public class BaseRoutines
{
	public static int[] labelOffset = new int[] {7,7,7};
	public static float arrowLenght = 25f, arrowWidth = 5f;
	static float cursorPos = 0f;
	
	/*
	 * ################
	 * ## DRAW GRID
	 * #################
	 */
	
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
	public static int loadLabeledGrid(float[] area, float[] spacing, int[] labelSpacing, float[] labelMultiplier, String[] axisName, TrueTypeFont font ){
		int gridList = glGenLists(1);
		glNewList(gridList, GL_COMPILE);
		Grids.drawGrid2fWithLabels(area, spacing, labelSpacing, labelMultiplier, axisName, font);
		glEndList();
		return gridList;
	}
	
	public static void renderList(int list)
	{
	   glCallList(list);
	   glFlush(); // ?
	}
	
	/*
	 * ################
	 * ## CURSOR 
	 * #################
	 */
	

	public static void updateCursor(Float cursorPos, float[] cursorVertices, int cursorPosVBO, float dt){
		float newPos = uniformModulusTranslation(
				//TODO : test period
				cursorPos, Qubble.TABLE_OFFSET_X, Qubble.TABLE_LENGTH+Qubble.TABLE_OFFSET_X, 
				1f/Qubble.TEST_PERIOD,dt);
		cursorVertices[0] = newPos;
		cursorVertices[3] = newPos+Qubble.CURSOR_WIDTH;
		cursorVertices[6] = newPos+Qubble.CURSOR_WIDTH;
		cursorVertices[9] = newPos;
		VBO.overwrite(cursorPosVBO, cursorVertices);
	}
	
	public static float uniformModulusTranslation(Float pos, float min, float max, float freq, float dt){
		pos += freq*dt;
		return (float) ((pos)/1000f*(max-min))%(max-min)+min;
	}
	
	/*
	 * ################
	 * ## Time TOOLS
	 * #################
	 */
	
	/**
	 * Time in milliseconds
	 * @return time in milliseconds
	 */
	public static float getTimeMS(){
		return (float) (Sys.getTime()*1000/Sys.getTimerResolution());
	}
	
	public static float convertSysTimeToMS(long sysTime){
		return (float) (sysTime*1000/Sys.getTimerResolution());
	}

	/**
	 * Time between last given frame and current frame
	 * @param lastFrameTime
	 * @return delta in milliseconds
	 */
	public static float getDt(long lastFrameTime){
		return convertSysTimeToMS(Sys.getTime()-lastFrameTime);
	}
	
	/*
	 * ################
	 * ## OTHER TOOLS
	 * #################
	 */
	
	public static TrueTypeFont TimesNewsRomanTTF(){
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);;
		return(new TrueTypeFont(new Font("Times New Roman", Font.BOLD, 24), true));
	}
	
	public static void render(TrueTypeFont font, float x, float y, String text, Color color) {
		//TODO : Use glPushAttrib instead
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL_CULL_FACE);
		font.drawString(x, y, text, color);
		GL11.glEnable(GL_CULL_FACE);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

	public static void HighlightTile(Dimension dim) {
		//TODO : Something else
		glPushMatrix();
		GL11.glTranslatef(((float)dim.width)*Qubble.SPACING_X+Qubble.TABLE_OFFSET_X, 
				+((float)dim.height)*Qubble.SPACING_Y+Qubble.TABLE_OFFSET_Y, 0f);
		glBegin(GL_QUADS);
		glNormal3f(0f,0f,1f);
		glVertex2f(0f,0f);
		glVertex2f(Qubble.SPACING_X,0f);
		glVertex2f(Qubble.SPACING_X,Qubble.SPACING_Y);
		glVertex2f(0f,Qubble.SPACING_Y);
		glEnd();
		glPopMatrix();
	}
}
