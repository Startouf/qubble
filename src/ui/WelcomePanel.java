package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
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
	private final JPanel config = new JPanel(), shortcutsBox, shortcuts; 
	
	public WelcomePanel(App app){
		super();
		this.app = app;
		BorderLayout layout = new BorderLayout();
		layout.setHgap(20);
		layout.setHgap(20);
		this.setLayout(layout);
//		this.setOpaque(false);
		
		shortcutsBox = new JPanel();
		BorderLayout lay = new BorderLayout();
		lay.setVgap(15);
		shortcutsBox.setLayout(lay);
		shortcuts = new JPanel();
		shortcuts.add(newProject = new JButton(app.getNewAction()));
		shortcuts.add(loadProject = new JButton(app.getLoadAction()));
		shortcuts.add(saveProject = new JButton(app.getSaveProjectAction()));
		shortcutsBox.add(shortcuts, BorderLayout.NORTH);
		JLabel label = new JLabel("Liste des projets ouverts :");
		label.setHorizontalAlignment(JLabel.CENTER);
		shortcutsBox.add(label, BorderLayout.SOUTH);
		this.add(shortcutsBox, BorderLayout.PAGE_START);
		
		main.setOpaque(false);
//		main.setMinimumSize(new Dimension(0, 300));
		main.setLayout(new BoxLayout(main, BoxLayout.PAGE_AXIS));
		this.add(main, BorderLayout.CENTER);
		
		config.add(new JButton(this.app.getOpenIndividualSettingsAction()));
		config.add(new JButton(this.app.getOpenListSettingsAction()));
		this.add(config, BorderLayout.SOUTH);
		
		hideComponentsWhenNoProject();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if(MainPanel.backgroundImage != null){
			BufferedImage bf = MainPanel.backgroundImage;
			float scale = (float)bf.getHeight()/(float)this.getHeight();
			BufferedImage dest = MainPanel.backgroundImage.getSubimage(0, 
					(int) (shortcutsBox.getHeight()*scale), 
					bf.getWidth(), 
					bf.getHeight()-(int) (shortcutsBox.getHeight()*scale)-1);
			g.drawImage(dest, 0, 0, getWidth(), getHeight(), this);
		}
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
		shortcuts.setVisible(true);
	}
	
	private void showComponentsWhenProjectOpened(){
		saveProject.setVisible(true);
		config.setVisible(true);
		shortcuts.setVisible(false);
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
