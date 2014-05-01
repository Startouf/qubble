package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import qubject.MediaInterface;
import qubject.Qubject;
import qubject.QubjectModifierInterface;
import qubject.QubjectProperty;


/**
 * @author Cyril
 * A box that contains: 
 * ¤ A JLabel indicating the object (Qubject or one of its params)
 * ¤ A visual representation of the object
 * ¤ An arrow to show the Palette to select the object
 * 
 * NOTE : object = Qubject or Qubject modifier
 * Maybe this class should be split into 2 classes !
 * 
 * See https://docs.google.com/drawings/d/1NOopF11r3Y56fjHIWkAm6jW3N124hpSTVYJpzmWgv50/edit?usp=sharing
 * For a visual representation
 *
 */
public class Selector extends JPanel {
	
	private final App app;
	//change the specific object (pattern or patternmodifier)
	private JPanel imagePanel;
	private ShadowedJLabel label;
	private boolean isModifier;
	private BufferedImage image;

	/**
	 * Selector for a Qubject
	 * TODO Make it somewhat larger than the Modifiers ?
	 * (Or more distinctive)
	 * @param app
	 * @param qubject
	 */
	public Selector(App app, MediaInterface qubject){
		this.app = app;		
		tune();
		add(label = new ShadowedJLabel(qubject.getName(), Color.white, new Color(0,0,0,85)), BorderLayout.WEST);
		label.setFont(new Font("Cambria", Font.BOLD, 16));
		image = (BufferedImage) qubject.getImage();
		setMaximumSize(new Dimension(250,250));
		imagePanel = new JPanel(){
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);

				if(image != null){
					g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
				}
			}
		};
		imagePanel.setPreferredSize(new Dimension(100,120));
		imagePanel.setVisible(false);
		add(imagePanel);
		add(new SelectorButton(app,qubject), BorderLayout.EAST);
		isModifier = false;
	}

	/**
	 * Selector for a Param
	 * @param app
	 * @param modifier
	 */
	public Selector(App app, MediaInterface qubject, QubjectProperty modifier){
		this.app = app;		
		tune();
		isModifier = true;
		add(label = new ShadowedJLabel(getNameFor(qubject, modifier), Color.white, new Color(0,0,0,85)), BorderLayout.WEST);
		add(new SelectorButton(app, modifier), BorderLayout.EAST);
	}

	private void tune(){
		setPreferredSize(new Dimension(400,40));
		setBorder(BorderFactory.createCompoundBorder(
				new EtchedBorder(), 
				new EmptyBorder(10, 20, 10, 20)));
		setLayout(new BorderLayout());
		this.setBackground(new Color(25,25,25,85));
	}
	
	private String getNameFor(MediaInterface qubject, QubjectProperty selectedParam){
		return qubject.getModifierForProperty(selectedParam).getName();
	}
	
	public void setQubject(MediaInterface qubject){
		label.setText(qubject.getName());
		image = (BufferedImage) qubject.getImage();
	}
	
	public void setModifier(QubjectModifierInterface modifier){
		label.setText(modifier.getName());
		image = (BufferedImage) modifier.getImage();
	}
	
}
