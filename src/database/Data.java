package database;

import java.io.File;
import java.util.ArrayList;

import audio.SoundEffectInterface;
/**
 * Holds the assets of Qubble
 * 
 * @author duchon
 *
 */
public class Data implements DataInterface
{
	private ArrayList<Qubject> qubjects = new ArrayList<Qubject>();
	private ArrayList<SampleInterface> samples = new ArrayList<SampleInterface>();
	private ArrayList<SoundEffectInterface> soundEffects = new ArrayList<SoundEffectInterface>();
	private ArrayList<AnimationInterface> animations = new ArrayList<AnimationInterface>();

	public Data(){
		super();
	}
	
	@Override
	public ArrayList<Qubject> getQubjects() {
		return qubjects;
	}

	@Override
	public ArrayList<SampleInterface> getSamples() {
		return samples;
	}

	@Override
	public ArrayList<SoundEffectInterface> getSoundEffects() {
		return soundEffects;
	}

	@Override
	public ArrayList<AnimationInterface> getAnimations() {
		return animations;
	}

	@Override
	public ArrayList<SettingsInterface> getPreviousSettings() {
		return null;
	}

	public void setQubjects(ArrayList<Qubject> qubjects) {
		this.qubjects = qubjects;
	}

	public void setSamples(ArrayList<SampleInterface> samples) {
		this.samples = samples;
	}

	public void setSoundEffects(ArrayList<SoundEffectInterface> soundEffects) {
		this.soundEffects = soundEffects;
	}

	public void setAnimations(ArrayList<AnimationInterface> animations) {
		this.animations = animations;
	}

	@Override
	public SettingsInterface getSettings(File file) {
		return null;
	}
}
