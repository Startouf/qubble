package routines;

import static org.lwjgl.opengl.GL11.*;
import static routines.someMath.*;

import org.newdawn.slick.opengl.Texture;

public final class Squares
{
	public static void squareFromQuad(float x, float y, float s){
        glBegin(GL_QUADS);
        glNormal3f(0,0,1);
        glVertex2f(x, y);
        glVertex2f(x+s, y);
        glVertex2f(x+s, y+s);
        glVertex2f(x, y+s);
        glEnd();
    }
	
	public static void squareFromFan(float x, float y, float s){
		glBegin(GL_TRIANGLE_FAN);
        glNormal3f(0,0,1);
		glVertex2f(x+(float)s/2,y+(float)s/2); //center of quad
		glVertex2f(x+s, y);
		glVertex2f(x+s,y+s);
		glVertex2f(x,y+s);
		glVertex2f(x,y);
		glVertex2f(x+s,y);
		glEnd();
	}
	
	public static void squareFromStrip(float x, float y, float s){
		glBegin(GL_TRIANGLE_STRIP);
		glNormal3f(0,0,1);
		glVertex2f(x,y);
		glVertex2f(x+s,y);
		glVertex2f(x,y+s);
		glVertex2f(x+s,y+s);
		glEnd();
	}
	
	public static void drawCubef(float x, float y, float z, float s){
		//Four faces with QUAD_Strip (front, top, back, bottom)
		//Normals must be sent before the vertex that finish a face
		float[][] v = {
				{x,y,-z}, {x+s,y,-z}, {x+s,y+s,-z}, {x,y+s,-z},
				{x,y,-z-s}, {x+s,y,-z-s}, {x+s,y+s,-z-s}, {x,y+s,-z-s}
				};
		/*
		 * 3________2	7_______6
		 * |		|	|		|
		 * |		|	|		|
		 * |		|	|		|
		 * |________|	|_______|
		 * 0		1	4		5
		 */
		
		glColor3f(0,0,1f); //front face :blue 
		
		square3D(v[0],v[1],v[2],v[3]);
		
		glColor3f(1f,0,0); //top face :red
		square3D(v[3], v[2], v[6], v[7]);
		
		glColor3f(0,1f,0); //back face : green
		square3D(v[5],v[4],v[7],v[6]);
		
		glColor3f(1f,1f,0); //bottom face : yellow
		square3D(v[1],v[0], v[4], v[5]);

		glColor3f(1f,0,1f); //left face magenta
		square3D(v[0], v[3], v[7], v[4]);

		glColor3f(0,1f,1f); //right face Cyan
		square3D(v[1], v[5], v[6], v[2]);
		
	}
	
	public static void square3D(float[] v1, float[]v2, float[] v3, float[] v4){
		glBegin(GL_QUADS);
		
		glVertex3f(v1[0], v1[1], v1[2]);
		glVertex3f(v2[0], v2[1], v2[2]);
		glVertex3f(v3[0], v3[1], v3[2]);
		glVertex3f(v4[0], v4[1], v4[2]);
		glEnd();
	}
	
	public static void drawCubeWithNormals(float x, float y, float z, float s){
		//Four faces with QUAD_Strip (front, top, back, bottom)
		//Normals must be sent before the vertex that finish a face
		float[][] v = {
				{x,y,-z}, {x+s,y,-z}, {x+s,y+s,-z}, {x,y+s,-z},
				{x,y,-z-s}, {x+s,y,-z-s}, {x+s,y+s,-z-s}, {x,y+s,-z-s}
				};
		/*	
		 * 							  7 ___________ 6
		 * 							   /|		  /|
		 * 3________2	7_______6    3/_|________/ |
		 * |		|	|		|    |  |		|2 |
		 * |front	|	|back	|	 | 4|_______|__| 5
		 * |face	|	|face	|	 | /		| /
		 * |________|	|_______|    |/_________|/
		 * 0		1	4		5	0			1
		 */
		
		//!!!! Must define sides counterclockwise for normals !
		
		glColor3f(0,0,1f); //front face :blue 
		square3DWithNormal(v[0],v[1],v[2],v[3], getNormal(v[0], v[1], v[3]));
		
		glColor3f(1f,0,0); //top face :red
		square3DWithNormal(v[3], v[2], v[6], v[7], getNormal(v[3], v[2], v[7]));
		
		glColor3f(0,1f,0); //back face : green
		square3DWithNormal(v[5],v[4],v[7],v[6], getNormal(v[5], v[4], v[6]));
		
		glColor3f(1f,1f,0); //bottom face : yellow
		square3DWithNormal(v[1],v[0], v[4], v[5], getNormal(v[1], v[0], v[5]));

		glColor3f(1f,0,1f); //left face magenta
		square3DWithNormal(v[0], v[3], v[7], v[4], getNormal(v[0], v[3], v[4]));

		glColor3f(0,1f,1f); //right face Cyan
		square3DWithNormal(v[1], v[5], v[6], v[2], getNormal(v[1], v[6], v[2]));
	}
	
