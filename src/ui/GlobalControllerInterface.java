package ui;

import java.util.ArrayList;

import qubject.Animation;
import qubject.SampleInterface;
import qubject.SoundEffectInterface;

public interface GlobalControllerInterface {

	public ArrayList<SampleInterface> getSamples();

	public ArrayList<SoundEffectInterface> getSoundEffects();

	public ArrayList<Animation> getAnimations();
}
