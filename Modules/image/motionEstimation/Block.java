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
	
	private BufferedImage image, bestMotion;
	
	/**
	 * Création des références du block
	 * Taille : width et height
	 * Deux points importants : Point central (xCenter, yCenter) et le coin supérieur droit (xCorner, yCorner)
	 */
	public Block(int xCenter, int yCenter, int imgWidth, int imgHeight, int sizeWidth, int sizeHeight){
		init(xCenter, yCenter, imgWidth, imgHeight, sizeWidth, sizeHeight);
	}
	
	
	public void init(int xCenter, int yCenter, int imgWidth, int imgHeight, int sizeWidth, int sizeHeight){
		this.xCenter = xCenter;
		this.yCenter = yCenter;
		// Récupération des coordonnées du coin droit
		if(xCenter-(sizeWidth/2) < 0){
			xCorner = 0;
		}else{
			xCorner = xCenter-(sizeWidth/2);
		}
		if(yCenter-(sizeHeight/2) < 0){
			yCorner = 0;
		}else{
			yCorner = yCenter-(sizeHeight/2);
		}
		// Taille de la zone du block
		if(xCenter + (sizeWidth/2) > imgWidth){
			width = imgWidth - xCorner;
		}else{
			width = (xCenter + (sizeWidth/2)) - xCorner;
		}
		if(yCenter + (sizeHeight/2) > imgHeight){
			height = imgHeight - yCorner;
		}else{
			height = (yCenter + (sizeHeight/2)) - yCorner;
		}
	}
	/*
	 * je vais faire une liste de tous les blocks d'une image suivant
	 *  les paramètres du BM, je néglige certains effets de bords (bloccks périphériques < 32 )
	 */
	/*public static ArrayList<Block> listOfBlock(BufferedImage cur, BlockMatching param){
		int brow = param.getBlockSizeRow();
		int bcol = param.getBlockSizeCol();
		int XMax = cur.getWidth()-brow+1;
		int YMax = cur.getHeight()-bcol+1;
		ArrayList<Block> l = new ArrayList<Block>();
		for (int j=0; j <YMax; j += brow){// on remarque que je néglige les effets de bords de droite et du bas (je ne pense pas que ca genera beaucoup)
			for (int i =0; i< XMax; i += bcol){
				l.add(new Block(i,j,cur.getSubimage(i, j, bcol, brow)));
			}
		}
		return l;
	}*/

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

	public void setRbest(BufferedImage rbest, int motionX, int motionY) {
		bestMotion = new BufferedImage(rbest.getWidth(), rbest.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		Graphics g = bestMotion.getGraphics();
		g.drawImage(rbest, 0, 0, rbest.getWidth(), rbest.getHeight(), null);
		this.motionX = motionX*3;
		this.motionY = motionY*3;
	}


	public void move(int bestX, int bestY) {
		int xMove = bestX-xCorner;
		int yMove = bestY-yCorner;
		xCorner = bestX;
		yCorner = bestY;		
	}

}
