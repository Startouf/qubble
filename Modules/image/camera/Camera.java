package camera;

import static com.googlecode.javacv.cpp.opencv_highgui.cvSaveImage;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import sequencer.QubbleInterface;

import main.ImageDetectionInterface;
import main.TerminateThread;

import com.googlecode.javacv.FFmpegFrameGrabber;
import com.googlecode.javacv.FrameGrabber.Exception;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
/**
 * Gestion de l'acquisition de la caméra dans un thread séparé
 * @author eric
 *
 */
public class Camera implements Runnable, TerminateThread{
	private boolean run, cameraOK, pause;
	private ImageDetectionInterface controlImage;
	private FFmpegFrameGrabber grabber;
	private IplImage tableImage;
	private int i = 0;
	
	public Camera(ImageDetectionInterface controlImage){
		cameraOK = true;
		run = true;
		pause = false;
		this.controlImage = controlImage;
		grabber = null;
		// Tentative de démarage de la caméra
		try{
			// Ouverture de la caméra sur le port 0 
			grabber = new FFmpegFrameGrabber("/dev/video1");
			// A conserver : ancienne méthode
			//OpenCVFrameGrabber(0);
			grabber.setFormat("video4linux2");
			grabber.setImageHeight(720);
			grabber.setImageWidth(1280);
			grabber.start();
		}catch(ExceptionInInitializerError | Exception e){
			e.printStackTrace();
			System.out.println("Impossible de démarrer la caméra");
			cameraOK = false;
	   	}
	}
	

	public void run(){
    	// Récupération et sauvegarde de l'image
    	while(run){
    		// Stopper temporairement la capture
    		while(pause){
    			try {
    				Thread.sleep(200);
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
    		}
    		// Si la caméra fonctionne, récupérer une image
    		if(cameraOK){
    			try {
    				tableImage = grabber.grab();
    				if(tableImage != null){
    	    			//cvSaveImage("picture.jpg", tableImage);
    	    			//i++;
    	    			controlImage.setImage(tableImage.getBufferedImage());
    					//System.out.println("Nouvelle image !");
    					
    	    		}
    			} catch (com.googlecode.javacv.FrameGrabber.Exception e) {
    				e.printStackTrace();
    			}
    			// Pause de 200 ms avant la prochaine capture
    			try {
    				Thread.sleep(50);
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
    		// Sinon récupérer une image prédéfinie sur le disque dur
    		}else{
    			try {
					controlImage.setImage(ImageIO.read(new File("Modules/image/database/test/picture6.jpg")));
				} catch (IOException e) {
					e.printStackTrace();
				}
    					
    					// Image de la table réelle
    					//wind.readImage(new File("Modules/image/database/test/vlcsnap-2014-04-08-16h13m36s195.png"), true, 180, 21, 62);
    					//wind.readImage(new File("Modules/image/database/test/vlcsnap-2014-04-08-16h11m25s168.png"), true, 180, 21, 62);
    					//wind.readImage(new File("Modules/image/database/test/vlcsnap-2014-04-08-16h11m39s52.png"), true, 180, 21, 62);
    					//wind.readImage(new File("Modules/image/database/test/vlcsnap-2014-04-08-14h55m54s173.png"), true, 180, 21, 62);
    					//wind.readImage(new File("Modules/image/database/test/vlcsnap-2014-04-08-14h55m22s0.png"), true, 180, 21, 62);
    					
    					//wind.readImage(new File("Modules/image/database/test/vlcsnap-2014-04-08-14h55m22s0_petit.png"), true, 180, 21, 42);
    					//wind.readImage(new File("Modules/image/database/test/vlcsnap-2014-04-08-14h55m22s0_tres_petit.png"), true, 180, 21, 42);
    					
    					//qrWindow.readImage(new File("Modules/image/database/test/picture6.jpg"), true, 180, 21, 42);
    					//Useless : 
    						//wind.readImage(new File("Modules/image/database/test/vlcsnap-2014-04-08-16h13m17s0.png"), true, 180, 21, 62);
    		}
    			
			
    	}

	}
	
	/*public BufferedImage getImage(){
		return tableImage;
	}*/
	
	@Override
	public void terminate() {
		run = false;
	}
}
