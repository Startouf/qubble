package motionEstimation;
import java.awt.image.BufferedImage;

/*
 * fonction d'erreur quadratique moyenne, ainsi que
 *  le psnr "Peak Signal to Noise Ratio" qui est peut etre facultatif
 */
public class Erreur {
	
	public static float errQM(BufferedImage img1, BufferedImage img2)
			throws Exception{
		
		//je prends l'image de niveau de gris obtene par difference des deux images
		BufferedImage imD = ImageTransform.imageDifference(img1, img2);
		float err = 0;
		/*
		 * si la taille ne corrrespond pas, renvoyer une exception 
		 * (peut etre inutile car j'utilise la fonction imageDifference qui integre cela)
		 * 
		 */
		/*
		 * if(img1.getHeight() != img2.getHeight() && img1.getWidth() != img2.getWidth())
		 * throw new Exception();
		 */
			
		/*
		 * je calcule l'erreur quadratique moyenne de deux blocs de meme taille
		 */
		for (int i =0; i < img1.getHeight(); i++){
			for (int j = 0; j < img1.getWidth(); j++){
				err = err + (float)Math.pow(imD.getRGB(i, j) & 0xff, 2);
				//err = err/(img1.getHeight()*(img1.getWidth()));// ne sert pas à grand chose car on compare des blocs de meme taille
			}
		}
		return err;
	}

}
