package ui;

import java.awt.Dimension;

import javax.swing.JPanel;

/**
 * @author Cyril
 * The SuperPanel which contains the interesting sub-panels:
 * -> The TableViewPanel which displays elements(cubes, triangles...) on the table
 * -> A panel with useful buttons (adjust volume, turn off visual effects...)
 * -> ... Other stuff ?
 *
 */
public class MainPanel extends JPanel
{
	private App app;
	
	public MainPanel(App app)
	{
		super();
		
		this.setPreferredSize(new Dimension(256, 256));
		
		this.app = app;
	}
}
