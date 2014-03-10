package database;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

import audio.EffectType;
import audio.SoundEffect;
import audio.SoundEffectInterface;
import qubject.Animation;
import qubject.AnimationInterface;
import qubject.Qubject;
import qubject.Sample;
import qubject.SampleInterface;

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

	/**
	 * Currently loaded props : name and file
	 * @return
	 */
	public static ArrayList<SampleInterface> loadSamples(){
		Properties prop;
		File[] files = InitialiseTools.getFiles("data/samples/");
		ArrayList<SampleInterface> list = new ArrayList<SampleInterface>(files.length);
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

	/**
	 * Currently loaded props : name and file
	 * @return
	 */
	public static ArrayList<EffectType> loadSoundEffects(){
		//Using hardcoded SoundEffects
		ArrayList<EffectType> list = new ArrayList<EffectType>();
		for (EffectType effect : EffectType.values()){
			list.add(effect);
		}
		
		//OLD :
//		Properties prop;
//		File[] files = InitialiseTools.getFiles("data/sound_effects/");
//		ArrayList<SoundEffectInterface> list = new ArrayList<SoundEffectInterface>(files.length);
//		for (File entry : files){ //TODO : use fileInputStream
//			prop = new Properties();
//			try {
//				prop.load(new FileInputStream(entry));	
//				//TODO load other params
//				list.add(new SoundEffect(prop.getProperty("name"), prop.getProperty("file")));
//			} catch (IOException ex) {
//				ex.printStackTrace();
//			}
//		}
		return list;
	}

	/**
	 * Currently loaded props : name and file from data/animations
	 * @return
	 */
	public static ArrayList<AnimationInterface> loadAnimations(){
		ArrayList<AnimationInterface> list = new ArrayList<AnimationInterface>(2);
		list.add(new Animation("Water wave", new File("data/animations/controllers/wave/WaterWave.java")));
		list.add(new Animation("Pixel Explosion", new File("data/animations/controllers/explosion/PixelExplosion.java")));
//		Properties prop;
//		File[] files = InitialiseTools.getFiles("data/animations/");
//		for (File entry : files){ //TODO : use fileInputStream
//			prop = new Properties();
//			try {
//				prop.load(new FileInputStream(entry));	
//				//TODO load other params
//				list.add(new Animation(prop.getProperty("name"), new File(prop.getProperty("file"))));
//			} catch (IOException ex) {
//				ex.printStackTrace();
//			}
//		}
		return list;
	}

	/**
	 * Load Qubjects from the available Qubjects list, and initialise them with default settings
	 * !! Should only be used when starting a new blank project !!
	 * @return Qubject ArrayList
	 */
	public static ArrayList<Qubject> loadQubjects(){
		Properties prop;
		File[] files = InitialiseTools.getFiles("data/qubjects/");
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