	public static void drawCubeWithFluctuatingNormals(float x, float y, float z, float s, float freq, float delta){
		float[][] v = {
				{x,y,-z}, {x+s,y,-z}, {x+s,y+s,-z}, {x,y+s,-z},
				{x,y,-z-s}, {x+s,y,-z-s}, {x+s,y+s,-z-s}, {x,y+s,-z-s}
				};		
		
		glColor3f(0,0,1f); //front face :blue 
		square3DWithNormal(v[0],v[1],v[2],v[3], getFluctuatingNormal(v[0], v[1], v[3],freq, delta));
		
		glColor3f(1f,0,0); //top face :red
		square3DWithNormal(v[3], v[2], v[6], v[7], getFluctuatingNormal(v[3], v[2], v[7],freq, delta));
		
		glColor3f(0,1f,0); //back face : green
		square3DWithNormal(v[5],v[4],v[7],v[6], getFluctuatingNormal(v[5], v[4], v[6],freq, delta));
		
		glColor3f(1f,1f,0); //bottom face : yellow
		square3DWithNormal(v[1],v[0], v[4], v[5], getFluctuatingNormal(v[1], v[0], v[5],freq, delta));

		glColor3f(1f,0,1f); //left face magenta
		square3DWithNormal(v[0], v[3], v[7], v[4], getFluctuatingNormal(v[0], v[3], v[4],freq, delta));

		glColor3f(0,1f,1f); //right face Cyan
		square3DWithNormal(v[1], v[5], v[6], v[2], getFluctuatingNormal(v[1], v[6], v[2],freq, delta));
	}
	
	public static void drawCubeWithTexture(float x, float y, float z, float s, Texture t){
		//Four faces with QUAD_Strip (front, top, back, bottom)
		//Normals must be sent before the vertex that finish a face
		float[][] v = {
				{x,y,-z}, {x+s,y,-z}, {x+s,y+s,-z}, {x,y+s,-z},
				{x,y,-z-s}, {x+s,y,-z-s}, {x+s,y+s,-z-s}, {x,y+s,-z-s}
				};
		
		t.bind();
		
		square3DWithTexture(v[0],v[1],v[2],v[3], getNormal(v[0], v[1], v[3]));
		square3DWithTexture(v[3], v[2], v[6], v[7], getNormal(v[3], v[2], v[7]));
		square3DWithTexture(v[5],v[4],v[7],v[6], getNormal(v[5], v[4], v[6]));
		square3DWithTexture(v[1],v[0], v[4], v[5], getNormal(v[1], v[0], v[5]));
		square3DWithTexture(v[0], v[3], v[7], v[4], getNormal(v[0], v[3], v[4]));
		square3DWithTexture(v[1], v[5], v[6], v[2], getNormal(v[1], v[6], v[2]));
	}
	
	public static void square3DWithTexture(float[] v1, float[]v2, float[] v3, float[] v4){
		square3DWithTexture(v1,v2,v3,v4,getNormal(v1, v2, v4));
	}
	
	public static void square3DWithTexture(float[] v1, float[]v2, float[] v3, float[] v4, float[] n){
		glBegin(GL_QUADS);
		glNormal3f(n[0], n[1], n[2]);
		glTexCoord2f(0,1);
		glVertex3f(v1[0], v1[1], v1[2]);
		glTexCoord2f(1,1);
		glVertex3f(v2[0], v2[1], v2[2]);
		glTexCoord2f(1,0);
		glVertex3f(v3[0], v3[1], v3[2]);
		glTexCoord2f(0,0);
		glVertex3f(v4[0], v4[1], v4[2]);
		glEnd();
	}
	
	public static void square3DWithNormal(float[] v1, float[]v2, float[] v3, float[] v4, float[] n){
		glBegin(GL_QUADS);
		glNormal3f(n[0], n[1], n[2]);
		glVertex3f(v1[0], v1[1], v1[2]);
		glVertex3f(v2[0], v2[1], v2[2]);
		glVertex3f(v3[0], v3[1], v3[2]);
		glVertex3f(v4[0], v4[1], v4[2]);
		glEnd();
	}

	/**
	 * Rectangle of coords (x1,y1), (x1,y2), (x2,y2), (x2, y1)
	 * @param x1
	 * @param x2
	 * @param y1
	 * @param y2
	 */
	public static void rectangleHorizontal2f(float x1, float x2, float y1, float y2){
		glBegin(GL_QUADS);
		glNormal3f(0f,0f,1f);
		glVertex2f(x1, y1);
		glVertex2f(x2, y1);
		glVertex2f(x2, y2);
		glVertex2f(x1, y2);
		glEnd();
	}
}
