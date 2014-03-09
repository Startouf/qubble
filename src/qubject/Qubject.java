package qubject;

import java.io.File;
import java.util.Properties;

import org.lwjgl.util.Point;

import audio.EffectType;
import audio.SoundEffect;
import audio.EffectType;



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
	
	private SampleInterface sampleWhenPlayed = new Sample("Cool sound", new File("data/samples/files/VEE Melody Kits 03 128 BPM Root G#Mono.wav"));
	private EffectType yAxisModifier = EffectType.Volume;
	private EffectType rotationModifier = EffectType.Distortion;
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
	public void setYAxisEffect(EffectType soundEffect) {
		this.yAxisModifier =  soundEffect;
	}

	@Override
	public EffectType getYAxisEffect() {
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

	@Override
	public QubjectModifierInterface getModifierForProperty(QubjectModifiers property) {
		switch(property){
		case animationWhenPlayed:
			return(this.animationwhenPlayed);
		case rotationModifier:
			return(this.rotationModifier);
		case sampleWhenPlayed:
			return(this.sampleWhenPlayed);
		case whenPutOnTable:
			return this.whenPutOnTable;
		case yAxisModifier:
			return this.getYAxisEffect();
		default:
			//TODO : throw exception
			return null;
		}
	}

	@Override
	public void setRotationEffect(EffectType soundEffect) {
		this.rotationModifier = soundEffect;
	}

	@Override
	public EffectType getSoundEffect() {
		return this.rotationModifier;
	}

	@Override
	public void setAnimationWhenDetected(AnimationInterface animation) {
		this.whenPutOnTable = animation;
	}

	@Override
	public AnimationInterface getAnimationWhenDetected() {
		return this.whenPutOnTable;
	}
}
