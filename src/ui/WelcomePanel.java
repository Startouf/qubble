package ui;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class WelcomePanel extends JPanel
{
	private final App app;
	private final JButton newProject, loadProject;
	private final ArrayList<ProjectPanel> projects = new ArrayList<ProjectPanel>();
	private final JPanel main = new JPanel();
	private final JPanel config = new JPanel(); 
	
	public WelcomePanel(App app){
		super();
		this.app = app;
		BorderLayout layout = new BorderLayout();
		layout.setHgap(20);
		layout.setHgap(20);
		this.setLayout(layout);
		
		JPanel shortcuts = new JPanel();
		shortcuts.add(newProject = new JButton(app.getNewAction()));
		shortcuts.add(loadProject = new JButton(app.getLoadAction()));
		this.add(shortcuts, BorderLayout.PAGE_START);
		
		main.setLayout(new BoxLayout(main, BoxLayout.PAGE_AXIS));
		this.add(main, BorderLayout.CENTER);
		
		config.add(new JButton(this.app.getOpenIndividualSettingsAction()));
		config.add(new JButton(this.app.getOpenListSettingsAction()));
		this.add(config, BorderLayout.SOUTH);
	}
	
	public void addProjectEntry(ProjectController project){
		ProjectPanel panel = new ProjectPanel(app, project);
		panel.setBorder(BorderFactory.createEtchedBorder());

		projects.add(panel);
		main.add(panel);
		
		config.setVisible(true);
		
		repaint();
	}
	
	public void refresh(){
		for (ProjectPanel project : projects){
			project.refresh();
		}
	}
	
	public void disableProjects(){
		for (ProjectPanel project : projects){
			project.setStatus(false);
		}
	}
	
	public void removeProject(){
		//TODO
		
		if (projects.size() == 0){
			config.setVisible(false);
		}
	}
}
