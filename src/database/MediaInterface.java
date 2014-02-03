package database;

import audio.SoundEffectInterface;

/**
 * @author duchon
 * Handles Qubject-related features : music/animation to be played
 *
 */
public interface MediaInterface
{
	/*
	 * Prototype allégé
	 */
	
	/**
	 * Sample played when cursor reaches it
	 * @param sample
	 */
	public void setSampleWhenPlayed(SampleInterface sample);
	public Sample getSampleWhenPlayed();
	
	/**
	 * Sound effect applied when translating along the Y-axis
	 * @param soundEffect
	 */
	public void setYAxisEffect(SoundEffectInterface soundEffect);
	public SoundEffect getYAxisEffect();

	/**
	 * Animation played when cursor reaches it
	 * @param animation
	 */
	public void setAnimationWhenPlayed(AnimationInterface animation);
	public Animation getAnimationWhenPlayed();
	
	/*
	 * Prototype final
	 */
	
//	public void setRotationEffect(SoundEffect soundEffect);
//	public SoundEffect getSoundEffect();
//	
//	public void setAnimationWhenDetected(Animation animation);
//	public Animation getAnimationWhenDetected();
}
