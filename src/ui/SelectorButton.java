package ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import database.Pattern;
import database.PatternModifierInterface;

public class SelectorButton extends JButton implements ActionListener{
	
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}


}
