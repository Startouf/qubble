package imageTransform;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Gère les images
 * Permet de créer une image en niveau de gris ou une image binaire
 * @author masseran
 *
 */
public class MyImage extends BufferedImage{
	
	// Niveau de sensibilité pour l'image binaire
	public static int BINARY_LEVEL = 180; 

	/*
	 * Reprise des constructeurs de BufferedImage
	 */
	public MyImage(int width, int height, int imageType) {
		super(width, height, imageType);
	}
	
	public MyImage(ColorModel cm, WritableRaster raster,
			boolean isRasterPremultiplied, Hashtable<?, ?> properties) {
		super(cm, raster, isRasterPremultiplied, properties);
		
	}	

	public MyImage(int width, int height, int imageType, IndexColorModel cm) {
		super(width, height, imageType, cm);
	}
	
	public MyImage(BufferedImage img){
		super(img.getWidth(), img.getHeight(), img.getType());
		Graphics2D g = this.createGraphics();
		g.setComposite(AlphaComposite.Src);      
	    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null);
		g.dispose();
	}

	
	public MyImage redimensionner(BufferedImage img, int tailleX, int tailleY) {
			MyImage resizedImage = new MyImage(tailleX,tailleY,BufferedImage.TYPE_INT_ARGB);
	       Graphics2D g = resizedImage.createGraphics();
	       g.setComposite(AlphaComposite.Src);      
	       g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	       g.drawImage(img, 0, 0, tailleX, tailleY, null);
	       g.dispose();
	       return resizedImage;
	}
	
	/**
	 * Transforme une image couleur en image niveau de gris
	 * @return
	 */
	public MyImage getGreyMyImage(){
		int greyValue = 0, red = 0, green = 0, blue = 0;
		MyImage greyImage = new MyImage(this.getWidth(),this.getHeight(),BufferedImage.TYPE_INT_ARGB);
		for(int i = 0 ; i < this.getWidth() ; i++){
			for(int j = 0 ; j < this.getHeight() ; j++){
				// Calcul du niveau de gris
				red = this.getRGB(i, j) >> 16 & 0xff;
				green = this.getRGB(i, j) >> 8 & 0xff;
				blue = this.getRGB(i, j) & 0xff;
				greyValue = (int) (0.299 * red +  0.587 * green + 0.114 * blue);
				greyImage.setRGB(i, j, (new Color(greyValue, greyValue, greyValue).getRGB()));
			}
		}
		return greyImage;
	}
	
	/**
	 * Transforme une image en niveau de gris en une image binaire
	 * @return
	 */
	public MyImage getBinaryMyImage(){
		MyImage binaryImage = new MyImage(this.getWidth(),this.getHeight(),BufferedImage.TYPE_INT_ARGB);
		int binaryLevel = this.getBinaryLevel();
		for(int i = 0 ; i < this.getWidth() ; i++){
			for(int j = 0 ; j < this.getHeight() ; j++){
				// Calcul du niveau de gris
				if((this.getRGB(i, j) & 0x000000ff) > binaryLevel)
					binaryImage.setRGB(i, j, (new Color(255, 255, 255).getRGB()));
				else
					binaryImage.setRGB(i, j, (new Color(0, 0, 0).getRGB()));
			}
		}
		return binaryImage;
	}
	
	/** A IMPLEMENTER
	 * Calcule le meilleur niveau de binarisation pour l'image afin d'avoir les bonnes composantes en noires
	 * @param greyImage
	 * @return
	 */
	private int getBinaryLevel(){
		return BINARY_LEVEL;
	}
	
}
