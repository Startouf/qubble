package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

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
	private final JTabbedPane settingsTabs;	//Utilisation d'onglets
	private final GlobalSettingsPanel globalSettingsPanel;
	
	public MainPanel(App app)
	{
		super();
		this.app = app;
		
		this.setPreferredSize(new Dimension(650, 600));
		this.setLayout(new BorderLayout());

		//Tabs are added by the app
		add(settingsTabs = new JTabbedPane(), BorderLayout.CENTER);
		add(globalSettingsPanel = new GlobalSettingsPanel(app), BorderLayout.SOUTH);
	}

	public App getApp() {
		return app;
	}

	public void addTab(String title, JPanel tab){
		settingsTabs.addTab(title, tab);
		repaint();
	}

	public JTabbedPane getSettingsTabs() {
		return settingsTabs;
	}

	public GlobalSettingsPanel getGlobalSettingsPanel() {
		return globalSettingsPanel;
	}
	
	
}
