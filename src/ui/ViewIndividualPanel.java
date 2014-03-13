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
		
		//Display Current position : NOT POSSIBLE WITH MEDIAINTERFACE !!!
		c.gridy=1;
		c.gridx=0;
		add(new JLabel("Position : "));
		qubjectPosition = new JLabel(this.app.getActiveProject().getQubble().whereIsIt(activeQubject));
		c.gridx = 1;
		add(qubjectPosition);
		
		//TODO : modify the enum to also associate a User-friendly Jlabel with each QubjectModifier !
		//(then it's possible to do a for loop here on QubjectModifiers.values())
		addOption("Sample Associé", QubjectProperty.SAMPLE_WHEN_PLAYED);
		addOption("Effet Axe Y", QubjectProperty.AUDIO_EFFECT_Y_AXIS);
		addOption("Rotation", QubjectProperty.AUDIO_EFFECT_ROTATION);
		addOption("Animation quand joué", QubjectProperty.ANIM_WHEN_PLAYED);
		addOption("Animation quand posé", QubjectProperty.ANIM_WHEN_DETECTED);
	}
	
	private void addOption(String title, QubjectProperty modifier){
		c.gridy = qubjectModifiers+3;
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
		qubjectPosition.setText(this.app.getActiveProject().getQubble().whereIsIt(activeQubject));
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
		this.selectors.get(activeProperty).setModifier(modifier);		
	}
}
