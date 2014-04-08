package camera;

import static com.googlecode.javacv.cpp.opencv_highgui.cvSaveImage;

import imageTransform.MyImage;

import java.awt.image.BufferedImage;

import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
/**
 * Gestion de l'acquisition de la caméra dans un thread séparé
 * @author eric
 *
 */
public class Camera extends Thread{
	private MyImage tableImage;
	public void run(){
		// Ouverture de la caméra sur le port 0 
		final OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
    	// Démarage de la caméra
    	try{
    		grabber.start();
     	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	// Récupération et sauvegarde de l'image
    	while(true){
    		IplImage img;
			try {
				img = grabber.grab();
				if(img != null){
	    			//cvSaveImage("picture.jpg", img);
					tableImage = new MyImage(img.getBufferedImage());
					System.out.println("Nouvelle image !");
					
	    		}
			} catch (com.googlecode.javacv.FrameGrabber.Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}

	}
	
	public MyImage getImage(){
		return tableImage;
	}

}
