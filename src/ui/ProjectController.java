package ui;

import java.util.ArrayList;

import qubject.MediaInterface;
import qubject.Qubject;
import sequencer.QubbleInterface;

public class ProjectController implements ProjectControllerInterface
{
	private final App app;
	private final QubbleInterface qubble;
	
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
}
