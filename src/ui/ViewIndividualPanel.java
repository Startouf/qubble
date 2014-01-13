package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

import database.Pattern;
import database.PatternModifierInterface;

/**
 * @author Cyril
 * Allows editing settings for a given pattern 
 * 
 * This is the panel that can be seen in :
 * https://docs.google.com/drawings/d/1NOopF11r3Y56fjHIWkAm6jW3N124hpSTVYJpzmWgv50/edit?usp=sharing
 *	
 */
public class ViewIndividualPanel extends JPanel {

	private final App app;
	private Pattern currentPattern;
	private int patternModifiers = 0;
	private final GridBagConstraints c = new GridBagConstraints();
	
	public ViewIndividualPanel(App app) {
		this.app = app;
		this.currentPattern = Pattern.SQUARE;
		
		this.setLayout(new GridBagLayout());
		c.insets = new Insets(10, 20, 10, 20);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 20;

		//Display selected pattern (with test Square)
		c.gridy = 0;
		c.gridx = 0;
		add(new JLabel("Options pour :"), c);
		c.gridx = 1;
		add(new Selector(app, "Pattern", currentPattern), c);
		
		//TODO : Replace nulls by default choices
		addOption("Sample Associé", currentPattern, null);
		addOption("Effet Axe Y", currentPattern, null);
		addOption("Rotation", currentPattern, null);
		addOption("Animation", currentPattern, null);
	}
	
	private void addOption(String title, Pattern pattern, 
		PatternModifierInterface modifier){
		c.gridy = patternModifiers+2;
		c.gridx = 0;
		add(new JLabel(title), c);
		c.gridx = 1;
		add(new Selector(app, pattern, modifier), c);
		patternModifiers++;
	}
}
