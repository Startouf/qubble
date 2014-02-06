package ui;

import java.util.ArrayList;

import qubject.Animation;
import qubject.SampleInterface;
import qubject.SoundEffectInterface;
public class GlobalController implements GlobalControllerInterface {
	
	private final App app;
	private final ArrayList<SampleInterface> samples = new ArrayList<SampleInterface>();
	private final ArrayList<SoundEffectInterface> soundEffects = new ArrayList<SoundEffectInterface>();
	private final ArrayList<Animation> animations = new ArrayList<Animation>();
	
	public GlobalController(App app){
		this.app=app;
	}

	public App getApp() {
		return app;
	}

	public ArrayList<SampleInterface> getSamples() {
		return samples;
	}

	public ArrayList<SoundEffectInterface> getSoundEffects() {
		return soundEffects;
	}

	public ArrayList<Animation> getAnimations() {
		return animations;
	}

}
