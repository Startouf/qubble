package database;

import java.io.File;
import java.util.ArrayList;

import audio.SoundEffectInterface;
/**
 * Holds the assets of Qubble
 * 
 * NOTE : this class assumes only one project can be opened at a time !
 * (therefore methods are declared static)
 * 
 * @author duchon
 *
 */
public class Data implements DataInterface
{
	private static ArrayList<Qubject> qubjects = new ArrayList<Qubject>();
	private static ArrayList<SampleInterface> samples = new ArrayList<SampleInterface>();
	private static ArrayList<SoundEffectInterface> soundEffects = new ArrayList<SoundEffectInterface>();
	private static ArrayList<AnimationInterface> animations = new ArrayList<AnimationInterface>();

	
	@Override
	public ArrayList<Qubject> getQubject() {
		return qubjects;
	}

	@Override
	public ArrayList<SampleInterface> getSamples() {
		return samples;
	}

	@Override
	public ArrayList<SoundEffectInterface> getSoundEffects() {
		return null;
	}

	@Override
	public ArrayList<AnimationInterface> getAnimations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<SettingsInterface> getPreviousSettings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SettingsInterface getSettings(File file) {
		// TODO Auto-generated method stub
		return null;
	}

}
