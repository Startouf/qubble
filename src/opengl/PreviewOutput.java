package opengl;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JFrame;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class PreviewOutput implements Runnable{
	
	private final static AtomicReference<Dimension> newCanvasSize = new AtomicReference<Dimension>();
	private static boolean closeRequested = false;
	private final Canvas canvas;

	public PreviewOutput(Canvas canvas){
		this.canvas = canvas;
	}
	
	public void run(){
		try {
	        Display.setParent(canvas);
	        Display.setVSyncEnabled(true);
	        Display.create();


	        Dimension newDim = newCanvasSize.getAndSet(null);
	        GL11.glViewport(0, 0, newDim.width, newDim.height);
	        
	        while(!Display.isCloseRequested() && !closeRequested)
	        {
	           GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	           Display.update();
	        }

	        Display.destroy();
	        //frame.dispose();
	     } catch (LWJGLException e) {
	        e.printStackTrace();
	     }
	}
	
	public static void main(String[] args)
	  {
	     JFrame frame = new JFrame("Test");
	     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     frame.setLayout(new BorderLayout());
	     frame.setPreferredSize(new Dimension(1024, 786));
	     frame.setMinimumSize(new Dimension(800, 600));
	     frame.pack();
	     frame.setVisible(true);
	     final Canvas canvas = new Canvas();

	     canvas.addComponentListener(new ComponentAdapter() {
	        @Override
	        public void componentResized(ComponentEvent e)
	        { newCanvasSize.set(canvas.getSize()); }
	     });
	     
	     frame.addWindowFocusListener(new WindowAdapter() {
	        @Override
	        public void windowGainedFocus(WindowEvent e)
	        { canvas.requestFocusInWindow(); }
	     });
	     
	     frame.addWindowListener(new WindowAdapter() {
	        @Override
	        public void windowClosing(WindowEvent e)
	        { closeRequested = true; }
	     });
	     
	     frame.add(canvas, BorderLayout.CENTER);

	     Thread t = new Thread(new PreviewOutput(canvas));
	     t.start();
	     
	  }
}
