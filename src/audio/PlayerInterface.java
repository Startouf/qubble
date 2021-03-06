package audio;

import java.io.File;

import qubject.SampleInterface;

public interface PlayerInterface
{
	/**
	 * 
	 * @param sample
	 * @return A controller of the sound that is played so it can be tweaked (Volume, SoundEffects...)
	 */
	public SampleControllerInterface playSample(SampleInterface sample);
	
	/**
	 * Change the sample that is currently played (volume, effect...)
	 * @param ref a reference to the controller of a sample currently being played
	 * @param effect the effect that should be applied to the sample
	 * @param amount the percentage (which corresponds to the y-position on the Qubble) 
	 */
	public void tweakSample(SampleControllerInterface ref, EffectType effect, int amount);

	/**
	 * Pause the currently playing sample
	 * @param sc
	 */
	public void playPause();

	/**
	 * Quand le projet est fermé, il faut terminer le thread et tout détruire
	 * (la destruction se fait par le Garbage Collector, en gros il faut juste terminer les threads)
	 */
	public void destroy();

	/**
	 * Cancel all sounds (No need to send hasFinishedPlaying stuff)
	 */
	public void stopAllSounds();
	
	/**
	 * Start recording all audio output in a file (in addition to playing the sound)
	 * @param file output file (assume it already exists)
	 */
	public void startRecording(File file);
	
	/**
	 * Stop recording the sound
	 */
	public void stopRecording();
	
	public void mute();
}
