package qrDetection;

import imageObject.ConnexeComponent;
import imageObject.Point;
import imageObject.QRCode;
import imageTransform.TabImage;

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
	
	// Longueur du demi-rayon du carré attendu
	public static int SQUARESIZE = 88;
	
	private ArrayList<QRCode> listQRcode;
	// Liste des points qui ont été analysé pour détecter la valeur du QR
	private HashMap<Point, Boolean> targetQr;
	// Contient les ids des QR codes et sa position
	
	private HashMap<Integer, Point> qrFound;
	private int imageHeight;
	private int imageWidth;
	
	
	public QRCodesAnalyser(TabImage tableImage, TabImage varianceImage, ComponentsAnalyser componentResult){
		
		imageHeight = tableImage.getHeight();
		imageWidth = tableImage.getWidth();
		
		targetQr = new HashMap<Point, Boolean>();
		listQRcode = new ArrayList<QRCode>();
				
		// Garder les compo carré de grande taille // Petite taille + Créer Qr Code
		for(ConnexeComponent cc : componentResult.getCClist()){

			if(cc.getConnexePoints().size() > 100){
				if(cc.isSquare(SQUARESIZE)){
					listQRcode.add(new QRCode(cc, tableImage));
				}
			}
			
		}
	}
	
	/**
	 * Retourne une image avec le contour et les 3 repères de chaque QR codes dans une couleur 
	 * @return
	 */
	public BufferedImage getQRCodesImage() {
		BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);	
		Graphics g = image.getGraphics();
		g.fillRect(0, 0, image.getWidth(), image.getHeight());
		
		Color compoColor = null;
		// Affichage
		for(QRCode qr : listQRcode){
			compoColor = new Color ((int) (Math.random()*255), ((int) Math.random()*255), (int) (Math.random()*255) );
			for(Point pt : qr.getBorder().getConnexePoints()){
				image.setRGB(pt.getX(), pt.getY(), compoColor.getRGB());
			}
			
			// Affichage des 4 coins de la composante
			/*for(int i = 0; i<4; i++){
				g.setColor(new Color(i*255/4, i*255/4, i*255/4));
				g.fillRect(qr.getBorder().getCorner(i).getX()-4, qr.getBorder().getCorner(i).getY()-4, 8, 8);
			}*/
			if(qr.getBorder().getConnexePoints().size()> 100){
				g.setColor(Color.green);
				g.fillRect(qr.getBorder().getConnexePoints().get(0).getX()-4, qr.getBorder().getConnexePoints().get(0).getY()-4, 8, 8);
				
				g.setColor(Color.green);
				g.fillRect(qr.getBorder().getConnexePoints().get(qr.getBorder().getConnexePoints().size()-1).getX()-4, qr.getBorder().getConnexePoints().get(qr.getBorder().getConnexePoints().size()-1).getY()-4, 8, 8);
				
			}
			
		}
		for(Point pt : targetQr.keySet()){
			if(targetQr.get(pt)){
				g.setColor(Color.red);
			}else{
				g.setColor(Color.blue);
			}
			
			g.fillRect(pt.getX() - 2, pt.getY() -2, 4, 4);
		}
		
		return image;
	}
	
	/** A IMPLEMENTER
	 * Retourne une image avec les courbes représentatives  pour chaque composantes connexes 
	 * de la distance du point central à l'extrémité la plus loin en fonction de l'angle
	 * @return
	 */
/*	public TabImage getGraphicSquareForm() {
		Graphics g = image.getGraphics();
		g.fillRect(0, 0, image.getWidth(), image.getHeight());
		
		 Afficher la courbe d'un carré parfait 
//		g.setColor(Color.cyan);
//		for(int j = 0; j<180; j++){
//			g.drawLine(j, (int)(ConnexeComponent.perfectSquare[j%90]*100), j, (int)(ConnexeComponent.perfectSquare[j%90]*100));
//		}
		
		return image;
	}*/
	
	/**
	 * Affiche pour chaque qr code valide son id
	 */
	public void getValeur(BufferedImage img){
		for(QRCode qr : listQRcode){
			System.out.println("Valeur du QR Code : " + qr.getValeurByCenter(targetQr));;
		}
		Graphics g = img.getGraphics();
		for(Point pt : targetQr.keySet()){
			if(targetQr.get(pt)){
				g.setColor(Color.red);
			}else{
				g.setColor(Color.blue);
			}
			
			g.fillRect(pt.getX() - 2, pt.getY() -2, 4, 4);
		}
		
	}

}
