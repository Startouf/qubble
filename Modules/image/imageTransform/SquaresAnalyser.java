package imageTransform;

import imageObject.ConnexeComponent;
import imageObject.Point;
import imageObject.QRCode;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Traite les différentes composantes connexes de l'image pour en faire ressortir les QR codes et les afficher.
 * @author masseran
 *
 */
public class SquaresAnalyser {
	
	// Liste des carrés trouvés avec ses coordonnées
	private HashMap<Integer, Point> squareFound;
	private ArrayList<ConnexeComponent> squareList;
	private MyImage image;
	public static int SQUARESIZE = 50;
	
	public SquaresAnalyser(MyImage binaryImage, ComponentsAnalyser componentResult){
						
		int imageHeight = binaryImage.getHeight();
		int imageWidth = binaryImage.getWidth();
			
		squareList = new ArrayList<ConnexeComponent>();
		image = new MyImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);	
					
		// Garder les compo carré de grande taille // Petite taille + Créer Qr Code
		for(ConnexeComponent cc : componentResult.getCClist()){
			// Affichage de la longueur des compos afin de trouver quel seuil prendre pour la détection des carrés
			//System.out.println("Longueur : " + cc.getLength());
					
			if(Math.abs(SQUARESIZE - cc.getLength()) < 8 && cc.isSquare()){
				squareList.add(cc);
			}
		}
	}
		
	/**
	 * Retourne une image tous les carrés de la bonne taille avec une couleur différente
	 * @return
	 */
	public MyImage getQRCodesImage() {
		Graphics g = image.getGraphics();
		g.fillRect(0, 0, image.getWidth(), image.getHeight());
		
		Color compoColor = null;
		// Affichage
		for(ConnexeComponent cc : squareList){
			compoColor = new Color ((int) (Math.random()*255), ((int) Math.random()*255), (int) (Math.random()*255) );
			for(Point pt : cc.getConnexePoints()){
				image.setRGB(pt.getX(), pt.getY(), compoColor.getRGB());
			}
		}
		
		return image;
	}
	
	/** A IMPLEMENTER
	 * Retourne une image avec les courbes représentatives  pour chaque composantes connexes 
	 * de la distance du point central à l'extrémité la plus loin en fonction de l'angle
	 * @return
	 */
	public MyImage getGraphicSquareForm() {
		Graphics g = image.getGraphics();
		g.fillRect(0, 0, image.getWidth(), image.getHeight());
		
		/* Afficher la courbe d'un carré parfait */
//			g.setColor(Color.cyan);
//			for(int j = 0; j<180; j++){
//				g.drawLine(j, (int)(ConnexeComponent.perfectSquare[j%90]*100), j, (int)(ConnexeComponent.perfectSquare[j%90]*100));
//			}
		
		return image;
	}
	
	public HashMap<Integer, Point> getSquarePosition(){
		squareFound = new HashMap<Integer, Point>();
		
		for(ConnexeComponent cc : squareList){
			squareFound.put((int) (Math.random()*5000), new Point(cc.getxCenter()/50, cc.getyCenter()/50));
		}
		
		for(Integer i : squareFound.keySet()){
			System.out.println("Id du carré : " + i + " avec pour position : " + "(" + squareFound.get(i).getX() + "," + squareFound.get(i).getY() + ")");
		}
		
		return squareFound;
	}
		
}
