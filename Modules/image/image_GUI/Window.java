package image_GUI;

import imageTransform.ComponentAnalyser;
import imageTransform.MyImage;
import imageTransform.QRCodesAnalyser;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

/**
 * Affiche une fenetre
 * @author masseran
 *
 */
public class Window extends JFrame implements ActionListener, DocumentListener{
	
	public static int imageWidth, imageHeight;
	private ImageView imageView;
	private JPanel control;
	private JButton suivant, action, precedent;
	private JTextField greyLevel, bigSquareSize, smallSquareSize;
	
	public Window(){
		this.setSize(800, 600);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setTitle("Reconnaissance de QR Code");
		
		control = new JPanel();
		control.setLayout(new GridLayout(3, 3));
		
		
		precedent = new JButton("Précédent");
		action = new JButton("Transformer !");
		suivant = new JButton("Suivant");
		precedent.addActionListener(this);
		action.addActionListener(this);
		suivant.addActionListener(this);
	
		
		control.add(precedent);
		control.add(action);
		control.add(suivant);
		control.add(new JLabel("Niveau de gris (0-255)"));
		control.add(new JLabel("Taille petit carré"));
		control.add(new JLabel("Taille grand carré"));
		greyLevel = new JTextField(String.valueOf(MyImage.GREY_LEVEL));
		bigSquareSize = new JTextField(String.valueOf(QRCodesAnalyser.BIGSQUARESIZE));
		smallSquareSize = new JTextField(String.valueOf(QRCodesAnalyser.SMALLSQUARESIZE));
		
		// Ajouter la relation entre le document et le jtextfield
		greyLevel.getDocument().putProperty("parent", greyLevel);
		bigSquareSize.getDocument().putProperty("parent", bigSquareSize);
		smallSquareSize.getDocument().putProperty("parent", smallSquareSize);
		
		greyLevel.getDocument().addDocumentListener(this);
		bigSquareSize.getDocument().addDocumentListener(this);
		smallSquareSize.getDocument().addDocumentListener(this);
		
		control.add(greyLevel);
		control.add(smallSquareSize);
		control.add(bigSquareSize);
		
		imageView = new ImageView();
		this.setLayout(new BorderLayout());
		this.getContentPane().add(imageView, BorderLayout.CENTER);
		this.getContentPane().add(control, BorderLayout.SOUTH);
		this.setVisible(true);
	}
	
	/** Lit une image de l'ordinateur
	 * 
	 * @param fichier
	 */
	public void readImage(File fichier){
		try {
			imageView.setImage(new MyImage(ImageIO.read(fichier)), imageView.COLOR);
			imageWidth = imageView.getImage(imageView.COLOR).getWidth();
			imageHeight = imageView.getImage(imageView.COLOR).getHeight();
			affiche();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void affiche(){
		//((ImageIcon) ((JLabel)(imageView.getComponent(0))).getIcon()).setImage(image);
		imageView.repaint();
		this.repaint();
	}

	@Override
	/**
	 * Gestion des actions lors d'un appui sur un bouton de la fênetre
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == action){
			long startTime = System.currentTimeMillis();
			imageView.setImage(imageView.getImage(imageView.COLOR).getGreyMyImage(), imageView.GREY);
			long greyTime = System.currentTimeMillis();
			imageView.setImage(imageView.getImage(imageView.GREY).getBinaryMyImage(), imageView.BINARY);
			long binaryTime = System.currentTimeMillis();
			ComponentAnalyser test = new ComponentAnalyser(imageView.getImage(imageView.BINARY));
			imageView.setImage(test.getCCMyImage(), imageView.CONNEXE);
			long componentTime = System.currentTimeMillis();
			QRCodesAnalyser qrImage = new QRCodesAnalyser(imageView.getImage(imageView.BINARY), test);
			imageView.setImage(qrImage.getImage(), imageView.QR_CODE);
			long qrTime = System.currentTimeMillis();
			
			long endTime = System.currentTimeMillis();
			System.out.println("Temps de calcul de la transformation en niveau de gris : " + (greyTime-startTime) + " ms.");
			System.out.println("Temps de calcul de la transformation en binaire : " + (binaryTime-greyTime) + " ms.");
			System.out.println("Temps de calcul pour trouver les composantes connexes: " + (componentTime-binaryTime) + " ms.");
			System.out.println("Temps de calcul pour trouver le qr code : " + (qrTime-componentTime) + " ms.");
			System.out.println("Temps de calcul du Block Matching : " + (endTime-startTime) + " ms.");
			
		}
		if(e.getSource() == suivant){
			imageView.next();
		}
		if(e.getSource() == precedent){
			imageView.previous();
		}
		affiche();
	}

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
		if(e.getDocument().equals(greyLevel.getDocument())){
			changeGreyLevel(value);			
		}else if(e.getDocument().equals(bigSquareSize.getDocument())){
			changeBigSquareSize(value);	
		}else if(e.getDocument().equals(smallSquareSize.getDocument())){
			changeSmallSquareSize(value);	
		}
	}
	
	
	
	
	/**
	 * Modifie le seuil pour la transformation en image binaire
	 * @param value
	 * @return true si 0 <= value <= 255 sinon false
	 */
	private boolean changeGreyLevel(int value){
		if(value < 0 || value > 255){
			return false;
		}else{
			MyImage.GREY_LEVEL = value;
			return true;
		}
	}
	
	/**
	 * Modifie le seuil de détection des grands carrés qui font le contour des QR codes
	 * @param value
	 * @return true si 0 <= value sinon false
	 */
	private boolean changeBigSquareSize(int value){
		if(value < 0){
			return false;
		}else{
			QRCodesAnalyser.BIGSQUARESIZE = value;
			return true;
		}
	}
	
	/**
	 * Modifie le seuil de détection des petits carrés pour le repère des QR codes
	 * @param value
	 * @return true si 0 <= value sinon false
	 */
	private boolean changeSmallSquareSize(int value){
		if(value < 0){
			return false;
		}else{
			QRCodesAnalyser.SMALLSQUARESIZE = value;
			return true;
		}
	}
	


}
