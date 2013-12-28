package ui;

import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import database.PatternModifierInterface;
import table.Pattern;

public class SelectorButton extends JButton {
	
	//Constructor for the select pattern selector
	public SelectorButton(Pattern pattern) {
		super(new ImageIcon("data/ui/arrow.png"));
		//TODO : add action listener with the pattern
		setPreferredSize(new Dimension(35,35));
	}
	
	//Constructor for the select modifier selector
	public SelectorButton(Pattern pattern, PatternModifierInterface modifier){
		super(new ImageIcon("data/ui/arrow.png"));
		//TODO : add action listener with the pattern modifier
		setPreferredSize(new Dimension(35,35));
	}


}
