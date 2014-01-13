package database;

import java.io.File;

import audio.SampleInterface;

public interface PatternInterface {
	
	public boolean isHere();
	public int getBitIdentifier();
	public String getName();
	
	public void setSample(SampleInterface sample);
	public Sample getSample();
	
	public void setYAxisEffect(SoundEffect soundEffect);
	public SoundEffect getYAxisEffect();
	
	public void setRotationEffect(SoundEffect soundEffect);
	public SoundEffect getSoundEffect();

	public void setAnimation(Animation animation);
	public Animation getAnimation();
	
}
