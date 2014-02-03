package database;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * @author Cyril
 * Class used to load Qubject-related assets (samples, animations)
 * For project related settings, see InitialiseProjects
 * 
 * Methods in this class use a predetermined relative path (/data)
 * 
 * Note : should implement an interface or extends a class with static methods, 
 * however java 1.6 and 1.7 don't allow implementing interfaces with static methods !
 * (see SettingsInterface for tricks)
 *
 */
public class InitialiseAssets
{
	/*
	 * Note : cannot factorise more without using Class objects
	 * 	The instructions inside the try/catch block should be different for every Object type
	 */
	
	public static ArrayList<Sample> loadSamples(){
		Properties prop;
		File[] files = InitialiseTools.getFiles("/data/sound_effects/.");
		ArrayList<Sample> list = new ArrayList<Sample>(files.length);
		for (File entry : files){ //TODO : use fileInputStream
			prop = new Properties();
			try {
				prop.load(new FileInputStream(entry));	
				//TODO load other params
				list.add(new Sample(prop.getProperty("name"), new File(prop.getProperty("file"))));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return list;
	}
	
	public static ArrayList<SoundEffect> loadSoundEffects(){
		Properties prop;
		File[] files = InitialiseTools.getFiles("/data/sound_effects/.");
		ArrayList<SoundEffect> list = new ArrayList<SoundEffect>(files.length);
		for (File entry : files){ //TODO : use fileInputStream
			prop = new Properties();
			try {
				prop.load(new FileInputStream(entry));	
				//TODO load other params
				list.add(new SoundEffect(prop.getProperty("name"), prop.getProperty("file")));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return list;
	}
	
	public static ArrayList<Animation> loadAnimations(){
		Properties prop;
		File[] files = InitialiseTools.getFiles("/data/animation/.");
		ArrayList<Animation> list = new ArrayList<Animation>(files.length);
		for (File entry : files){ //TODO : use fileInputStream
			prop = new Properties();
			try {
				prop.load(new FileInputStream(entry));	
				//TODO load other params
				list.add(new Animation(prop.getProperty("name"), new File(prop.getProperty("file"))));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return list;
	}
	
	/*
	 * The following method should only be used when starting a new blank project
	 */
	/**
	 * Load Qubjects from the available Qubjects list, and initialise them with default settings
	 * @return Qubject ArrayList
	 */
	public static ArrayList<Qubject> loadQubjects(){
		Properties prop;
		File[] files = InitialiseTools.getFiles("/data/qubjects/.");
		ArrayList<Qubject> list = new ArrayList<Qubject>(files.length);
		for (File entry : files){ //TODO : use fileInputStream
			prop = new Properties();
			try {
				prop.load(new FileInputStream(entry));	
				//TODO load other params (default) + try/catch
				list.add(new Qubject(prop.getProperty("name"), 
						Integer.parseInt(prop.getProperty("bitIdentifier"))));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return list;
	}
}
