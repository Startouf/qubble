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
	
	private ArrayList<QRCode> listQRcode;
	private MyImage image;
	
	public ArrayList<ConnexeComponent> Square = new ArrayList<ConnexeComponent>();
	
	public QRCodeView(Component comp){
		image = new MyImage(200, 200, BufferedImage.TYPE_INT_ARGB);
		
		Graphics g = image.getGraphics();
		g.fillRect(0, 0, 200, 200);
		int i = 0;
		// Grder les compo carré de grande taille // Petite taille + Créer Qr Code
		for(ConnexeComponent cc : comp.getCClist()){
			/*
			if(i == 0){
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
			i++;
			*/
			if(cc.isSquare(g))
				Square.add(cc);
		}
		/*
		g.setColor(Color.cyan);
		for(int j = 0; j<90; j++){
			g.drawLine(j, (int)(ConnexeComponent.perfectSquare[j]*100), j, (int)(ConnexeComponent.perfectSquare[j]*100));
		}
		*/
		// Assembler les QrCode
		
		// Chercher la valeur
		
		
		
		for(ConnexeComponent cc : Square){
			
			for(Point pt : cc.getConnexePoints()){
				image.setRGB(pt.getX(), pt.getY(), Color.BLUE.getRGB());
			}
		}
		
		
	}
	
	public MyImage getImage() {
		return image;
	}
	

}
