package ui;

import java.awt.BorderLayout;
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
	private TableViewPanel tableViewPanel;
	private ButtonsPanel buttonsPanel;
	
	public MainPanel(App app)
	{
		super();
		
		this.setPreferredSize(new Dimension(512, 256));
		this.setLayout(new BorderLayout());
		
		this.add(tableViewPanel = new TableViewPanel(this.app), BorderLayout.CENTER);
		this.add(buttonsPanel = new ButtonsPanel(this.app), BorderLayout.SOUTH);
		
		this.app = app;
	}
}
