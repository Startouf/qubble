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
		    	// Supprimer l'opacité
		    	img[i][j] = (buf.getRGB(i, j) & 0x00ffffff);
	}
	
	/**
	 * Transforme une image couleur en image niveau de gris
	 * @param : color :: true : 3 canal de gris, false : 1 canal 
	 * @return
	 */
	public TabImage getGrey(boolean color){
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
				if(color){
					greyImage[i][j] = greyValue | (greyValue<<8) | (greyValue<<16);
				}else{
					greyImage[i][j] = greyValue;
				}
				
			}
		}
		return new TabImage(greyImage, width,height);
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
		int windowSize, var;
		
		// Vérification que windowSize est un nombre impair
		if(window%2 == 1 && window>2){
			windowSize = (window-1)/2;
		}else{
			windowSize = (window)/2;
		}
		
		int[][] varianceImg = new int [width][height];
		
		int N = window*window;
		int sumCarre = 0, sum = 0;
		int temp = 0;
		
		// Calcul de la fenêtre initiale
		// Augmenter perf :
		for(int a = -windowSize; a <= windowSize; a++){
			for(int b = -windowSize; b <= windowSize; b++){
				temp = img[windowSize+a][windowSize+b] & 0xff;
				sum += temp;
				sumCarre += temp*temp;
			}
		}
		sum = sum*sum;
		var = (sumCarre - (sum/N))/N;
		//System.out.println(var)
		if(var > BINARY_LEVEL){
			varianceImg[windowSize][windowSize] = 0x00000000;
		}else{
			varianceImg[windowSize][windowSize] = 0x00ffffff;
		}
					
		// Parcours sur x
		for(int i = windowSize+1; i < width-windowSize; i++){
			
			// Enlever la colonne de gauche
			// Ajouter la colonne de droite
			for(int a = -windowSize; a <= windowSize; a++){
				temp = temp - (img[i-windowSize][windowSize+a] & 0xff) + (img[i+windowSize][windowSize+a] & 0xff);
			}
			
			// Parcours sur y
			for(int j = windowSize+1; j < height-windowSize; j++){
				sum = 0;
				sumCarre = 0;
				
				
				// Enlever la ligne du dessus
				// Ajouter la ligne du dessous
				for(int a = -windowSize; a <= windowSize; a++){
					temp = temp - (img[i+a][j-windowSize] & 0xff) + (img[i+a][j+windowSize] & 0xff);
					sum += temp;
					sumCarre += temp*temp;
				}
				
				sum = sum*sum;
				var = (sumCarre - (sum/N))/N;
				//System.out.println(var)
				if(var > BINARY_LEVEL){
					varianceImg[i][j] = 0x00000000;
				}else{
					varianceImg[i][j] = 0x00ffffff;
				}
				//System.out.println("mean : " + mean[i][j]);
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
		BufferedImage bimg = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		int[] linearbuffer = new int[width*height];
		int i=0;
		for(int y=0;y<height;y++)
			for(int x=0;x<width;x++)
				// Remettre l'opacité
				linearbuffer[i++]=(img[x][y] | 0xff000000);
		bimg.getRaster().setDataElements(0, 0, width, height, linearbuffer);
		return bimg;
	}

	public int[][] getImg() {
		return img;
	}
	
}
