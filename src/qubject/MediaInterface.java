package qubject;

import java.awt.Image;

import audio.EffectType;


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
	 * The name corresponds to a human-distinction sign 
	 * (for example a (Qubject) Cube with stars drawn on it would be called "Star")
	 * @return
	 */
	public String getName();
	public Image getImage();
	
	/**
	 * Sample played when cursor reaches it
	 * @param sample
	 */
	public void setSampleWhenPlayed(SampleInterface sample);
	public SampleInterface getSampleWhenPlayed();
	
	/**
	 * Sound effect applied when translating along the Y-axis
	 * @param soundEffect
	 */
	public void setYAxisEffect(EffectType soundEffect);
	public EffectType getYAxisEffect();

	/**
	 * Animation played when cursor reaches it
	 * @param animation
	 */
	public void setAnimationWhenPlayed(AnimationInterface animation);
	public AnimationInterface getAnimationWhenPlayed();
	
	/*
	 * Prototype final
	 */
	
	public void setRotationEffect(EffectType soundEffect);
	public EffectType getRotationEffect();
	
	public void setAnimationWhenDetected(AnimationInterface animation);
	public AnimationInterface getAnimationWhenDetected();

	/**
	 * Useful to iterate over all of them
	 * @param property (SampleWhenPlayed, etc...)
	 */
	public QubjectModifierInterface getModifierForProperty(QubjectProperty property);
}
