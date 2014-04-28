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
 * Créer
 * Permet de créer une image en niveau de gris ou une image binaire
 * @author masseran
 *
 */
public class TabImage {
	
	// Niveau de sensibilité pour l'image binaire
	public static int BINARY_LEVEL = 180; 
	private int[] histogramme;
	private int[][] img;
	private int width, height;
	private boolean height_bit;

	
	public TabImage(int width, int height) {
		this.width = width;
		this.height = height;
		img = new int [width][height];
	}
	
	public TabImage(int[][] img, int width, int height) {
		this.width = width;
		this.height = height;
		this.img = img;
	}
	
	public TabImage(BufferedImage buf) {
		this.width = buf.getWidth();
		this.height = buf.getHeight();
		img = new int[width][height];

		for(int i = 0; i < width; i++)
		    for(int j = 0; j < height; j++)
		    	img[i][j] = buf.getRGB(i, j);
	}
	
	/**
	 * Transforme une image couleur en image niveau de gris
	 * @return
	 */
	public TabImage getGrey(){
		int greyValue = 0, red = 0, green = 0, blue = 0;
		int[][] greyImage = new int [width][height];
		for(int i = 0 ; i < width ; i++){
			for(int j = 0 ; j < height ; j++){
				// Calcul du niveau de gris
				int pix = img[i][j];
				red   = pix >> 16 & 0xff;
				green = pix >> 8 & 0xff;
				blue  = pix & 0xff;
				//greyValue = (int) (0.114 * red +  0.299 * green + 0.587 * blue);
				greyValue = (int)((299 * red +  587 * green + 114 * blue)/1000);
				greyImage[i][j] = (new Color(greyValue, greyValue, greyValue)).getRGB();
			}
		}
		return new TabImage(greyImage, width,height);
	}
	
	/**
	 * Transforme une image en niveau de gris en une image binaire
	 * @return
	 */
/*	public TabImage getBinaryMyImage(){
		int[][] binaryImage = new int [width][height];
		
		int binaryLevel = this.getBinaryLevel();
		for(int i = 0 ; i < width ; i++){
			for(int j = 0 ; j < height ; j++){
				// Calcul du niveau de gris
				if((img[i][j] & 0x000000ff) > binaryLevel)
					binaryImage[i][j] = (new Color(255, 255, 255).getRGB());
				else
					binaryImage[i][j] = (new Color(0, 0, 0).getRGB());
			}
		}
		return new TabImage(binaryImage, width,height);
	}*/
	
	/**
	 * Transforme une image en niveau de gris en une image binaire en décomposant l'image en sous image
	 * @return
	 */
	/*public TabImage getBinaryMyImageByBlock(){
		// Paramètre des blocs de l'image
		int blockX = 5, blockY = 4;
		int sizeBlockX = width/blockX, sizeBlockY = height/blockY;
		
		int[][] binaryTable = new int[blockX][blockY];
		int[][] binaryImage = new int [width][height];
		
		// Calcul des seuils pour chaque bloc
		for(int X = 0 ; X < blockX ; X++){
			for(int Y = 0 ; Y < blockY ; Y++){
				TabImage sub = new TabImage(this.getSubimage(X*sizeBlockX, Y*sizeBlockY, sizeBlockX-1, sizeBlockY-1));
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
	}*/
	
	
	/** A IMPLEMENTER
	 * Calcule le meilleur niveau de binarisation pour l'image afin d'avoir les bonnes composantes en noires
	 * @param greyImage
	 * @return
	 */
	/*private int getBinaryLevel(){		
		if(histogramme == null){
			getHistogramme();
		}
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
	}*/
	
	// Get binary treshold using Otsu's method
	/*private int otsuTreshold() {
		
	    int[] histogram = histogramme;
	    int total = height * width;
	 
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
	 
	}*/
	
	/**
	 * Construction de l'histogramme
	 */
	public void getHistogramme(){
		histogramme = new int[256];
		for(int i = 0 ; i < width ; i++){
			for(int j = 0 ; j < height ; j++){
				histogramme[img[i][j] & 0x000000ff]++;
			}
		}
	}
	
