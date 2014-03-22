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
	
	public GlobalController(App app){
		this.app=app;
	}

	public App getApp() {
		return app;
	}

	public ArrayList<SampleInterface> getSamples() {
		return Data.getSamples();
	}

	public ArrayList<EffectType> getSoundEffects() {
		return Data.getSoundEffects();
	}

	public ArrayList<AnimationInterface> getAnimations() {
		return Data.getAnimations();
	}
}
