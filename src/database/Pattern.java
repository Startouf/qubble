package database;

import java.io.File;

import org.lwjgl.util.Point;

import audio.SampleInterface;

/**
 * @author duchon
 * List of the patterns that will be used 
 * Patterns are QR-codes identified by a sequence of 0 and 1
 * Therefore they have an attribute bitIdentifier that must be >= to short (2^16)
 * (An int was chosen instead)
 *
 */
public class Pattern implements PatternInterface {

	private final Point coords = null;
	private final String name;
	private final int bitIdentifier;
	private boolean isHere = false;
	
	private SoundEffect yAxisModifier;
	private SoundEffect rotationModifier;
	private Animation whenPutOnTable;
	private Animation whenPlayed;
	
	public Pattern(String name, int bitIdentifier){
		this.name = name;
		this.bitIdentifier = bitIdentifier;
	}
	
	@Override
	public int getBitIdentifier(){
		return this.bitIdentifier;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public boolean isHere() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setSample(SampleInterface sample) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Sample getSample() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setYAxisEffect(SoundEffect soundEffect) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SoundEffect getYAxisEffect() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRotationEffect(SoundEffect soundEffect) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SoundEffect getSoundEffect() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAnimation(Animation animation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Animation getAnimation() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
