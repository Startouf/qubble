package imageTransform;

import imageObject.ConnexeComponent;
import imageObject.Point;
import imageObject.QRCode;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Traite les différentes composantes connexes de l'image pour en faire ressortir les QR codes et les afficher.
 * @author masseran
 *
 */
public class QRCodeView {
	
	public static final int BIGSQUARESIZE = 190;
	public static final int SMALLSQUARESIZE = 35;
	
	private ArrayList<QRCode> listQRcode;
	private MyImage image;
	
	private ArrayList<ConnexeComponent> smallSquare;
	
	public QRCodeView(MyImage screen, Component comp){
		
		int imageHeight = screen.getHeight();
		int imageWidth = screen.getWidth();
		
		image = new MyImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);	
		Graphics g = image.getGraphics();
		g.fillRect(0, 0, imageWidth, imageHeight);
		
		smallSquare = new ArrayList<ConnexeComponent>();
		listQRcode = new ArrayList<QRCode>();
		
		int i = 0;
		// Garder les compo carré de grande taille // Petite taille + Créer Qr Code
		for(ConnexeComponent cc : comp.getCClist()){
			
			/*	if(i == 0){
				g.setColor(Color.blue);
			}
			if(i == 1){
				g.setColor(Color.green);
			}
			if(i == 2){
				g.setColor(Color.red);
			}
			if(i == 3){
				g.setColor(Color.black);
			}
			if(i == 4){
				g.setColor(Color.orange);
			}
			i++;*/
			System.out.println("Longueur : " + cc.getLength());
			if(Math.abs(SMALLSQUARESIZE - cc.getLength()) < 5 && cc.isSquare(g)){
				smallSquare.add(cc);
			}else if(Math.abs(BIGSQUARESIZE - cc.getLength()) < 10 && cc.isSquare(g)){
				listQRcode.add(new QRCode(cc));
			}
		}
		
		/* Afficher la courbe d'un carré parfait
		 * g.setColor(Color.cyan);
		for(int j = 0; j<180; j++){
			g.drawLine(j, (int)(ConnexeComponent.perfectSquare[j%90]*100), j, (int)(ConnexeComponent.perfectSquare[j%90]*100));
		}*/
		
		// Assembler les QrCodes
		for(ConnexeComponent cc : smallSquare){
			for(QRCode qr : listQRcode){
				if(cc.getxMin() > qr.getBorder().getxMin() && cc.getyMin() > qr.getBorder().getyMin() && cc.getxMax() < qr.getBorder().getxMax() && cc.getxMax() < qr.getBorder().getxMax()){
					qr.addLandMark(cc);
					break;
				}
					
			}
		}
		
		// Chercher la valeur
		
		Color compoColor = null;
		// Affichage
		for(QRCode qr : listQRcode){
			compoColor = new Color ((int) (Math.random()*255), ((int) Math.random()*255), (int) (Math.random()*255) );
			for(Point pt : qr.getBorder().getConnexePoints()){
				image.setRGB(pt.getX(), pt.getY(), compoColor.getRGB());
			}
			for(ConnexeComponent landmark : qr.getLandMark()){
				for(Point pt : landmark.getConnexePoints()){
					image.setRGB(pt.getX(), pt.getY(), compoColor.getRGB());
				}
			}
			qr.getValeur();
		}
	}
	
	public MyImage getImage() {
		return image;
	}
	

}
