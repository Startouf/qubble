package image_GUI;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Graphics;

import imageTransform.MyImage;

import javax.swing.JPanel;

/**
 * Affiche l'image dans la fenêtre
 * @author masseran
 *
 */
public class ImageView extends JPanel{
	
	private MyImage[] image;
	private final int COLOR = 0, GREY = 1, BINARY = 2, CONNEXE = 3; // Choisi l'image affichée
	private int view;
	
	public ImageView(){
		super();
		this.setSize(new Dimension(800, 600));
		image = new MyImage[4];
		view = 0;
	}
	
	public void paintComponent(Graphics g){
		if(image[view] != null)
			g.drawImage(image[view], 0, 0, this.getWidth(), this.getHeight(), null);
		else
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
	
	public void setImage(MyImage image){
		this.image[COLOR] = image;
	}
	
	public void setGreyImage(MyImage image){
		this.image[GREY] = image;
	}
	
	public void setBinaryImage(MyImage image){
		this.image[BINARY] = image;
	}
	
	public void setConnexeImage(MyImage image){
		this.image[CONNEXE] = image;
	}
	
	public MyImage getImage(){
		return image[COLOR];
	}
	
	public MyImage getGreyImage(){
		return image[GREY];
	}
	
	public MyImage getBinaryImage(){
		return image[BINARY];
	}
	
	public MyImage getConnexeImage(){
		return image[CONNEXE];
	}
	
	public void next(){
		view = (view+1)%4;
		// Parcourir les images suivantes
		while(image[view] == null && hasImage()){
			view = (view+1)%4;
		}
	}
	
	public void previous(){
		
	}
	
	private boolean hasImage(){
		if(image[COLOR] != null || image[GREY] != null || image[BINARY] != null || image[CONNEXE] != null )
			return true;
		else
			return false;
	}
}
