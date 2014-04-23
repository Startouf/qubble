package imageTransform;

import imageObject.ConnexeComponent;
import imageObject.Point;
import imageObject.QRCode;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import com.googlecode.javacv.cpp.ARToolKitPlus.CornerPoint;

/**
 * Traite les différentes composantes connexes de l'image pour en faire ressortir les QR codes et les afficher.
 * @author masseran
 *
 */
public class QRCodesAnalyser {
	
	public static int BIGSQUARESIZE = 116;
	public static int SMALLSQUARESIZE = 88;
	
	private ArrayList<QRCode> listQRcode;
	// Contient les ids des QR codes et sa position
	private HashMap<Integer, Point> qrFound;
	private MyImage image;
	
	
	public QRCodesAnalyser(MyImage tableImage, MyImage varianceImage, ComponentsAnalyser componentResult){
		
		int imageHeight = tableImage.getHeight();
		int imageWidth = tableImage.getWidth();
		
		//ArrayList<ConnexeComponent> smallSquare = new ArrayList<ConnexeComponent>();
		listQRcode = new ArrayList<QRCode>();
		
		image = new MyImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);	
				
		// Garder les compo carré de grande taille // Petite taille + Créer Qr Code
		for(ConnexeComponent cc : componentResult.getCClist()){

			if(cc.getConnexePoints().size() > 100){
				if(((Math.abs(SMALLSQUARESIZE - cc.getLength()) < 10)/* || (Math.abs(BIGSQUARESIZE - cc.getLength()) < 10)*/) && cc.isSquare()){
					listQRcode.add(new QRCode(cc, tableImage, new MyImage(tableImage.getSubimage(cc.getCorner(0).getX()-5, cc.getCorner(2).getY()-5, 160, 160))));
				}
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
			
			g.setColor(Color.black);
			for(int i = 0; i<4; i++){
				g.fillRect(qr.getBorder().getCorner(i).getX()-4, qr.getBorder().getCorner(i).getY()-4, 8, 8);
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
