package ui;

import java.awt.Color;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ShadowedJLabel extends JPanel{
	public JLabel label;
	private final Color shadowColor = new Color(0,0,0,85);
	private final Color fontColor = Color.white;
	
	
	public ShadowedJLabel(String text, Color textColor, Color shadow){
		super();
		add (label = new JLabel(text));
		this.setBackground(shadow);
		label.setForeground(textColor);
		tune();
	}
	
	public ShadowedJLabel(String text){
		super();
		add (label = new JLabel(text));
		this.setBackground(shadowColor);
		label.setForeground(fontColor);
		tune();
	}
	
	private void tune(){
		label.setVerticalAlignment(JLabel.CENTER);
		label.setVerticalTextPosition(JLabel.CENTER);
		this.setAlignmentY(CENTER_ALIGNMENT);
	}
	
	public void setText(String text){
		label.setText(text);
	}
	
	public void setIcon(Icon icon){
		label.setIcon(icon);
	}
}