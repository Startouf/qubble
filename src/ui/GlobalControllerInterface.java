package ui;

import java.util.ArrayList;

import audio.EffectType;
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
	public ArrayList<EffectType> getSoundEffects();

	/**
	 * 
	 * @return The list of Animations
	 */
	public ArrayList<AnimationInterface> getAnimations();

}
