package ui;

import java.util.ArrayList;

import qubject.MediaInterface;

public class ProjectController
{
	private final App app;
	private final ArrayList<MediaInterface> qubjects;
	public ProjectController(App app, String path){
		this.app=app;
		
		//TODO: Load Qubjects

		//TODO
		qubjects = new ArrayList<MediaInterface>();
	}

	public App getApp() {
		return app;
	}

	public ArrayList<MediaInterface> getQubjects() {
		return qubjects;
	}
}
