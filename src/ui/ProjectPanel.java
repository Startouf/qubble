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
	private final JButton switchTo;
	
	private final Color activeColor = Color.green, inactiveColor = Color.red;
	
	public ProjectPanel(App app, ProjectController project){
		this.project = project;
		projectName = new JLabel(project.getProjectName());
		projectName.setHorizontalAlignment(JLabel.LEFT);
		
		status = new JLabel("Active");
		status.setFont(new Font(null, Font.BOLD, 14));
		status.setForeground(activeColor);
		status.setHorizontalAlignment(JLabel.LEFT);
		
		switchTo = new JButton(app.getSwitchActivePojectAction());
		switchTo.setHorizontalAlignment(JLabel.LEFT);
		
		this.add(Box.createRigidArea(new Dimension (100,10)));
		this.add(switchTo);
		this.add(status);
		this.add(Box.createHorizontalGlue());
		this.add(projectName);
		
		this.setSize(700, 50);
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