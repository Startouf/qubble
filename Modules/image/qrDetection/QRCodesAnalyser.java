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

import main.ImageDetection;


import com.googlecode.javacv.cpp.ARToolKitPlus.CornerPoint;

/**
 * Traite les différentes composantes connexes de l'image pour en faire ressortir les QR codes et les afficher.
 * @author masseran
 *
 */
public class QRCodesAnalyser {
	
	// Longueur du demi-rayon du carré attendu
	public static int SQUARESIZE = 42;
	
	private ArrayList<QRCode> listQRcode;
	private ArrayList<Integer[]> patternList;
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
		qrFound = new HashMap<Integer, Point>();
		patternList = new ArrayList<Integer[]>();
		Integer[] pattern;		
		// Garder les compo carré de grande taille // Petite taille + Créer Qr Code
		for(ConnexeComponent cc : componentResult.getCClist()){

			if(cc.getConnexePoints().size() > 100){
				if((pattern = cc.isSquare(SQUARESIZE)) != null){
					patternList.add(pattern);
					listQRcode.add(new QRCode(cc, tableImage));
				}
			}
			
		}
		// Affichage et calcul des id's de chaque qr
		for(QRCode qr : listQRcode){
			if(ImageDetection.PRINTDEBUG){
				System.out.println("Valeur du QR Code : " + qr.getValeurByCenter(targetQr));
			} else{
				qr.getValeurByCenter(targetQr);
			}
			qrFound.put(qr.getID(), new Point(qr.getBorder().getxCenter(), qr.getBorder().getyCenter()));
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
	 * Affiche pour chaque qr code, les différentes zones analysées pour trouver sa valeur : blue = 0, rouge = 1 
	 */
	public BufferedImage printValeur(TabImage tab){
		BufferedImage img = tab.ColorArrayToBufferedImage();
		Graphics g = img.getGraphics();
		for(Point pt : targetQr.keySet()){
			if(targetQr.get(pt)){
				g.setColor(Color.red);
			}else{
				g.setColor(Color.blue);
			}
			
			g.fillRect(pt.getX() - 2, pt.getY() -2, 4, 4);
		}
		return img;
		
	}
	
	public HashMap<Integer, Point> getQRList(){
		return qrFound;
	}
	
	/**
	 * Construit l'image qui contient les signatures de qr codes pour analyser
	 * @return
	 */
	public BufferedImage getPatternImage(){
		BufferedImage img = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		g.setColor(Color.BLACK);
		int countX = 0, countY = 0;
		for(Integer[] pt : patternList){
			for(int i = 0; i <180 ; i++){
				g.drawLine(i+(countX%4*180), pt[i]+(countY)*200, i+(countX%4*180), pt[i]+(countY)*200);
			}
			countX++;
			countY=countX/4;
		}
		
		
		return img;
	}

	public ArrayList<QRCode> getListQRcode() {
		return listQRcode;
	}
	
	

}
