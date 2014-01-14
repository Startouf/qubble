package database;

import java.io.File;
import java.util.ArrayList;

/**
 * @author Cyril
 * Allow to retrieve Pattern Info, and user settings
 *
 */
public interface DataInterface {
	//Load entities
	//Interface methods can't be declared static in JAVA 1.6 and 1.7 !!!!!
	public ArrayList<Pattern> loadPattern();
	public ArrayList<Sample> loadSamples();
	public ArrayList<SoundEffect> loadSoundEffects();
	public ArrayList<Animation> loadAnimations();
	
	//Load user configs
	public ArrayList<SettingsInterface> getPreviousSettings(); //Show a list of recent projects
	public SettingsInterface getSettings(File file);
}