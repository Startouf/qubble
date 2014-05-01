package motionEstimation;
import imageTransform.TabImage;

import java.awt.image.BufferedImage;

/*
 * fonction d'erreur quadratique moyenne, ainsi que
 *  le psnr "Peak Signal to Noise Ratio" qui est peut etre facultatif
 */
public class Erreur {
	
	public static int errQM(TabImage cur, TabImage ref, Block blockCur, Block blockRef){
		
		int[][] tabCur = cur.getImg();
		int[][] tabRef = ref.getImg();
		int err = 0;
		int errSave = 0;
		/*
		 * je calcule l'erreur quadratique moyenne de deux blocs de meme taille
		 */
		// par difference des deux images
		int xBlCur = blockCur.getxCorner(), yBlCur = blockCur.getyCorner();
		int xBlRef = blockRef.getxCorner(), yBlRef = blockRef.getyCorner();
		
		for(int i = 0; i < blockCur.getWidth(); i++){
			for(int j = 0; j < blockCur.getHeight(); j++){
				errSave = ((tabCur[xBlCur+i][yBlCur+j] - tabRef[xBlRef+i][yBlRef+j])*(tabCur[xBlCur+i][yBlCur+j] - tabRef[xBlRef+i][yBlRef+j]));
				err = err + errSave;
			}
		}
		
		//err = err/(blockCur.getHeight()*(blockCur.getWidth()));// ne sert pas à grand chose car on compare des blocs de meme taille
			
		return err;
	}

}
