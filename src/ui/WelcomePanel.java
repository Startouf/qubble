package ui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class WelcomePanel extends JPanel
{
	private final App app;
	private final JButton newProject, loadProject, saveProject;
	private final Hashtable<ProjectController, ProjectPanel> projects 
		= new Hashtable<ProjectController, ProjectPanel>();
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
		shortcuts.add(saveProject = new JButton(app.getSaveProjectAction()));
		this.add(shortcuts, BorderLayout.PAGE_START);
		
		main.setLayout(new BoxLayout(main, BoxLayout.PAGE_AXIS));
		this.add(main, BorderLayout.CENTER);
		
		config.add(new JButton(this.app.getOpenIndividualSettingsAction()));
		config.add(new JButton(this.app.getOpenListSettingsAction()));
		this.add(config, BorderLayout.SOUTH);
		
		hideComponentsWhenNoProject();
	}
	
	public void addProjectEntry(ProjectController project){
		ProjectPanel panel = new ProjectPanel(app, project);
		panel.setBorder(BorderFactory.createEtchedBorder());

		projects.put(project, panel);
		main.add(panel);
		
		showComponentsWhenProjectOpened();
		repaint();
	}
	
	private void hideComponentsWhenNoProject(){
		saveProject.setVisible(false);
		config.setVisible(false);
	}
	
	private void showComponentsWhenProjectOpened(){
		saveProject.setVisible(true);
		config.setVisible(true);
	}
	
	public void refresh(){
		for (ProjectPanel project : projects.values()){
			project.refresh();
		}
	}
	
	public void disableProjects(){
		for (ProjectPanel project : projects.values()){
			project.setStatus(false);
		}
	}
	
	public void removeProject(ProjectController project){
		main.remove(projects.get(project));
		projects.remove(project);
		
		if (projects.size() == 0){
			hideComponentsWhenNoProject();
		}
	}
}
