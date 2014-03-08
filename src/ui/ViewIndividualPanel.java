package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

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
	private Qubject currentQubject;
	private int qubjectModifiers = 0;
	private final GridBagConstraints c = new GridBagConstraints();
	private final Selector qubjectSelector; 
	
	public ViewIndividualPanel(App app) {
		this.app = app;
		this.project = app.getActiveProject();
		this.currentQubject = (Qubject) project.getQubjects().get(0);
		
		this.setLayout(new GridBagLayout());
		c.insets = new Insets(10, 20, 10, 20);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 20;

		//Display selected pattern (with test Square)
		c.gridy = 0;
		c.gridx = 0;
		add(new JLabel("Options pour :"), c);
		c.gridx = 1;
		add(qubjectSelector = new Selector(app, "Qubject: "+currentQubject.getName(), currentQubject), c);
		
		//TODO : Replace nulls by default choices (this.app.getXXX.get(0) for example)
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
		add(new Selector(app, modifier), c);
		qubjectModifiers++;
	}

	@Override
	public void setActiveQubject(MediaInterface selectedQubject) {
		currentQubject=(Qubject) selectedQubject;
	}
}
