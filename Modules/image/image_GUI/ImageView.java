package image_GUI;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import imageTransform.MyImage;

import javax.swing.JPanel;

/**
 * Affiche l'image dans la fenêtre
 * @author masseran
 *
 */
public class ImageView extends JPanel{
	
	private ArrayList<BufferedImage> slide;
	private int view;
	private int nbrImage;
	
	public ImageView(){
		super();
		this.setSize(new Dimension(800, 600));
		slide = new ArrayList<BufferedImage>();
		view = 0;
	}
	
	public void paintComponent(Graphics g){
		if(slide.size() > view)
			g.drawImage(slide.get(view), 0, 0, this.getWidth(), this.getHeight(), null);
		else
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
	
	/**
	 * 
	 * @param image
	 * @param type
	 * @return la position de l'image dans la liste
	 */
	public int setImage(BufferedImage image){
		this.slide.add(image);
		return nbrImage++;
	}
	
	public MyImage getImage(int type){
		// Vérifier l'index du tableau
		if(slide.size() >= type)
			return (MyImage)slide.get(type);
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
		if(view < (nbrImage-1) && slide.get(view+1) != null){
			view++;
		}else
			System.out.println("Pas d'image suivante.");
	}
	
	private boolean hasImage(){
		for(int i = 0 ; i < nbrImage ; i++){
			if(slide.get(i) == null)
				return false;
		}
		return true;
	}
	
	public void resetList(boolean keepFirst){
		BufferedImage img = null;
		if(!keepFirst) {
			 img = slide.get(0);
		} 
		slide.clear();
		if(!keepFirst){
			slide.add(img);
			view = 0;
			nbrImage = 1;
		}else{
			view = -1;
			nbrImage = 0;
		}
	}
}
