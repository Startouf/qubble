package audio;

import qubject.SampleInterface;
import qubject.SoundEffect;

public interface SoundInterface
{
	/**
	 * 
	 * @param sample
	 * @return A controller of the sound that is played so it can be tweaked (Volume, SoundEffects...)
	 */
	public SampleController playSample(SampleInterface sample);
	
	/**
	 * Change the sample that is currently played (volume, effect...)
	 * @param ref a reference to the controller of a sample currently being played
	 * @param effect the effect that should be applied to the sample
	 */
	public void tweakSample(SampleController ref, SoundEffect effect);
}