	/**
	 * 
	 * @return une image de l'histogramme
	 */
	public BufferedImage getHistogramImage(){
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		if(true){
			
			int max = 0;
			for(int j = 0 ; j < 256 ; j++){
				max = Math.max(max, histogramme[j]);
			}
			int columnWidth = width/256;
			float stepHeight = (float)(height)/(float)(max);
			
			Graphics g = image.getGraphics();
			for(int j = 0 ; j < 256 ; j++){
				if(j == BINARY_LEVEL)
					g.setColor(Color.red);
				else
					g.setColor(Color.black);
				g.fillRect(j*columnWidth, height-(int) (histogramme[j]*stepHeight), columnWidth, (int) (histogramme[j]*stepHeight));
			}
		}
			
		return image;
	}
	
	/*% *************************************************************************
	% Title: Function-Compute Variance map of the image
	% Inputs: Input Image (var: inputImage), Window Size (var: windowSize),
	% Threshold (var: thresh) Typical value is 140
	% Outputs: Variance Map (var: varianceImg) , Time taken (var: timeTaken)
	% Example Usage of Function: [varianceImg, timeTaken]=funcVarianceMap('MonopolyLeftColor.png', 5, 9);
	% **************************************************************************/
	/**
	 * Lis une image en niveau de gris
	 * @param window : taille de la fenêtre en pixel pour faire la moyenne
	 * @param thresh : seuil de binarisation de la variance
	 * @return
	 */
	public TabImage getVarianceFilter(int window, int thresh){
		int windowSize, sum, var;
		
		// Vérification que windowSize est un nombre impair
		if(window%2 == 1 && window>2){
			windowSize = (window-1)/2;
		}else{
			windowSize = (window)/2;
		}
		
		int[][] mean = new int [width][height];
		int[][] varianceImg = new int [width][height];
		
		// Parcours de l'image
		for(int i = windowSize; i < width-windowSize; i++){
			for(int j = windowSize; j < height-windowSize; j++){
				sum = 0;
				// Parcours de la fenêtre
				for(int a = -windowSize; a <= windowSize; a++){
					for(int b = -windowSize; b <= windowSize; b++){
						sum += img[i+a][j+b] & 0xff;
					}
				}
				mean[i][j] = (sum/(window*window));
				//System.out.println("mean : " + mean[i][j]);
			}
		}
		
		// Parcours de l'image
				for(int i = windowSize; i < width-windowSize; i++){
					for(int j = windowSize; j < height-windowSize; j++){
						sum = 0;
						// Parcours de la fenêtre
						for(int a = -windowSize; a <= windowSize; a++){
							for(int b = -windowSize; b <= windowSize; b++){
								sum += Math.pow(((img[i+a][j+b] & 0xff) - mean[i][j]), 2);
							}
						}
						var = sum/((window*window)-1);
						//System.out.println(var);
						if(var > BINARY_LEVEL){
							varianceImg[i][j] = Color.black.getRGB();
						}else{
							varianceImg[i][j] = Color.white.getRGB();
						}
					}
				}
				return new TabImage(varianceImg, width, height);
		
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public int getRGB(int x, int y){
		return img[x][y];
	}
	
	public void setRGB(int x, int y, int value){
		img[x][y] = value;
	}
	
	/**
	 * Créer une BufferedImage à partir d'un tableau de pixel
	 * @param data
	 * @param w
	 * @param h
	 * @return
	 */
	public BufferedImage ColorArrayToBufferedImage() {
		BufferedImage bimg = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		int[] linearbuffer = new int[width*height];
		int i=0;
		for(int y=0;y<height;y++)
			for(int x=0;x<width;x++)
				linearbuffer[i++]=img[x][y];
		bimg.getRaster().setDataElements(0, 0, width, height, linearbuffer);
		return bimg;
	}
	
}
