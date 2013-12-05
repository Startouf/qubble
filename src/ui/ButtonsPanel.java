package ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class ButtonsPanel extends JPanel
{
	private final App app;
	
	public ButtonsPanel(App app)
	{
		super();
		this.app = app;
		
		

		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(512,64));
	}
}
