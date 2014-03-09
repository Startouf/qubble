package qubject;

import java.io.File;
import java.util.Properties;

import org.lwjgl.util.Point;

import audio.SoundEffect;
import audio.SoundEffectInterface;



/**
 * @author duchon
 * The Qubject class
 *
 */
public final class Qubject implements QRInterface, MediaInterface {

	private final String name;
	private final int bitIdentifier;
	private boolean isHere = false;
	private Point coords = new Point(0,0);
	
	private SampleInterface sampleWhenPlayed = new Sample("Cool sound", new File("data/samples/bruits.wav"));
	private SoundEffectInterface yAxisModifier;
	private SoundEffectInterface rotationModifier;
	private AnimationInterface whenPutOnTable;
	private AnimationInterface animationwhenPlayed = 
			new Animation("Water wave", new File("data/animations/controllers/wave/WaterWave.java"));
	
	public static final float SIZE = 30f;
	
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
	public void setSampleWhenPlayed(SampleInterface sample) {
		this.sampleWhenPlayed = (Sample) sample;
	}

	@Override
	public SampleInterface getSampleWhenPlayed() {
		return this.sampleWhenPlayed;
	}

	@Override
	public void setYAxisEffect(SoundEffectInterface soundEffect) {
		this.yAxisModifier = (SoundEffect) soundEffect;
	}

	@Override
	public SoundEffectInterface getYAxisEffect() {
		return this.yAxisModifier;
	}

	@Override
	public void setAnimationWhenPlayed(AnimationInterface animation) {
		this.animationwhenPlayed = animation;
	}

	@Override
	public AnimationInterface getAnimationWhenPlayed() {
		return animationwhenPlayed;
	}

	@Override
	public Point getCoords() {
		return coords;
	}

	@Override
	public void setCoords(Point pos) {
		this.coords = pos;
	}
}
