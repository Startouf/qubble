package image_GUI;

import imageTransform.ComponentsAnalyser;
import imageTransform.QRCodesAnalyser;
import imageTransform.TabImage;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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
	// Barre de contrôle du bas
	private JPanel control;
	private JButton suivant, action, precedent;
	private JTextField binaryLevelJTF, bigSquareSizeJTF, smallSquareSizeJTF;
	// Menu 
	private JMenuBar mainMenu;
	private JMenu fichier;
	private JMenuItem ouvrir;
	
	private File currentFolder;
	
	private boolean qrCodesSearch;
	private Camera wb;
	
	// Utiliser des images enregistrées depuis l'ordinateur 
	public Window(){
		init();
	}
	
	// Utiliser les images de la caméra
	public Window(Camera wb){
		init();
		this.wb = wb;
	}
	
	/**
	 * Initialisation de la fenêtre et création des composants
	 */
	private void init(){
		this.setSize(800, 600);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setTitle("Reconnaissance de QR Code");
		currentFolder = null;
		// Menu
		mainMenu = new JMenuBar();
		fichier = new JMenu("Fichier");
		ouvrir = new JMenuItem("Ouvrir");
		
		ouvrir.addActionListener(this);
		fichier.add(ouvrir);
		mainMenu.add(fichier);
		this.setJMenuBar(mainMenu);
		
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
		binaryLevelJTF = new JTextField(String.valueOf(TabImage.BINARY_LEVEL));
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
	 *  Mise à jour des paramètres de seuil
	 * @param fichier
	 */
	public void readImage(File fichier, boolean qrCodesSearch, int binaryLevel, int bigSquare, int smallSquare){
		try {
			COLOR = imageView.setImage(ImageIO.read(fichier));
			imageWidth = imageView.getImage(COLOR).getWidth();
			imageHeight = imageView.getImage(COLOR).getHeight();
			affiche();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Mise à jour des valeurs de seuil
		this.qrCodesSearch = qrCodesSearch;
		this.changeBinaryLevel(binaryLevel);
		this.binaryLevelJTF.setText(String.valueOf(binaryLevel));
		this.changeBigSquareSize(bigSquare);
		this.bigSquareSizeJTF.setText(String.valueOf(bigSquare));
		this.changeSmallSquareSize(smallSquare);
		this.smallSquareSizeJTF.setText(String.valueOf(smallSquare));
	}
	
	/**
	 * Lis une image pour pouvoir l'analyser
	 * @param fichier
	 */
	public void readImage(File fichier){
		try {
			imageView.resetList(false);
			COLOR = imageView.setImage(ImageIO.read(fichier));
			imageWidth = imageView.getImage(COLOR).getWidth();
			imageHeight = imageView.getImage(COLOR).getHeight();
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
		// Lancement de la reconnaissance de forme
		if(e.getSource() == action){
			
			if(wb != null){
					imageView.resetList(false);
					COLOR = imageView.setImage(wb.getImage());
					imageWidth = imageView.getImage(COLOR).getWidth();
					imageHeight = imageView.getImage(COLOR).getHeight();
					affiche();
			}else{
				imageView.resetList(true);
			}
			long startTime = System.currentTimeMillis();
			// Transformation en niveau de gris
			TabImage image = new TabImage(imageView.getImage(COLOR));
			TabImage grey = image.getGrey();

			long greyTime = System.currentTimeMillis();
			// Transformation par le filtre de variance
			TabImage variance = grey.getVarianceFilter(3, 5);
			
			long binaryTime = System.currentTimeMillis();
			
			// Recherche des composantes connexes
			ComponentsAnalyser compoConnex = new ComponentsAnalyser(variance);
			long componentTime = System.currentTimeMillis();
			
			QRCodesAnalyser qrImage = new QRCodesAnalyser(grey, variance, compoConnex);
			
			long qrTime = System.currentTimeMillis();
						
			long endTime = System.currentTimeMillis();
			
			GREY = imageView.setImage(grey.ColorArrayToBufferedImage());
			BINARY = imageView.setImage(variance.ColorArrayToBufferedImage());
			CONNEXE = imageView.setImage(compoConnex.getCCMyImage());
			QR_CODE = imageView.setImage(qrImage.getQRCodesImage());
			
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
		if(e.getSource() == ouvrir){
	        // Boîte de sélection de fichier à partir du répertoire courant
			if(currentFolder == null){
		        try {
		            // obtention d'un objet File qui désigne le répertoire courant. Le
		            // "getCanonicalFile" : meilleurs formatage
		        	currentFolder = new File(".").getCanonicalFile();
		        } catch(IOException err) {}
			}
	         
	        // création de la boîte de dialogue dans ce répertoire courant
	        JFileChooser dialogue = new JFileChooser(currentFolder);
	         
	        // affichage
	        dialogue.showOpenDialog(null);
	        // récupération du fichier sélectionné
	        if(dialogue.getSelectedFile() != null){
	        	File selected = dialogue.getSelectedFile();
	        	System.out.println("Fichier choisi : " + selected);
		        // Mémorisation du dossier en cours
		        currentFolder = new File(selected.getParent());
		        readImage(selected);
	        }
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
			TabImage.BINARY_LEVEL = value;
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
