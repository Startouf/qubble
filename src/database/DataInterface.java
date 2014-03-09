package database;

import java.util.ArrayList;

import audio.EffectType;
import audio.SoundEffectInterface;
import qubject.AnimationInterface;
import qubject.Qubject;
import qubject.SampleInterface;
import sequencer.QubbleInterface;


/**
 * @author Cyril
 * Allow to retrieve Qubject Info, and user settings
 * NOTE : Interface methods can't be declared static in JAVA 1.6 and 1.7 !!!!!
 * ...But many ways to "use" static methods in interfaces :
 * 		1 - Use an abstract class that has static methods
 * 		2- create an instance of the class, then it's possible to use non-static methods 
 * 
 * NOTE 2 : After seeing the table, we might want to be able to start 2 projects at the same time. 
 * So maybe we don't even need static methods at all !
 */
public interface DataInterface {
	//Load assets :
	/**
	 * Load all the Qubjects that have been defined in .properties in data/qubjects/
	 * @return
	 */
	public ArrayList<Qubject> getQubjects();
	
	/**
	 * Load all the samples that have been defined in .properties in data/samples/
	 * @return list of Samples
	 */
	public ArrayList<SampleInterface> getSamples();
	
	/**
	 * Load all the effects that have been defined in .properties in data/sound_effects/
	 * @return list of sound effects
	 */
	public ArrayList<EffectType> getSoundEffects();
	
	/**
	 * Load all the animations that have been defined in .properties in data/animations/
	 * @return list of animations
	 */
	public ArrayList<AnimationInterface> getAnimations();

	//Load lists of user configs :
	/**
	 * Load a list of saved projects: number of folders in /data/save/
	 * @return list of saved projects
	 */
	public ArrayList<QubbleInterface> getPreviousSettings();
	/**
	 * Return a reference to the last saved project
	 * @return last saved Project
	 */
	public QubbleInterface getSettings();
}