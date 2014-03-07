package image_GUI;

import imageTransform.ComponentAnalyser;
import imageTransform.MyImage;
import imageTransform.QRCodesAnalyser;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Container;
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

/**
 * Affiche une fenetre
 * @author masseran
 *
 */
public class Window extends JFrame implements ActionListener{
	
	public static int imageWidth, imageHeight;
	private ImageView imageView;
	private JPanel control;
	private JButton suivant, action, precedent;
	
	public Window(){
		this.setSize(800, 600);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setTitle("Reconnaissance de QR Code");
		
		control = new JPanel();
		control.setLayout(new GridLayout(1, 3));
		
		precedent = new JButton("Précédent");
		action = new JButton("Transformer !");
		suivant = new JButton("Suivant");
		precedent.addActionListener(this);
		action.addActionListener(this);
		suivant.addActionListener(this);
		
		control.add(precedent);
		control.add(action);
		control.add(suivant);
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
	


}
