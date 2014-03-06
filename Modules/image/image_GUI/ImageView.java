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
	public final int COLOR = 0, GREY = 1, BINARY = 2, CONNEXE = 3, QR_CODE = 4; // Choisi l'image affichée
	private final int nbrImage = 5;
	private int view;
	
	public ImageView(){
		super();
		this.setSize(new Dimension(800, 600));
		image = new MyImage[nbrImage];
		view = 0;
	}
	
	public void paintComponent(Graphics g){
		if(image[view] != null)
			g.drawImage(image[view], 0, 0, this.getWidth(), this.getHeight(), null);
		else
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
	
	public void setImage(MyImage image, int type){
		if(nbrImage > type && type >= 0)
			this.image[type] = image;
	}
	
	public MyImage getImage(int type){
		// Vérifier l'index tu tableau
		if(nbrImage > type && type >= 0)
			return image[type];
		else
			return null;
	}
	
	
	public void previous(){
		if(view > 0){
			view--;
		}else
			System.out.println("Pas d'image précédente.");
	}
	
	public void next(){
		if(view < (nbrImage-1) && image[view+1] != null){
			view++;
		}else
			System.out.println("Pas d'image suivante.");
	}
	
	private boolean hasImage(){
		for(int i = 0 ; i < nbrImage ; i++){
			if(image[i] == null)
				return false;
		}
		return true;
	}
}
