package imageObject;

import java.util.ArrayList;

/**
 * Représente un QR Code détecté par l'ordinateur
 * Contient : un cadre, 3 repères, une liste de composantes connexes
 * @author masseran
 *
 */
public class QRCode {
	
	private ConnexeComponent border;
	private ConnexeComponent[] landmark;
	private ArrayList<ConnexeComponent> insideCC;
	
	public QRCode(){
		
	}
	
	
}
