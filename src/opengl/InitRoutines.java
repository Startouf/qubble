package opengl;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glViewport;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import calibration.Calibrate;

public class InitRoutines
{
	public static void initView(int width, int height){
		glViewport(0, 0, width, height);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, width, height, 0, 300, -1); //Extrapolation 3D
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}

	public static void initDisplay(int width, int height){
		try{
			Display.setDisplayMode(new DisplayMode(width,height));
			Display.create();
		} catch (LWJGLException e){
			e.printStackTrace();
		}
	}
	
	public static Frame initDisplayOnSecondDevice(int width, int height){
		GraphicsDevice[] gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
		Frame frame = null;
				if (gd.length >= 2){
					try {
						frame = new Frame(gd[1].getDefaultConfiguration());
						frame.setUndecorated(true); 
						Canvas c = new Canvas(gd[1].getDefaultConfiguration());
						c.setSize(Calibrate.OpenGL_WIDTH, Calibrate.OpenGL_HEIGHT);
						c.setFocusable(true);
						c.requestFocus();
						c.setIgnoreRepaint(true);
						c.setVisible(true);
						frame.add(c, BorderLayout.CENTER);
						frame.pack();
						frame.setVisible(true);
						Display.setParent(c);
					} catch (LWJGLException e) {
						System.err.println("Error creating Canvas for 2nd monitor display");
						e.printStackTrace();
					}
				} else {
					System.err.println("Second monitor not detected !");
					System.err.println("Opening OpenGL output on first device");
				}
		initDisplay(width, height);	
		return frame;
	}
}
