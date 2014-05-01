package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import qubject.MediaInterface;
import qubject.Qubject;
import qubject.QubjectModifierInterface;
import qubject.QubjectProperty;


/**
 * @author Cyril
 * Allows editing settings for a given pattern 
 * 
 * This is the panel that can be seen in :
 * https://docs.google.com/drawings/d/1NOopF11r3Y56fjHIWkAm6jW3N124hpSTVYJpzmWgv50/edit?usp=sharing
 *	
 * TODO : Better design
 * TODO : Close tab button
 */
public class ViewIndividualPanel extends ViewQubjects {

	private final App app;
	private final ProjectController project;
	private int qubjectModifiers = 0;
	private final GridBagConstraints c = new GridBagConstraints();
	private final Selector qubjectSelector; 
	private final Hashtable<QubjectProperty, Selector> selectors 
		= new Hashtable<QubjectProperty, Selector>();
	private final JLabel qubjectPosition;
	
	public static final BufferedImage backgroundImage;
	
	static{
		BufferedImage tryImage = null;
		try {
			tryImage = ImageIO.read(new FileInputStream("data/ui/fond 1.png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		backgroundImage = tryImage;
	}
	
	public ViewIndividualPanel(App app) {
		super(app.getActiveProject());
		this.app = app;
		this.project = app.getActiveProject();
		this.activeQubject = (Qubject) project.getQubjects().get(0);
		
		this.setLayout(new GridBagLayout());
		c.insets = new Insets(10, 20, 10, 20);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 20;

		//Display selected Qubject
		c.gridy = 0;
		c.gridx = 0;
		add(new ShadowedJLabel("Options pour :"), c);
		c.gridx = 1;
		add(qubjectSelector = new Selector(app, activeQubject), c);
		
		//Display Current position : NOT POSSIBLE WITH MEDIAINTERFACE !!!
		c.gridy=1;
		c.gridx=0;
		add(new ShadowedJLabel("Position : "));
		Dimension dim = this.app.getActiveProject().getQubble().getPosition(activeQubject);
		qubjectPosition = new JLabel("t = " + dim.getWidth() + " Intensité = " + dim.getHeight());
		c.gridx = 1;
		add(qubjectPosition);
		
		for(QubjectProperty prop : QubjectProperty.values()){
			addOption(prop.getUserFriendlyString(), prop);
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if(this.backgroundImage != null){
			BufferedImage bf = this.backgroundImage;
			float scale = (float)bf.getHeight()/(float)this.getHeight();
			BufferedImage dest = this.backgroundImage.getSubimage(0, 
					0, 
					bf.getWidth(), 
					bf.getHeight());
			g.drawImage(dest, 0, 0, getWidth(), getHeight(), this);
		}
	}
	
	private void addOption(String title, QubjectProperty modifier){
		c.gridy = qubjectModifiers+3;
		c.gridx = 0;
		add(new ShadowedJLabel(title, Color.white, new Color(0,0,0,85)), c);
		c.gridx = 1;
		Selector selector = new Selector(app, activeQubject, modifier);
		add(selector, c);
		qubjectModifiers++;
		selectors.put(modifier, selector);
	}

	@Override
	public void setActiveQubject(MediaInterface selectedQubject) {
		activeQubject= selectedQubject;
		qubjectSelector.setQubject(selectedQubject);
		Dimension dim = this.app.getActiveProject().getQubble().getPosition(activeQubject);
		qubjectPosition.setText("t = " + dim.getWidth() + " Intensité = " + dim.getHeight());
		for (QubjectProperty property : QubjectProperty.values()){
			this.selectors.get(property).setModifier(selectedQubject.getModifierForProperty(property));
		}
	}

	@Override
	public void setActiveProperty(QubjectProperty property) {
		this.activeProperty = property;
	}

	@Override
	public void setModifierOfActiveProperty(QubjectModifierInterface modifier) {
		this.activeModifier = modifier;
		this.selectors.get(activeProperty).setModifier(modifier);		
	}

	@Override
	public void setConfigForQubject(MediaInterface qubject, QubjectProperty prop,
			QubjectModifierInterface modifier) {
		// TODO Auto-generated method stub
		
	}
}
