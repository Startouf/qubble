package database;

import java.io.File;
import java.util.ArrayList;

import audio.SoundEffectInterface;
import qubject.AnimationInterface;
import qubject.Qubject;
import qubject.SampleInterface;
import sequencer.QubbleInterface;

/**
 * Holds the assets of Qubble
 * 
 * @author duchon
 *
 */
public final class Data implements DataInterface
{
	//NOTE : there should be a difference Between the qubjects of this list and the qubjects of a saved project
	private final ArrayList<Qubject> qubjects;
	private final ArrayList<SampleInterface> samples;
	private final ArrayList<SoundEffectInterface> soundEffects;
	private final ArrayList<AnimationInterface> animations;

	public Data(){
		super();
		qubjects = InitialiseAssets.loadQubjects();
		samples = InitialiseAssets.loadSamples();
		soundEffects = InitialiseAssets.loadSoundEffects();
		animations = InitialiseAssets.loadAnimations();
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
	public ArrayList<QubbleInterface> getPreviousSettings() {
		//TODO
		return null;
	}

	@Override
	public QubbleInterface getSettings() {
		// TODO Auto-generated method stub
		return null;
	}
}
