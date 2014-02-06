package ui;

import java.util.ArrayList;

import audio.SampleInterface;
import audio.SoundEffectInterface;
import database.Animation;
import database.PatternInterface;

public class ProjectController
{
	private final App app;
	private final ArrayList<PatternInterface> patterns;
	public ProjectController(App app, String path){
		this.app=app;
		
		//TODO: Load Qubjects

		//TODO
		patterns = new ArrayList<PatternInterface>();
	}

	public App getApp() {
		return app;
	}

	public ArrayList<PatternInterface> getPatterns() {
		return patterns;
	}
}
