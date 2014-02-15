package ui;

import java.util.ArrayList;

import database.Data;
import qubject.Animation;
import qubject.AnimationInterface;
import qubject.SampleInterface;
import qubject.SoundEffectInterface;
public class GlobalController implements GlobalControllerInterface {
	
	private final App app;
	private final Data data;
	
	public GlobalController(App app){
		this.data = new Data();
		this.app=app;
	}

	public App getApp() {
		return app;
	}

	public ArrayList<SampleInterface> getSamples() {
		return data.getSamples();
	}

	public ArrayList<SoundEffectInterface> getSoundEffects() {
		return data.getSoundEffects();
	}

	public ArrayList<AnimationInterface> getAnimations() {
		return data.getAnimations();
	}

	@Override
	public Data getData() {
		return data;
	}

}
