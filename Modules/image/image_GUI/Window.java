package image_GUI;

import imageTransform.ComponentsAnalyser;
import imageTransform.MyImage;
import imageTransform.QRCodesAnalyser;
import imageTransform.SquaresAnalyser;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import camera.Camera;

/**
 * Affiche une fenetre
 * @author masseran
 *
 */
public class Window extends JFrame implements ActionListener, DocumentListener{
	
	// Index pour retrouver les images spécifiques dans le lecteur
	public int COLOR = -1, GREY = -1, BINARY = -1, CONNEXE = -1, QR_CODE = -1, COURBE = -1; 
	
	public static int imageWidth, imageHeight;
	private ImageView imageView;
	private JPanel control;
	private JButton suivant, action, precedent;
	private JTextField binaryLevelJTF, bigSquareSizeJTF, smallSquareSizeJTF;
	private boolean qrCodesSearch;
	private Camera wb;
	
	public Window(){
		init();
	}
	
	public Window(Camera wb){
		init();
		this.wb = wb;
	}
	
	private void init(){
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
		binaryLevelJTF = new JTextField(String.valueOf(MyImage.BINARY_LEVEL));
		bigSquareSizeJTF = new JTextField(String.valueOf(QRCodesAnalyser.BIGSQUARESIZE));
		smallSquareSizeJTF = new JTextField(String.valueOf(QRCodesAnalyser.SMALLSQUARESIZE));
		
		// Ajouter la relation entre le document et le jtextfield
		binaryLevelJTF.getDocument().putProperty("parent", binaryLevelJTF);
		bigSquareSizeJTF.getDocument().putProperty("parent", bigSquareSizeJTF);
		smallSquareSizeJTF.getDocument().putProperty("parent", smallSquareSizeJTF);
		
		binaryLevelJTF.getDocument().addDocumentListener(this);
		bigSquareSizeJTF.getDocument().addDocumentListener(this);
		smallSquareSizeJTF.getDocument().addDocumentListener(this);
		
		control.add(binaryLevelJTF);
		control.add(smallSquareSizeJTF);
		control.add(bigSquareSizeJTF);
		
		imageView = new ImageView();
		this.setLayout(new BorderLayout());
		this.getContentPane().add(imageView, BorderLayout.CENTER);
		this.getContentPane().add(control, BorderLayout.SOUTH);
		this.setVisible(true);
	}
	
	/** Lit une image de l'ordinateur avec en spécification si la recherche porte sur des qr codes ou sur des carrés
	 * 
	 * @param fichier
	 */
	public void readImage(File fichier, boolean qrCodesSearch, int binaryLevel, int bigSquare, int smallSquare){
		try {
			COLOR = imageView.setImage(new MyImage(ImageIO.read(fichier)));
			imageWidth = imageView.getImage(COLOR).getWidth();
			imageHeight = imageView.getImage(COLOR).getHeight();
			affiche();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.qrCodesSearch = qrCodesSearch;
		this.changeBinaryLevel(binaryLevel);
		this.binaryLevelJTF.setText(String.valueOf(binaryLevel));
		this.changeBigSquareSize(bigSquare);
		this.bigSquareSizeJTF.setText(String.valueOf(bigSquare));
		this.changeSmallSquareSize(smallSquare);
		this.smallSquareSizeJTF.setText(String.valueOf(smallSquare));
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
		// Lancement de la reconnaissance de forme
		if(e.getSource() == action){
			
			if(wb != null){
					imageView.resetList(true);
					COLOR = imageView.setImage(wb.getImage());
					imageWidth = imageView.getImage(COLOR).getWidth();
					imageHeight = imageView.getImage(COLOR).getHeight();
					affiche();
			}else{
				imageView.resetList(false);
			}
			long startTime = System.currentTimeMillis();
			// Transformation en niveau de gris
			GREY = imageView.setImage(imageView.getImage(COLOR).getGreyMyImage());
			imageView.getImage(GREY).getHistogramme();
			imageView.setImage(imageView.getImage(GREY).getHistogramImage());
			long greyTime = System.currentTimeMillis();
			// Transformation binaire
			BINARY = imageView.setImage(imageView.getImage(GREY).getVarianceFilterImage(3, 5));
			long binaryTime = System.currentTimeMillis();
			
			// Recherche des composantes connexes
			ComponentsAnalyser compoConnex = new ComponentsAnalyser(imageView.getImage(BINARY));
			CONNEXE = imageView.setImage(compoConnex.getCCMyImage());
			long componentTime = System.currentTimeMillis();
			
			if(qrCodesSearch){
				// Recherche des QR codes
				QRCodesAnalyser qrImage = new QRCodesAnalyser(imageView.getImage(GREY), imageView.getImage(BINARY), compoConnex);
				QR_CODE = imageView.setImage(qrImage.getQRCodesImage());
			}else{
				// Recherche des carrés
				SquaresAnalyser squareImage = new SquaresAnalyser(imageView.getImage(BINARY), compoConnex);
				QR_CODE = imageView.setImage(squareImage.getQRCodesImage());
				squareImage.getSquarePosition();
			}
			
			long qrTime = System.currentTimeMillis();
						
			long endTime = System.currentTimeMillis();
			System.out.println("Temps de calcul de la transformation en niveau de gris : " + (greyTime-startTime) + " ms.");
			System.out.println("Temps de calcul de la transformation en binaire : " + (binaryTime-greyTime) + " ms.");
			System.out.println("Temps de calcul pour trouver les composantes connexes: " + (componentTime-binaryTime) + " ms.");
			System.out.println("Temps de calcul pour trouver le qr code : " + (qrTime-componentTime) + " ms.");
			System.out.println("Temps de calcul de la reconnaissance : " + (endTime-startTime) + " ms.");
			
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
		if(e.getDocument().equals(binaryLevelJTF.getDocument())){
			changeBinaryLevel(value);			
		}else if(e.getDocument().equals(bigSquareSizeJTF.getDocument())){
			changeBigSquareSize(value);	
		}else if(e.getDocument().equals(smallSquareSizeJTF.getDocument())){
			changeSmallSquareSize(value);	
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
			MyImage.BINARY_LEVEL = value;
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
