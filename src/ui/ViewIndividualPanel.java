package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JPanel;

import qubject.MediaInterface;
import qubject.Qubject;
import qubject.QubjectModifierInterface;
import qubject.QubjectModifiers;


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
	private final Hashtable<QubjectModifiers, Selector> selectors 
		= new Hashtable<QubjectModifiers, Selector>();
	
	public ViewIndividualPanel(App app) {
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
		add(new JLabel("Options pour :"), c);
		c.gridx = 1;
		add(qubjectSelector = new Selector(app, activeQubject), c);
		
		//TODO : modify the enum to also associate a User-friendly Jlabel with each QubjectModifier !
		//(then it's possible to do a for loop here on QubjectModifiers.values())
		addOption("Sample Associé", QubjectModifiers.sampleWhenPlayed);
		addOption("Effet Axe Y", QubjectModifiers.yAxisModifier);
		addOption("Rotation", QubjectModifiers.rotationModifier);
		addOption("Animation", QubjectModifiers.animationWhenPlayed);
	}
	
	private void addOption(String title, QubjectModifiers modifier){
		c.gridy = qubjectModifiers+2;
		c.gridx = 0;
		add(new JLabel(title), c);
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
	}

	@Override
	public void setActiveProperty(QubjectModifiers modifier) {
		this.activeProperty = modifier;
	}

	@Override
	public void setModifierOfActiveProperty(QubjectModifierInterface modifier) {
		this.selectors.get(activeProperty).setModifier(modifier);		
	}
}
