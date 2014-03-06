package ui;

import java.util.ArrayList;

import audio.SoundEffectInterface;
import database.Data;
import qubject.Animation;
import qubject.AnimationInterface;
import qubject.SampleInterface;

public interface GlobalControllerInterface {

	public ArrayList<SampleInterface> getSamples();

	public ArrayList<SoundEffectInterface> getSoundEffects();

	public ArrayList<AnimationInterface> getAnimations();
	
	public Data getData();
}
