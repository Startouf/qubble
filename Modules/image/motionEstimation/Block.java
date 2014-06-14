package motionEstimation;

import imageTransform.TabImage;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Représente un carré de l'image qu'on analyse
 * @author eric
 *
 */
public class Block {
	private int xCenter,yCenter, motionX, motionY, xCorner, yCorner;
	private int height, width;
	private int imgWidth, imgHeight;
	
	private BufferedImage image, bestMotion;
	
	/**
	 * Création des références du block
	 * Taille : width et height
	 * Deux points importants : Point central (xCenter, yCenter) et le coin supérieur droit (xCorner, yCorner)
	 */
	public Block(int xCenter, int yCenter, int imgWidth, int imgHeight, int sizeWidth, int sizeHeight){
		// Toujours paire
		width = sizeWidth +sizeWidth%2;
		height = sizeHeight + sizeHeight%2;
		this.imgWidth = imgWidth;
		this.imgHeight = imgHeight;
		intiCoord(xCenter, yCenter);
	}
	
	
	public void intiCoord(int xCenter, int yCenter){
		int halfWidth = width/2;
		int halfHeight = height/2;
		
				if(xCenter-halfWidth < 0){
					xCorner = 0;
					this.xCenter = halfWidth;
				}else{
					if(xCenter+(halfWidth) > imgWidth){
						this.xCenter = imgWidth-halfWidth;
						xCorner = xCenter-halfWidth;
					}else{
						xCorner = xCenter-halfWidth;
						this.xCenter = xCenter;
					}
				}
				if(yCenter-halfHeight < 0){
					yCorner = 0;
					this.yCenter = halfHeight;
				}else{
					if(xCenter+halfHeight > imgHeight){
						this.yCenter = imgHeight-halfHeight;
						yCorner = yCenter-halfHeight;
					}else{
						yCorner = yCenter-halfHeight;
						this.yCenter = yCenter;
					}
				}
	}

	public BufferedImage getImage() {
		return image;
	}
	public void setR(BufferedImage r) {
		image = r;
	}

	public BufferedImage getRbest() {
		return bestMotion;
	}
	
	public int getMotionX() {
		return motionX;
	}
	public int getMotionY() {
		return motionY;
	}	
	
	public int getxCenter() {
		return xCenter;
	}

	public int getyCenter() {
		return yCenter;
	}
	
	

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getxCorner() {
		return xCorner;
	}

	public int getyCorner() {
		return yCorner;
	}

	public void move(int bestX, int bestY) {
		intiCoord(bestX, bestY);		
	}

}
