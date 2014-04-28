package motionEstimation;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Représente un carré de l'image qu'on analyse
 * @author eric
 *
 */
public class Block {
	private int x,y, motionX, motionY;
	private BufferedImage image, bestMotion;
	
	/*
	 * x et le y represente les coordonnées du centre du block dans l'image 
	 * Image : [][] qui contient les valeurs de l'image (carré de taille : rayon du carré)
	 */
	public Block(int h, int v, BufferedImage R, int taille){
		x  = h;
		y = v;
		this.image = R;
	}
	/*
	 * je vais faire une liste de tous les blocks d'une image suivant
	 *  les paramètres du BM, je néglige certains effets de bords (bloccks périphériques < 32 )
	 */
	public static ArrayList<Block> listOfBlock(BufferedImage cur, BlockMatching param){
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
	}

	public BufferedImage getImage() {
		return image;
	}
	public void setR(BufferedImage r) {
		image = r;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public BufferedImage getRbest() {
		return bestMotion;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public int getMotionX() {
		return motionX;
	}
	public int getMotionY() {
		return motionY;
	}
	
	public void setRbest(BufferedImage rbest, int motionX, int motionY) {
		bestMotion = new BufferedImage(rbest.getWidth(), rbest.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		Graphics g = bestMotion.getGraphics();
		g.drawImage(rbest, 0, 0, rbest.getWidth(), rbest.getHeight(), null);
		this.motionX = motionX*3;
		this.motionY = motionY*3;
	}

}
