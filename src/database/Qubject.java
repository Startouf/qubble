package database;

import java.io.File;
import java.util.Properties;

import org.lwjgl.util.Point;

import audio.SoundEffectInterface;


/**
 * @author duchon
 * NOTE : class should be renamed "Cube" or "Polyhedron" !!!
 *
 */
public final class Qubject implements QRInterface, MediaInterface {

	private final Point coords = null;
	private final String name;
	private final int bitIdentifier;
	private boolean isHere = false;
	
	private Sample sampleWhenPlayed;
	private SoundEffect yAxisModifier;
	private SoundEffect rotationModifier;
	private Animation whenPutOnTable;
	private Animation whenPlayed;
	
	public Qubject(String name, int bitIdentifier){
		this.name = name;
		this.bitIdentifier = bitIdentifier;
		
		//TODO : assign default sample/effect/animation effects 
		//(done here or somewhere else ?)
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
		return isHere;
	}

	@Override
	public void setSampleWhenPlayed(SampleInterface sample) {
		this.sampleWhenPlayed = (Sample) sample;
	}

	@Override
	public Sample getSampleWhenPlayed() {
		return this.sampleWhenPlayed;
	}

	@Override
	public void setYAxisEffect(SoundEffectInterface soundEffect) {
		this.yAxisModifier = (SoundEffect) soundEffect;
	}

	@Override
	public SoundEffect getYAxisEffect() {
		return this.yAxisModifier;
	}

	@Override
	public void setAnimationWhenPlayed(AnimationInterface animation) {
		this.whenPlayed = (Animation) animation;
	}

	@Override
	public Animation getAnimationWhenPlayed() {
		return whenPlayed;
	}
	
	
}
