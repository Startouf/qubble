package database;

import java.io.File;


/**
 * @author Cyril
 * Note : interface should be split 
 * PatternInterface should keep only methods related to the detection of the QR-code 
 * CubeInterface should have the methods relative to a given user configuration
 *
 */
public interface PatternInterface {
	//PatternInterface
	public boolean isHere();
	public int getBitIdentifier();
	public String getName();
	
	//CubeInterface
	public void setSample(SampleInterface sample);
	public Sample getSample();
	
	public void setYAxisEffect(SoundEffect soundEffect);
	public SoundEffect getYAxisEffect();
	
	public void setRotationEffect(SoundEffect soundEffect);
	public SoundEffect getSoundEffect();

	public void setAnimation(Animation animation);
	public Animation getAnimation();
	
}
