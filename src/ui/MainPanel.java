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
	private final App app;
	private final TableViewPanel tableViewPanel;
	private final SettingsPanel settingsPanel;
	private final ButtonsPanel buttonsPanel;
	
	public MainPanel(App app)
	{
		super();
		this.app = app;
		
		this.setPreferredSize(new Dimension(512, 256));
		this.setLayout(new BorderLayout());
		
		this.add(tableViewPanel = new TableViewPanel(app), BorderLayout.CENTER);
		this.add(settingsPanel = new SettingsPanel(app), BorderLayout.NORTH);
		this.add(buttonsPanel = new ButtonsPanel(app), BorderLayout.SOUTH);
	}

	public App getApp() {
		return app;
	}

	public TableViewPanel getTableViewPanel() {
		return tableViewPanel;
	}

	public ButtonsPanel getButtonsPanel() {
		return buttonsPanel;
	}
}
