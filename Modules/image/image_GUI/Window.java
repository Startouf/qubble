package image_GUI;

import imageObject.ConnexeComponent;
import imageTransform.TabImage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
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

import main.ImageDetectionInterface;
import motionEstimation.MotionEstimation;
import qrDetection.ComponentsAnalyser;
import qrDetection.QRCodesAnalyser;
import qrDetection.QR_Detection;

/**
 * Affiche une fenetre pour suivre la réception de la caméra, la détection de forme et le mouvement
 * @author masseran
 *
 */
public class Window extends JFrame implements ActionListener, DocumentListener{
	
	private QR_Detection controlDetection;
	private MotionEstimation controlMotion;
	private ImageDetectionInterface detectionInterface;
		
	public static int imageWidth, imageHeight;
	private ImageView imageView;
	// Barre de contrôle du bas
	private JPanel control;
	private JButton suivant, action, precedent;
	private JTextField binaryLevelJTF, squareSizeJTF, squareTriggerJTF;
	// Menu 
	private JMenuBar mainMenu;
	private JMenu fichier, display, run;
	private JMenuItem ouvrir, motion, measure, motionRun, qrRun;
	
	private File currentFolder;
	
	
	public Window(QR_Detection controlDetection, MotionEstimation controlMotion, int binary, int rayon, int square){
		this.controlDetection = controlDetection;
		this.controlMotion = controlMotion;
		init(binary, rayon, square);
	}
	
	/**
	 * Initialisation de la fenêtre et création des composants
	 */
	private void init(int binary, int rayon, int square){
		this.setSize(800, 600);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setTitle("Détection et mouvement");
		currentFolder = null;
		// Menu
		mainMenu = new JMenuBar();
		fichier = new JMenu("Fichier");
		ouvrir = new JMenuItem("Ouvrir");
		ouvrir.addActionListener(this);
		
		display = new JMenu("Affichage");
		motion = new JMenuItem("Mouvement");
		motion.addActionListener(this);
		measure = new JMenuItem("Règle");
		measure.addActionListener(this);
		
		run = new JMenu("Exécution");
		motionRun = new JMenuItem("Mouvement");
		motionRun.addActionListener(this);
		qrRun = new JMenuItem("Detection QR");
		qrRun.addActionListener(this);
		
		
		fichier.add(ouvrir);
		display.add(motion);
		display.add(measure);
		run.add(motionRun);
		run.add(qrRun);
		mainMenu.add(fichier);
		mainMenu.add(display);
		mainMenu.add(run);
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
		control.add(new JLabel("Binarisation (0-255)"));
		control.add(new JLabel("Détection du carré (0-100)"));
		control.add(new JLabel("Taille rayon"));
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
		
		control.add(binaryLevelJTF);
		control.add(squareTriggerJTF);
		control.add(squareSizeJTF);
		
		imageView = new ImageView();
		this.setLayout(new BorderLayout());
		this.getContentPane().add(imageView, BorderLayout.CENTER);
		this.getContentPane().add(control, BorderLayout.SOUTH);
		this.setVisible(true);
	}
	
	/**
	 * Lis une image sur le disque dur puis effectue la détection des composantes connexes
	 * @param fichier
	 */
	public void readImage(File fichier){
		try {
			BufferedImage loadImage = ImageIO.read(fichier);
			controlDetection.analyseTable(new TabImage(loadImage));
			displayQRDetection(loadImage, controlDetection.getGrey(), controlDetection.getVariance(), controlDetection.getCompo(), controlDetection.getQrAnal());
			
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
		// Actualiser la visualisation de la dernière reconnaissance de forme
		if(e.getSource() == action){
			displayQRDetection(controlDetection.getLastDetection(), controlDetection.getGrey(), controlDetection.getVariance(), controlDetection.getCompo(), controlDetection.getQrAnal());
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
		        	// Ecole
		        	currentFolder = new File("/cal/homes/masseran/EclipseWorkspace/Pact/Modules/image/database/test").getCanonicalFile();
		        	// Portable Dell
		        	//currentFolder = new File("/home/eric/workspace/java/PACT/Modules/image/database/test").getCanonicalFile();
		        	// Portable Lenovo
		        	//currentFolder = new File("/home/eric/workspace/Pact/Modules/image/database/test").getCanonicalFile();
		        } catch(IOException err) {
					currentFolder = new File(".");
					
		        }
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
		if(e.getSource() == qrRun){
			controlDetection.switchPause();
		}
		if(e.getSource() == motionRun){
			controlMotion.switchPause();
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
		}else if(e.getDocument().equals(squareSizeJTF.getDocument())){
			changeSquareSize(value);	
		}else if(e.getDocument().equals(squareTriggerJTF.getDocument())){
			changeSquareTrigger(value);	
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
	
	public void displayQRDetection(BufferedImage lastDetection, TabImage grey, TabImage variance, ComponentsAnalyser compo, QRCodesAnalyser qrAnal){
		// Supprime les images en cours
		imageView.resetList();
		
		imageView.setLastDetectionImage(lastDetection);
		imageView.addImage(grey.ColorArrayToBufferedImage());
		imageView.addImage(variance.ColorArrayToBufferedImage());
		imageView.addImage(compo.getCCMyImage());
		imageView.addImage(qrAnal.getQRCodesImage());
		affiche();
	}
	
	public void displayCamera(BufferedImage camera){
		BufferedImage cameraToPrint = new BufferedImage(camera.getWidth(), camera.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = cameraToPrint.getGraphics();
		g.drawImage(camera, 0, 0, null);
		
		g.setColor(Color.red);
		
		g.drawLine(100, 100, 120, 100);
		g.drawLine(100, 200, 160, 200);
		g.drawLine(100, 300, 200, 300);
		
		//controlMotion.addMotionOnImage(cameraToPrint);
		
		imageView.setCameraImage(cameraToPrint);
		affiche();
	}


}
