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
public class QRCodesAnalyser {
	
	public static int BIGSQUARESIZE = 210;
	public static int SMALLSQUARESIZE = 35;
	
	private ArrayList<QRCode> listQRcode;
	// Contient les ids des QR codes et sa position
	private HashMap<Integer, Point> qrFound;
	private MyImage image;
	
	
	public QRCodesAnalyser(MyImage binaryImage, ComponentsAnalyser componentResult){
		
		int imageHeight = binaryImage.getHeight();
		int imageWidth = binaryImage.getWidth();
		
		//ArrayList<ConnexeComponent> smallSquare = new ArrayList<ConnexeComponent>();
		listQRcode = new ArrayList<QRCode>();
		
		image = new MyImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);	
				
		// Garder les compo carré de grande taille // Petite taille + Créer Qr Code
		for(ConnexeComponent cc : componentResult.getCClist()){
			// Affichage de la longueur des compos afin de trouver quel seuil prendre pour la détection des carrés
			//System.out.println("Longueur : " + cc.getLength());
			
/*			if(Math.abs(SMALLSQUARESIZE - cc.getLength()) < 5 && cc.isSquare()){
				smallSquare.add(cc);
			}else*/ if(Math.abs(BIGSQUARESIZE - cc.getLength()) < 10 && cc.isSquare()){
				listQRcode.add(new QRCode(cc, binaryImage));
			}
		}
		
		// Assembler les QrCodes
/*		for(ConnexeComponent cc : smallSquare){
			for(QRCode qr : listQRcode){
				if(cc.getxMin() > qr.getBorder().getxMin() && cc.getyMin() > qr.getBorder().getyMin() && cc.getxMax() < qr.getBorder().getxMax() && cc.getyMax() < qr.getBorder().getyMax()){
					qr.addLandMark(cc);
					break;
				}
					
			}
		}*/
		
		for(QRCode qr : listQRcode){
			System.out.println("Valeur du QR code : " + qr.getValeur());
		}
		
	}
	
	/**
	 * Retourne une image avec le contour et les 3 repères de chaque QR codes dans une couleur 
	 * @return
	 */
	public MyImage getQRCodesImage() {
		Graphics g = image.getGraphics();
		g.fillRect(0, 0, image.getWidth(), image.getHeight());
		
		Color compoColor = null;
		// Affichage
		for(QRCode qr : listQRcode){
			compoColor = new Color ((int) (Math.random()*255), ((int) Math.random()*255), (int) (Math.random()*255) );
			for(Point pt : qr.getBorder().getConnexePoints()){
				image.setRGB(pt.getX(), pt.getY(), compoColor.getRGB());
			}
			image.setRGB(qr.getBorder().getCorner(0).getX(), qr.getBorder().getCorner(0).getY(), Color.cyan.getRGB());
			image.setRGB(qr.getBorder().getCorner(1).getX(), qr.getBorder().getCorner(1).getY(), Color.yellow.getRGB());
			image.setRGB(qr.getBorder().getCorner(2).getX(), qr.getBorder().getCorner(2).getY(), Color.red.getRGB());
			image.setRGB(qr.getBorder().getCorner(3).getX(), qr.getBorder().getCorner(3).getY(), Color.blue.getRGB());
/*			for(ConnexeComponent landmark : qr.getLandMark()){
				for(Point pt : landmark.getConnexePoints()){
					image.setRGB(pt.getX(), pt.getY(), compoColor.getRGB());
				}
			}*/
			
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
//		g.setColor(Color.cyan);
//		for(int j = 0; j<180; j++){
//			g.drawLine(j, (int)(ConnexeComponent.perfectSquare[j%90]*100), j, (int)(ConnexeComponent.perfectSquare[j%90]*100));
//		}
		
		return image;
	}
	
	/**
	 * Affiche pour chaque qr code valide son id
	 */
	public void getValeur(){
		for(QRCode qr : listQRcode){
			System.out.println("Valeur du QR Code : " + qr.getValeur());;
		}
	}

}
