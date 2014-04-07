package ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ProjectPanel extends JPanel {
	private final ProjectController project;
	private final JLabel projectName;
	private final JLabel status;
	private final JButton switchTo;
	
	private final Color activeColor = Color.green, inactiveColor = Color.red;
	
	public ProjectPanel(App app, ProjectController project){
		this.project = project;
		this.projectName = new JLabel(project.getProjectName());
		
		this.status = new JLabel("Active");
		status.setFont(new Font(null, Font.BOLD, 14));
		status.setForeground(activeColor);
		
		this.switchTo = new JButton(app.getSwitchActivePojectAction());
		
		this.add(switchTo);
		this.add(status);
		this.add(projectName);
		
		this.setSize(500, 50);
	}
	
	public void refresh(){
		projectName.setText(project.getProjectName());
		repaint();
	}
	
	public void setStatus(boolean active){
		if(active){
			status.setForeground(activeColor);
			status.setText("Active");
		} else{
			status.setForeground(inactiveColor);
			status.setText("Inactive");
		}
		repaint();
	}
}
