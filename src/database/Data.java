package database;

import java.io.File;
import java.util.ArrayList;

import audio.EffectType;
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
public final class Data
{
	//NOTE : there should be a difference Between the qubjects of this list and the qubjects of a saved project
	private static final ArrayList<Qubject> qubjects;
	private static final ArrayList<SampleInterface> samples;
	private static final ArrayList<EffectType> soundEffects;
	private static final ArrayList<AnimationInterface> animations;

	static{
		samples = InitialiseAssets.loadSamples();
		soundEffects = InitialiseAssets.loadSoundEffects();
		animations = InitialiseAssets.loadAnimations();
		qubjects = InitialiseAssets.loadQubjects();
	}
	
	public static ArrayList<Qubject> getQubjects() {
		return qubjects;
	}

	public static ArrayList<SampleInterface> getSamples() {
		return samples;
	}

	public static ArrayList<EffectType> getSoundEffects() {
		return soundEffects;
	}

	public static ArrayList<AnimationInterface> getAnimations() {
		return animations;
	}

	public static ArrayList<QubbleInterface> getPreviousSettings() {
		//TODO
		return null;
	}
}
