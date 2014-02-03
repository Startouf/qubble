package database;

import java.io.File;
import java.util.ArrayList;

import audio.SoundEffectInterface;

/**
 * @author Cyril
 * Allow to retrieve Qubject Info, and user settings
 * NOTE : many ways to "use" static methods in interfaces :
 * 		1 - Use an abstract class that has static methods
 * 		2- create an instance of the class, then it's possible to use non-static methods 
 */
public interface DataInterface {
	//Load entities
	//Interface methods can't be declared static in JAVA 1.6 and 1.7 !!!!!
	public ArrayList<Qubject> getQubject();
	public ArrayList<SampleInterface> getSamples();
	public ArrayList<SoundEffectInterface> getSoundEffects();
	public ArrayList<AnimationInterface> getAnimations();
	
	//Load user configs
	public ArrayList<SettingsInterface> getPreviousSettings(); //Show a list of recent projects
	public SettingsInterface getSettings(File file);
}