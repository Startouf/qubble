package ui;

import java.awt.Color;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ShadowedJLabel extends JPanel{
	public JLabel label;
	private static Color shadowColor = new Color(0,0,0,85);
	private static Color fontColor = Color.white;
	
	public ShadowedJLabel(String text, Color textColor, Color shadow){
		super();
		add (label = new JLabel(text));
		this.setBackground(shadow);
		label.setForeground(textColor);
	}
	
	public ShadowedJLabel(String text){
		super();
		add (label = new JLabel(text));
		this.setBackground(shadowColor);
		label.setForeground(fontColor);
	}
	
	public void setText(String text){
		label.setText(text);
	}
	
	public void setIcon(Icon icon){
		label.setIcon(icon);
	}
}