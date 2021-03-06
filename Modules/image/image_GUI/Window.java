package image_GUI;

import imageObject.ConnexeComponent;
import imageObject.Point;
import imageTransform.TabImage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

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
import motionEstimation.MotionDetection;
import qrDetection.ComponentsAnalyser;
import qrDetection.QRCodesAnalyser;
import qrDetection.QR_Detection;


// Ajouter un choix pour masquer le contrôle de paramètre

/**
 * Affiche une fenetre pour suivre la réception de la caméra, la détection de forme et le mouvement
 * @author masseran
 * 
 */
public class Window extends JFrame implements ActionListener, MouseListener{
	
	private QR_Detection controlDetection;
	private MotionDetection controlMotion;
	private ImageDetectionInterface detectionInterface;
		
	public static int imageWidth, imageHeight;
	private ImageView imageView;
	// Barre de contrôle du bas
	private JPanel control;

	// Menu 
	private JMenuBar mainMenu;
	private JMenu fichier, display, camera, run;
	private JMenuItem ouvrir, motion, measure, webcam0, webcam1, motionRun, qrRun, cameraRun;
	
	private File currentFolder;
	
	private boolean printMeasure, printMotion;
	
	public Window(ImageDetectionInterface detectionInterface, QR_Detection controlDetection, MotionDetection controlMotion, int binary, int rayon, int square, int varWindow, int varThresh, int nothing){
		this.detectionInterface = detectionInterface;
		this.controlDetection = controlDetection;
		this.controlMotion = controlMotion;
		init(binary, rayon, square, varWindow, varThresh, nothing);
		printMeasure = false;
		printMotion = false;
	}
	
	/**
	 * Initialisation de la fenêtre et création des composants
	 */
	private void init(int binary, int rayon, int square, int varWindow, int varThresh, int nothing){
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
		
		camera = new JMenu("Caméra");
		webcam0 = new JMenuItem("Webcam 0");
		webcam0.addActionListener(this);
		webcam1 = new JMenuItem("Webcam 1");
		webcam1.addActionListener(this);
		
		run = new JMenu("Exécution");
		cameraRun = new JMenuItem("Camera");
		cameraRun.addActionListener(this);
		motionRun = new JMenuItem("Mouvement");
		motionRun.addActionListener(this);
		qrRun = new JMenuItem("Detection QR");
		qrRun.addActionListener(this);
		
		
		fichier.add(ouvrir);
		display.add(motion);
		display.add(measure);
		camera.add(webcam0);
		camera.add(webcam1);
		run.add(cameraRun);
		run.add(qrRun);
		run.add(motionRun);
		mainMenu.add(fichier);
		mainMenu.add(display);
		mainMenu.add(camera);
		mainMenu.add(run);
		this.setJMenuBar(mainMenu);
				
		imageView = new ImageView();
		imageView.addMouseListener(this);
		control = new ParamControl(this, controlDetection, imageView, binary, rayon, square, varWindow, varThresh, nothing);
		this.setLayout(new BorderLayout());
		this.getContentPane().add(imageView, BorderLayout.CENTER);
		this.getContentPane().add(control, BorderLayout.SOUTH);
		this.setVisible(true);
		System.out.println(imageView.getHeight());
	}
	
	/**
	 * Lis une image sur le disque dur puis effectue la détection des composantes connexes
	 * @param fichier
	 */
	public void readImage(File fichier){
		try {
			BufferedImage loadImage = ImageIO.read(fichier);
			controlDetection.analyseTable(new TabImage(loadImage));
			displayCamera(loadImage);
			displayQRDetection(loadImage, controlDetection.getGrey(), controlDetection.getVariance(), controlDetection.getCompo(), controlDetection.getQrAnal(), controlDetection.getPattern());
			
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
		if(e.getSource() == cameraRun){
			detectionInterface.switchCamera();
		}
		if(e.getSource() == motion){
			if(printMotion){
				printMotion = false;
			}else
				printMotion = true;
		}
		if(e.getSource() == measure){
			if(printMeasure){
				printMeasure = false;
			}else
				printMeasure = true;
		}
		affiche();
	}

	
	public void displayQRDetection(BufferedImage lastDetection, TabImage grey, TabImage variance, ComponentsAnalyser compo, QRCodesAnalyser qrAnal, BufferedImage Pattern){
		// Supprime les images en cours
		imageView.resetList();
		
		imageView.setLastDetectionImage(lastDetection);
		imageView.addImage(grey.ColorArrayToBufferedImage());
		imageView.addImage(variance.ColorArrayToBufferedImage());
		imageView.addImage(compo.getCCMyImage());
		imageView.addImage(qrAnal.getQRCodesImage());
		imageView.addImage(Pattern);
		affiche();
	}
	
	public void displayCamera(BufferedImage camera){
		BufferedImage cameraToPrint = new BufferedImage(camera.getWidth(), camera.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = cameraToPrint.getGraphics();
		g.drawImage(camera, 0, 0, null);
		
		if(printMeasure){
			g.setColor(Color.red);
			
			g.drawLine(100, 100, 120, 100);
			g.drawLine(100, 200, 160, 200);
			g.drawLine(100, 300, 200, 300);
		}
		
		if(printMotion){
			controlMotion.addMotionOnImage(cameraToPrint);
		}
		
		imageView.setCameraImage(cameraToPrint);
		affiche();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		controlMotion.addQubbleToList((int)(e.getX()*1.6), (int)(e.getY()*1.2), 888);
		System.out.println("X : " + (int)(e.getX()*1.4) + "//" + "Y : " + (int)(e.getY()*1.24));
	}

	@Override
	public void mouseEntered(MouseEvent e) {	}

	@Override
	public void mouseExited(MouseEvent e) {		}

	@Override
	public void mousePressed(MouseEvent e) {	
	}

	@Override
	public void mouseReleased(MouseEvent e) {	}
	
	/**
	 * Effectuer qu'une seule fois la reconnaissance sur l'image de la caméra en cours
	 */
	public void oneShot(){
		controlDetection.analyseTable(new TabImage(detectionInterface.getLastImage()));
		displayQRDetection(controlDetection.getLastDetection(), controlDetection.getGrey(), controlDetection.getVariance(), controlDetection.getCompo(), controlDetection.getQrAnal(), controlDetection.getPattern());
	}

}
