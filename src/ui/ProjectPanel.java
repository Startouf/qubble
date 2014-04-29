package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ProjectPanel extends JPanel {
	private final ProjectController project;
	private final JLabel projectName;
	private final JLabel status;
	private final JButton switchTo, remove;
	
	private final Color activeColor = Color.green, inactiveColor = Color.red;
	
	public ProjectPanel(App app, ProjectController project){
		this.project = project;
		this.setOpaque(false);
		projectName = new JLabel(project.getProjectName());
		projectName.setHorizontalAlignment(JLabel.LEFT);
		
		status = new JLabel("Actif");
		status.setFont(new Font(null, Font.BOLD, 14));
		status.setForeground(activeColor);
		status.setHorizontalAlignment(JLabel.LEFT);
		
		switchTo = new JButton(app.getSwitchActivePojectAction());
		switchTo.setHorizontalAlignment(JLabel.LEFT);
		
		remove = new ReferenceButton(this, app.getCloseProjectAction());
		
		this.add(status);
		this.add(Box.createHorizontalGlue());
		this.add(projectName);
		this.add(Box.createHorizontalGlue());
		this.add(switchTo);
		this.add(remove);
		
		this.setSize(700, 50);
	}
	
	public void refresh(){
		projectName.setText(project.getProjectName());
		repaint();
	}
	
	public void setStatus(boolean active){
		if(active){
			status.setForeground(activeColor);
			status.setText("Actif");
		} else{
			status.setForeground(inactiveColor);
			status.setText("Inactif");
		}
		repaint();
	}
	
	public ProjectController getProject(){
		return this.project;
	}
}
