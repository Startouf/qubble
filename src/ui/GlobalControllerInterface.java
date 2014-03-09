package ui;

import java.util.ArrayList;

import audio.SoundEffectInterface;
import database.Data;
import qubject.Animation;
import qubject.AnimationInterface;
import qubject.SampleInterface;

public interface GlobalControllerInterface {

	/**
	 * 
	 * @return The list of loaded samples
	 */
	public ArrayList<SampleInterface> getSamples();
	
	/**
	 * 
	 * @return The list of loaded soundEffects
	 */
	public ArrayList<SoundEffectInterface> getSoundEffects();

	/**
	 * 
	 * @return The list of Animations
	 */
	public ArrayList<AnimationInterface> getAnimations();

	/**
	 * TODO
	 * We don't care about this one ??? Data is somewhat already an implementation ?
	 * @return data which contains all the above stuff
	 */
	public Data getData();
}
