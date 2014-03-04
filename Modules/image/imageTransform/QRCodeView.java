package imageTransform;

import imageObject.ConnexeComponent;
import imageObject.Point;
import imageObject.QRCode;

import java.awt.Color;
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
		
		// Grder les compo carré de grande taille // Petite taille + Créer Qr Code
		for(ConnexeComponent cc : comp.getCClist()){
			if(cc.isSquare())
				Square.add(cc);
		}
		
		// Assembler les QrCode
		
		// Chercher la valeur
		
		image = new MyImage(200, 200, BufferedImage.TYPE_INT_ARGB);
		
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
