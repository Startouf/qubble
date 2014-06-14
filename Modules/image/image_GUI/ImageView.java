package image_GUI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import imageObject.ConnexeComponent;
import imageTransform.TabImage;

import javax.swing.JPanel;

/**
 * Affiche l'image dans la fenêtre
 * @author masseran
 *
 */
public class ImageView extends JPanel{
	// Liste des images :: 0 : caméra / 1 : Dernière capture de détection de QR / ...
	private ArrayList<BufferedImage> slide;
	// Indice de l'image en cours de visualisation
	private int view;
	private int nbrImage;
	
	public ImageView(){
		super();
		this.setSize(new Dimension(800, 600));
		slide = new ArrayList<BufferedImage>();
		view = 0;
		nbrImage = slide.size();
	}
	
	public void paintComponent(Graphics g){
		if(slide.size() > view)
			g.drawImage(slide.get(view), 0, 0, this.getWidth(), this.getHeight(), null);
		else{
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			g.drawString("No Image", 350, 250);
		}
			
	}
	
	/**
	 * 
	 * @param image
	 * @param type
	 * @return la position de l'image dans la liste
	 */
	public int addImage(BufferedImage image){
		this.slide.add(image);
		nbrImage = slide.size();
		return nbrImage-1;
	}
	
	public void setCameraImage(BufferedImage camera){
		if(slide.size()>0){
			slide.set(0, camera);
			// Lors de la première fois
		}else{
			slide.add(camera);
			nbrImage = slide.size();
		}
	}
	
	public void setLastDetectionImage(BufferedImage lastDetection){
		if(slide.size()>1){
			slide.set(1, lastDetection);
			// Lors de la première fois
		}else{
			slide.add(lastDetection);
			nbrImage = slide.size();
		}
	}
	
	/**
	 * Affiche un récapitulatif du calcule de la signature
	 * @param cc
	 * @param color
	 */
	public void addSignatureView(ConnexeComponent cc, Color color){
		BufferedImage img = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		g.setColor(color);
		
		//Afficher la composante connexe sous forme de courbe : distance en fonction de l'angle (pas de 2)
//		for(int i = 0; i<180; i += 2){
//			g.drawLine(i, (int)mySquare[i], i, (int)mySquare[i]);
//		}
		// Afficher le coefficient de réussite 
	}
	
	public BufferedImage getImage(int type){
		// Vérifier l'index du tableau
		if(slide.size() >= type)
			return slide.get(type);
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
		if(view < (nbrImage-1)){
			view++;
		}else
			System.out.println("Pas d'image suivante.");
	}
	
	/**
	 * Supprime les images mémorisées qui détaillent la détection
	 */
	public void resetList(){
		while(slide.size() > 2){
			slide.remove(2);
		}
		nbrImage = slide.size();
	}
}
