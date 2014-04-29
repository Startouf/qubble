package motionEstimation;
import imageTransform.TabImage;

import java.awt.image.BufferedImage;

/*
 * fonction d'erreur quadratique moyenne, ainsi que
 *  le psnr "Peak Signal to Noise Ratio" qui est peut etre facultatif
 */
public class Erreur {
	
	public static float errQM(TabImage cur, TabImage ref, Block blockCur, Block blockRef)
			throws Exception{
		
		int[][] tabCur = cur.getImg();
		int[][] tabRef = ref.getImg();
		float err = 0;
		
		/*
		 * je calcule l'erreur quadratique moyenne de deux blocs de meme taille
		 */
		// par difference des deux images
		
		for(int i = blockCur.getxCorner(); i < blockCur.getxCorner()+blockCur.getWidth(); i++){
			for(int j = blockCur.getyCorner(); j < blockCur.getyCorner()+blockCur.getHeight(); j++){
				err = err + (Math.abs(tabCur[i][j] - tabRef[i][j])*Math.abs(tabCur[i][j] - tabRef[i][j]));
			}
		}
		
		//err = err/(blockCur.getHeight()*(blockCur.getWidth()));// ne sert pas à grand chose car on compare des blocs de meme taille
			
		return err;
	}

}
