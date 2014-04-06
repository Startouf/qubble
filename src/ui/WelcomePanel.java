package ui;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WelcomePanel extends JPanel
{
	private final App app;
	private final JButton newProject, loadProject;
	
	public WelcomePanel(App app){
		super();
		this.app = app;
		this.add(newProject = new JButton(app.getNewAction()));
		this.add(loadProject = new JButton(app.getLoadAction()));
	}
}
