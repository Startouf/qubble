package imageTransform;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
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
	private int[] histogramme;

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
		//g.setComposite(AlphaComposite.Src);      
	    //g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
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
				int pix = this.getRGB(i, j);
				red   = pix >> 16 & 0xff;
				green = pix >> 8 & 0xff;
				blue  = pix & 0xff;
				//greyValue = (int) (0.114 * red +  0.299 * green + 0.587 * blue);
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
	
	/**
	 * Transforme une image en niveau de gris en une image binaire en décomposant l'image en sous image
	 * @return
	 */
	public MyImage getBinaryMyImageByBlock(){
		// Paramètre des blocs de l'image
		int blockX = 5, blockY = 4;
		int sizeBlockX = this.getWidth()/blockX, sizeBlockY = this.getHeight()/blockY;
		
		int[][] binaryTable = new int[blockX][blockY];
		MyImage binaryImage = new MyImage(this.getWidth(),this.getHeight(),BufferedImage.TYPE_INT_ARGB);
		
		// Calcul des seuils pour chaque bloc
		for(int X = 0 ; X < blockX ; X++){
			for(int Y = 0 ; Y < blockY ; Y++){
				MyImage sub = new MyImage(this.getSubimage(X*sizeBlockX, Y*sizeBlockY, sizeBlockX-1, sizeBlockY-1));
				sub.getHistogramme();
				binaryTable[X][Y] = sub.otsuTreshold();
			}
		}
		// Binarisation
		// Variable pour pondérer en fonction de la distance
		int a = 0, b = 0, c = 0, d = 0;
		// Mémorisation du coefficient de binarisation
		int Ta = 0, Tb = 0, Tc = 0, Td =0;
		int offsetX = 0, offsetY = 0;
		int binaryLevel = 0;
		for(int X = 0 ; X < blockX ; X++){
			for(int Y = 0 ; Y < blockY ; Y++){
				for(int i = X*sizeBlockX ; i < (X+1)*sizeBlockX ; i++){
					for(int j = Y*sizeBlockY ; j < (Y+1)*sizeBlockY ; j++){
						// Choix des coefficients avec gestion de l'effet de bord
						if(X == blockX-1){
							offsetX = -1;
						}else{
							offsetX = 1;
						}
						if(Y == 0){
							offsetY = 1;
						}else{
							offsetY= -1;
						}
						// grande distance selon y
						a = (int) Math.abs((Y+offsetY+0.5)*sizeBlockY-j);
						b = (int) Math.abs((Y+0.5)*sizeBlockY-j);
						c = (int) Math.abs((X+0.5)*sizeBlockX-i);
						d = (int) Math.abs((X+offsetX+0.5)*sizeBlockX-i);
						Ta = binaryTable[X][Y+offsetY];
						Tb = binaryTable[X+offsetX][Y+offsetY];
						Tc = binaryTable[X][Y];
						Td = binaryTable[X+offsetX][Y];
						
						// Calcul du niveau de gris
						binaryLevel = (int) ((a*d*Ta + b*c*Tb+ c*a*Td +d*b*Ta)/(float)((a + b)*(c +d)));
						//System.out.println("X : "+i+"//"+"Y : "+j+"//"+binaryLevel);
						if((this.getRGB(i, j) & 0x000000ff) > binaryLevel)
							binaryImage.setRGB(i, j, (new Color(255, 255, 255).getRGB()));
						else
							binaryImage.setRGB(i, j, (new Color(0, 0, 0).getRGB()));
					}
				}
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
		int i, l, totl, g=0;
        double toth, h;
        for (i = 1; i < 256; i++) {
            if (histogramme[i] > 0){
                g = i + 1;
                break;
            }
        }
        while (true){
            l = 0;
            totl = 0;
            for (i = 0; i < g; i++) {
                 totl = totl + histogramme[i];
                 l = l + (histogramme[i] * i);
            }
            h = 0;
            toth = 0;
            for (i = g + 1; i < 256; i++){
                toth += histogramme[i];
                h += ((double)histogramme[i]*i);
            }
            if (totl > 0 && toth > 0){
                l /= totl;
                h /= toth;
                if (g == (int) Math.round((l + h) / 2.0))
                    break;
            }
            g++;
            if (g > 254)
                return -1;
        }
        BINARY_LEVEL = g;
        return g;
	}
	
	// Get binary treshold using Otsu's method
	private int otsuTreshold() {
	 
	    int[] histogram = histogramme;
	    int total = this.getHeight() * this.getWidth();
	 
	    float sum = 0;
	    for(int i=0; i<256; i++) sum += i * histogram[i];
	 
	    float sumB = 0;
	    int wB = 0;
	    int wF = 0;
	 
	    float varMax = 0;
	    int threshold = 0;
	 
	    for(int i=0 ; i<256 ; i++) {
	        wB += histogram[i];
	        if(wB == 0) continue;
	        wF = total - wB;
	 
	        if(wF == 0) break;
	 
	        sumB += (float) (i * histogram[i]);
	        float mB = sumB / wB;
	        float mF = (sum - sumB) / wF;
	 
	        float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);
	 
	        if(varBetween > varMax) {
	            varMax = varBetween;
	            threshold = i;
	        }
	    }
	 
	    return threshold;
	 
	}
	
	/**
	 * Construction de l'histogramme
	 */
	public void getHistogramme(){
		histogramme = new int[256];
		for(int i = 0 ; i < this.getWidth() ; i++){
			for(int j = 0 ; j < this.getHeight() ; j++){
				histogramme[(this.getRGB(i, j) & 0x000000ff)]++;
			}
		}
	}
	
	public BufferedImage getHistogramImage(){
		BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
		if(true){
			
			int max = 0;
			for(int j = 0 ; j < 256 ; j++){
				max = Math.max(max, histogramme[j]);
			}
			int columnWidth = this.getWidth()/256;
			float stepHeight = (float)(this.getHeight())/(float)(max);
			
			Graphics g = image.getGraphics();
			for(int j = 0 ; j < 256 ; j++){
				if(j == BINARY_LEVEL)
					g.setColor(Color.red);
				else
					g.setColor(Color.black);
				g.fillRect(j*columnWidth, this.getHeight()-(int) (histogramme[j]*stepHeight), columnWidth, (int) (histogramme[j]*stepHeight));
			}
		}
			
		return image;
	}
	
}
