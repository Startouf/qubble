package qubject;

import java.io.File;
import java.util.Properties;

import org.lwjgl.util.Point;

import wave.WaterWave;

import audio.EffectType;
import audio.SoundEffect;
import audio.EffectType;



/**
 * @author duchon
 * The Qubject class
 *
 */
public final class Qubject implements QRInterface, MediaInterface, Comparable {

	private final String name;
	private final int bitIdentifier;
	private Point coords = new Point(0,0);

	/**
	 * Rotation value used by Qubble in float radians
	 * (Might want to save it in the .property !)
	 */
	private float rotation = 0f;
	
	/*
	 * TODO : default properties initialisation should be done in database.InitialiseAssets !
	 */
	private SampleInterface sampleWhenPlayed;
	private EffectType yAxisModifier;
	private EffectType rotationModifier;
	private AnimationInterface whenPutOnTable;
	private AnimationInterface animationwhenPlayed;
	
	public static final float SIZE = 30f;

	/**
	 * TODO : this constructor should NOT be used 
	 * ...once default modifiers for properties are assigned during InitialiseAssets()
	 * @param name
	 * @param bitIdentifier
	 */
	public Qubject(String name, int bitIdentifier){
		this.name = name;
		this.bitIdentifier = bitIdentifier;
		//Should not be used
	}

	/**
	 * Full Constructor for Qubjects. Assumes properties have already been extracted from a .property
	 * (Or generated by a Test class)
	 * @param name
	 * @param bitIdentifier
	 * @param sampleWhenPlayed
	 * @param yAxisModifier
	 * @param rotationModifier
	 * @param whenPutOnTable
	 * @param animationwhenPlayed
	 */
	public Qubject(String name, int bitIdentifier, SampleInterface sampleWhenPlayed, 
			EffectType yAxisModifier, EffectType rotationModifier,
			AnimationInterface whenPutOnTable,
			AnimationInterface animationwhenPlayed){
		this.name = name;
		this.bitIdentifier = bitIdentifier;
		this.animationwhenPlayed = animationwhenPlayed;
		this.rotationModifier = rotationModifier;
		this.sampleWhenPlayed = sampleWhenPlayed;
		this.whenPutOnTable = whenPutOnTable;
		this.yAxisModifier = yAxisModifier;
	}
	
	public Qubject(String name, int bitIdentifier, int lastX, int lastY, SampleInterface sampleWhenPlayed, 
			EffectType yAxisModifier, EffectType rotationModifier,
			AnimationInterface whenPutOnTable,
			AnimationInterface animationwhenPlayed){
		this.name = name;
		this.coords = new Point(lastX, lastY);
		this.bitIdentifier = bitIdentifier;
		this.animationwhenPlayed = animationwhenPlayed;
		this.rotationModifier = rotationModifier;
		this.sampleWhenPlayed = sampleWhenPlayed;
		this.whenPutOnTable = whenPutOnTable;
		this.yAxisModifier = yAxisModifier;
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
	public QubjectModifierInterface getModifierForProperty(QubjectProperty property) {
		switch(property){
		case ANIM_WHEN_PLAYED:
			return(this.animationwhenPlayed);
		case AUDIO_EFFECT_ROTATION:
			return(this.rotationModifier);
		case SAMPLE_WHEN_PLAYED:
			return(this.sampleWhenPlayed);
		case ANIM_WHEN_DETECTED:
			return this.whenPutOnTable;
		case AUDIO_EFFECT_Y_AXIS:
			return this.getYAxisEffect();
		default:
			System.err.println("TODO : Behaviour not defined in Qubject class for " + property.toString());
			return null;
		}
	}

	@Override
	public void setRotationEffect(EffectType soundEffect) {
		this.rotationModifier = soundEffect;
	}

	@Override
	public EffectType getRotationEffect() {
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

	@Override
	public float getRotation() {
		return rotation;
	}

	@Override
	public void setRotation(float floatRadians) {
		this.rotation = floatRadians;
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Qubject){
			return(this.getCoords().getX()-((Qubject)o).getCoords().getX());
		} else{
			System.err.println("Not sorting Qubjects!");
			return 0;
		}
	}
}	
