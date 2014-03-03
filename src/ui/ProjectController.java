package ui;

import java.util.ArrayList;

import qubject.MediaInterface;
import qubject.Qubject;
import sequencer.QubbleInterface;

public class ProjectController implements ProjectControllerInterface
{
	private final App app;
	private final QubbleInterface qubble;
	private String projectName = "New Project";
	
	public ProjectController(App app, QubbleInterface qubble){
		this.app=app;
		this.qubble = qubble;
	}

	public App getApp() {
		return app;
	}

	public ArrayList<Qubject> getQubjects() {
		return qubble.getAllQubjects();
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
		//TODO : change the JLabels of globalPanel + title of tabs
	}

	public QubbleInterface getQubble() {
		return qubble;
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}
}
