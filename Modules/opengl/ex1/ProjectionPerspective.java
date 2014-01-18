package ex1;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;
import org.lwjgl.util.Point;

//The static imports all classes without the need to write class name each time (here GL11)
import static org.lwjgl.opengl.GL11.*;

/**
 * @author Cyril
 * Ex 1.1: 
     - utilisez une projection perspective au lieu d'une projection orthogonale
     - expliquez la différence entre les modes de projections
 *
 */
public class ProjectionPerspective
{
	private Point coords = new Point(300,300);
	
	public void start(){
		initDisplay();		
		initGL();
		
		while (!Display.isCloseRequested()){
			renderGL();
			Display.update(); 
			Display.sync(60); 
		}
		Display.destroy();
	}
	
	private void initGL(){
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		//glOrtho(0, 800, 0, 600, -1, 30);	//Orthonormal projection
		glFrustum(0, 800, 0, 600, 10, 30);	//Perspective projection
		//Note : l'axe z sort de l'écran, 
		//	et les deux derniers params. de glFrustum : distance de l'origine aux "plans de la pyramide coupée" 
		
//		( 2n/(r-l)    0       (r+l)/(r-l)     0      )
//		(   0       2n/(t-b)  (t+b)/(t-b)     0      ) Matrice de passage en perspective
//		(   0         0       (f+n)/(n-f)  2fn/(n-f) )
//		(   0         0           -1          0      )
		glMatrixMode(GL_MODELVIEW);
	}
	
	private void initDisplay(){
		try{
			Display.setDisplayMode(new DisplayMode(800,600));
			Display.create();
		} catch (LWJGLException e){
			e.printStackTrace();
		}
	}
	
	private void renderGL(){
		//Clear screen and depth buffer
		glClear(GL_COLOR_BUFFER_BIT | 
				GL_DEPTH_BUFFER_BIT);
		
		//Set color of quad (R,G,B,A)
		glColor3f(0.5f,0.5f,1.0f);
		
		//draw
		draw_square(coords.getX(), coords.getY(), 200);
	}
	
	private void draw_square(float x, float y, float s){
        glBegin(GL_QUADS);
        glNormal3f(0f,0f,1f); //Normal Vector
        glVertex3f(x, y, -20);
        glVertex3f(x, 2*y, -20);
        glVertex3f(2*x, 2*y, -20);
        glVertex3f(2*x, y, -20);
        glEnd();
    }
	
	public static void main(String[] args){
        ProjectionPerspective app = new ProjectionPerspective();
        app.start();
    }
}

