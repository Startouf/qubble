package image_GUI;

import imageObject.ConnexeComponent;
import imageTransform.TabImage;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import qrDetection.QRCodesAnalyser;
import qrDetection.QR_Detection;

/**
 * Affiche un menu en bas de l'écran
 * Il y a 3 boutons pour effectuer des actions sur l'affichage
 * Il 6 input box pour gérer les paramètres de l'application
 * @author eric
 *
 */
public class ParamControl extends JPanel implements DocumentListener, ActionListener{
	private JTextField binaryLevelJTF, squareSizeJTF, squareTriggerJTF;
	private JTextField varWindowJTF, squareDiffJTF,  nullJTF;
	private JButton suivant, action, precedent;
	
	private QR_Detection controlDetection;
	private ImageView imageView;
	private Window window;
	
	public ParamControl(Window window, QR_Detection qrDetect, ImageView imgView, int binary, int rayon, int square, int varWindow, int squareDiff, int nothing) {
		this.window = window;
		this.controlDetection = qrDetect;
		this.imageView = imgView;
		this.setLayout(new GridLayout(5, 3));
		
		precedent = new JButton("Précédent");
		action = new JButton("Transformer !");
		suivant = new JButton("Suivant");
		precedent.addActionListener(this);
		action.addActionListener(this);
		suivant.addActionListener(this);
	
		
		this.add(precedent);
		this.add(action);
		this.add(suivant);
		this.add(new JLabel("Binarisation (0-255)"));
		this.add(new JLabel("Seuil de signature (0-100%)"));
		this.add(new JLabel("Taille rayon"));
		binaryLevelJTF = new JTextField(String.valueOf(binary));
		squareSizeJTF = new JTextField(String.valueOf(rayon));
		squareTriggerJTF = new JTextField(String.valueOf(square));
		changeBinaryLevel(binary);
		changeSquareSize(rayon);
		changeSquareTrigger(square);
		
		// Ajouter la relation entre le document et le jtextfield
		binaryLevelJTF.getDocument().putProperty("parent", binaryLevelJTF);
		squareSizeJTF.getDocument().putProperty("parent", squareSizeJTF);
		squareTriggerJTF.getDocument().putProperty("parent", squareTriggerJTF);
		
		binaryLevelJTF.getDocument().addDocumentListener(this);
		squareSizeJTF.getDocument().addDocumentListener(this);
		squareTriggerJTF.getDocument().addDocumentListener(this);
		
		this.add(binaryLevelJTF);
		this.add(squareTriggerJTF);
		this.add(squareSizeJTF);
		
		this.add(new JLabel("Taille fenetre variance"));
		this.add(new JLabel("Tolérance taille carré"));
		this.add(new JLabel("Null"));
		
		varWindowJTF = new JTextField(String.valueOf(varWindow));
		squareDiffJTF = new JTextField(String.valueOf(squareDiff));
		nullJTF = new JTextField(String.valueOf(nothing));
		changeBinaryLevel(binary);
		changeSquareSize(rayon);
		changeSquareTrigger(square);
		
		// Ajouter la relation entre le document et le jtextfield
		varWindowJTF.getDocument().putProperty("parent", varWindowJTF);
		squareDiffJTF.getDocument().putProperty("parent", squareDiffJTF);
		nullJTF.getDocument().putProperty("parent", nullJTF);
		
		varWindowJTF.getDocument().addDocumentListener(this);
		squareDiffJTF.getDocument().addDocumentListener(this);
		nullJTF.getDocument().addDocumentListener(this);
		
		this.add(varWindowJTF);
		this.add(squareDiffJTF);
		this.add(nullJTF);
	}
	
	//######################################"
	// Gestion des actions des boutons
	//######################################"
	
	@Override
	/**
	 * Gestion des actions lors d'un appui sur un bouton de la fênetre
	 */
	public void actionPerformed(ActionEvent e) {
		// Actualiser la visualisation de la dernière reconnaissance de forme
		if(e.getSource() == action){
			window.oneShot();
		}
		if(e.getSource() == suivant){
			imageView.next();
		}
		if(e.getSource() == precedent){
			imageView.previous();
		}
	}
	
	//######################################"
	// Gestion des InputBox et modification des variables associées
	//######################################"
	

	@Override
	public void changedUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		//System.out.println("changed");
	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		//System.out.println("insert");
		changingValue(arg0);
	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		//System.out.println("remove");
		changingValue(arg0);
	}
	
	/**
	 * Fonction qui gère la modification d'un paramètre après le déclenchement d'une modification d'un JTextfield
	 * @param e
	 */
	private void changingValue(DocumentEvent e){
		int value = 0;
		
		try{
			value = Integer.parseInt(((JTextField)(e.getDocument().getProperty("parent"))).getText());
		}catch(NumberFormatException error){
			
		}
		//System.out.println(value);
		if(e.getDocument().equals(binaryLevelJTF.getDocument())){
			changeBinaryLevel(value);			
		}else if(e.getDocument().equals(squareSizeJTF.getDocument())){
			changeSquareSize(value);	
		}else if(e.getDocument().equals(squareTriggerJTF.getDocument())){
			changeSquareTrigger(value);	
		}else if(e.getDocument().equals(varWindowJTF.getDocument())){
			changeVarWindowSize(value);	
		}else if(e.getDocument().equals(squareDiffJTF.getDocument())){
			changesquareDiffLevel(value);	
		}else if(e.getDocument().equals(nullJTF.getDocument())){

		}
	}
	
	
	/**
	 * Modifie le seuil de variance
	 * @param value
	 * @return true si 0 <= value 
	 */
	private boolean changesquareDiffLevel(int value){
		if(value < 0){
			return false;
		}else{
			imageObject.ConnexeComponent.SQUAREDIFF = value;
			return true;
		}
	}
	
	/**
	 * Modifie la taille de la fenêtre pour la variance
	 * @param value
	 * @return true si 0 <= value 
	 */
	private boolean changeVarWindowSize(int value){
		if(value < 0){
			return false;
		}else{
			qrDetection.QR_Detection.VAR_WINDOW = value;
			return true;
		}
	}
	
	/**
	 * Modifie le seuil pour la transformation en image binaire
	 * @param value
	 * @return true si 0 <= value <= 255 sinon false
	 */
	private boolean changeBinaryLevel(int value){
		if(value < 0 || value > 255){
			return false;
		}else{
			TabImage.BINARY_LEVEL = value;
			return true;
		}
	}
	
	/**
	 * Modifie le seuil de détection des grands carrés qui font le contour des QR codes
	 * @param value
	 * @return true si 0 <= value sinon false
	 */
	private boolean changeSquareSize(int value){
		if(value < 0){
			return false;
		}else{
			QRCodesAnalyser.SQUARESIZE = value;
			return true;
		}
	}
	
	/**
	 * Modifie le seuil de détection des petits carrés pour le repère des QR codes
	 * @param value
	 * @return true si 0 <= value sinon false
	 */
	private boolean changeSquareTrigger(int value){
		if(value < 0 || value > 100){
			return false;
		}else{
			ConnexeComponent.SQUARETRIGGER = value/(float)(100);
			return true;
		}
	}



}
