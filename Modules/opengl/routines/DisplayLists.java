package routines;

import static org.lwjgl.opengl.GL11.*;
import static routines.Buffers.*;

public class DisplayLists
{
	static int theTorus = createVBOID();
	
	public static void loadTorusDisplayList(){
	   theTorus = glGenLists (1);
	   glNewList(theTorus, GL_COMPILE);
	   torus(8, 25);
	   glEndList();

	   glShadeModel(GL_FLAT);
	   glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
	}

	public static void displayList(int list)
	{
	   glClear(GL_COLOR_BUFFER_BIT);
	   glColor3f (1.0f, 1.0f, 1.0f);
	   glCallList(list);
	   glFlush();
	}
	
	/* Draw a torus */
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
