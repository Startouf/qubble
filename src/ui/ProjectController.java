package ui;

import java.util.ArrayList;

import qubject.Qubject;
import sequencer.QubbleInterface;
import database.SaveProject;

public class ProjectController implements ProjectControllerInterface
{
	private final App app;
	private final QubbleInterface qubble;
	private String projectName = "Nouveau projet";
	
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
	}

	public QubbleInterface getQubble() {
		return qubble;
	}

	@Override
	public void save(String path) {
		SaveProject.saveTo(path, qubble);
	}

	@Override
	public void close() {
		this.qubble.close();
	}

	@Override
	public void panic() {
		this.qubble.panic();
	}
}
