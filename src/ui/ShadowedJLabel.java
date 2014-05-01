package ui;

import java.awt.Color;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ShadowedJLabel extends JPanel{
	public JLabel label;
	public ShadowedJLabel(String text, Color textColor, Color shadow){
		super();
		add (label = new JLabel(text));
		this.setBackground(shadow);
		label.setForeground(textColor);
	}
	
	public void setText(String text){
		label.setText(text);
	}
	
	public void setIcon(Icon icon){
		label.setIcon(icon);
	}
}