package ui;

import java.util.ArrayList;

import audio.EffectType;
import audio.SoundEffectInterface;
import database.Data;
import qubject.Animation;
import qubject.AnimationInterface;
import qubject.SampleInterface;
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

	public ArrayList<EffectType> getSoundEffects() {
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
