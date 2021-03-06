package database;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

import explosion.PixelSpray;
import audio.EffectType;
import audio.SoundEffect;
import audio.SoundEffectInterface;
import qubject.Animation;
import qubject.AnimationInterface;
import qubject.Qubject;
import qubject.Sample;
import qubject.SampleInterface;
import wave.WaterWave;

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
	private static final String controllersDir = "data/animations/controllers/";

	/**
	 * Convenience methods that allows to quickly change how samples should be loaded
	 * Historically used to swap from long samples to quick samples
	 * (PAN4 to Innovation Day)
	 * @return An ArrayList of Loaded Samples to be assigned to Qubjects
	 */
	public static ArrayList<SampleInterface> loadSamples(){
		return loadSamplesFromWAV();
	}
	
	/**
	 * NOTE : the following method was used to load long samples (~10secs)
	 * Currently loaded props : name and file
	 * @return
	 */
	public static ArrayList<SampleInterface> loadSamplesFromProperties(){
		Properties prop;
		File[] files = DataTools.getDotProperties("data/samples/");
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
	 * This method is used to load short samples (~1sec)
	 * @return Samples loaded from WAV files 
	 */
	public static ArrayList<SampleInterface> loadSamplesFromWAV(){
		Properties prop;
		File[] files = DataTools.getDotWAV("data/samples/files/short");
		ArrayList<SampleInterface> list = new ArrayList<SampleInterface>(files.length);
		for (File entry : files){ //TODO : use fileInputStream
			list.add(new Sample(entry.getName(), entry));
		}
		return list;
	}
	
	/**
	 * Loading Hard-coded EffectTypes
	 * @return
	 */
	public static ArrayList<EffectType> loadSoundEffects(){
		//Using hardcoded SoundEffects
		ArrayList<EffectType> list = new ArrayList<EffectType>();
		for (EffectType effect : EffectType.values()){
			list.add(effect);
		}
		return list;
	}

	/**
	 * Currently loaded props : name and file from data/animations
	 * Will try to load external classes found in /controllers/[...]
	 * @return
	 */
	public static ArrayList<AnimationInterface> loadAnimations(){
		ArrayList<AnimationInterface> list = new ArrayList<AnimationInterface>();
		//Dummy Load
//		list.add(new Animation("Water wave", new File("data/animations/controllers/wave/WaterWave.java"), WaterWave.class));
//		list.add(new Animation("Pixel Explosion", new File("data/animations/controllers/explosion/PixelExplosion.java"), PixelExplosion.class));
		Properties prop;
		File[] files = DataTools.getDotProperties("data/animations/");
		for (File entry : files){ //TODO : use fileInputStream
			prop = new Properties();
			try {
				prop.load(new FileInputStream(entry));
				File dotJavaFile = new File (controllersDir + prop.getProperty("dotJavaFile"));
				if (prop.getProperty("dotClassFile") == null){
					//TODO : check the date to see if it needs to be recompiled
					//(Use a sort of makefile)
					DataTools.compileAnimation(dotJavaFile);
					prop.setProperty("dotClassFile", DataTools.getDotClassFromDotJava(prop.getProperty("dotJavaFile")));
					prop.store(new FileOutputStream(entry), null);
				}				
				list.add(new Animation(prop.getProperty("name"),dotJavaFile,
						loadAnimation(new File(controllersDir + prop.getProperty("dotClassFile")))));
			} catch (IOException ex) {
				ex.printStackTrace();
			} catch (CannotLoadAnimationException e) {
				System.err.println("Cannot create Class Object from animation.class !");
				e.printStackTrace();
			}
		}
		
		
		return list;
	}

	/**
	 * @param dotClassFile the dotClassFile
	 * @return a Class than can be instanciated as a AnimationController
	 * @throws CannotLoadAnimationException
	 */
	private static Class loadAnimation(File dotClassFile) throws CannotLoadAnimationException{
		try {
			URLClassLoader cl = new URLClassLoader(new URL[]{(new File(controllersDir)).toURI().toURL()});
			Class<?> clazz = cl.loadClass(DataTools.getBinaryClassNameFromDotClass(new File(controllersDir), dotClassFile));
			return clazz;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//If didn't work :
		throw new CannotLoadAnimationException();
	}

	/**
	 * Load Qubjects from the available Qubjects .properties, and initialise them with default settings
	 * !! Should only be used when starting a new blank project !!
	 * @return Qubject ArrayList
	 */
	public static ArrayList<Qubject> loadQubjects(){
		Properties prop;
		File[] files = DataTools.getDotProperties("data/qubjects/");
		ArrayList<Qubject> list = new ArrayList<Qubject>(files.length);
		for (File entry : files){ //TODO : use fileInputStream
			prop = new Properties();
			try {
				prop.load(new FileInputStream(entry));	
				//TODO can swap getRandomlyConfiguredQubject(..) to new Qubject(...)
				list.add(getRandomlyConfiguredQubject(prop.getProperty("name"), 
						Integer.parseInt(prop.getProperty("bitIdentifier"))));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return list;
	}
	
	private static Random rand = new Random();
	
	/**
	 * Used to generate Qubjects with random properties 
	 * @param name Qubject name
	 * @param id Qubject ID
	 * @return
	 */
	public static Qubject getRandomlyConfiguredQubject(String name, int id){
		return(new Qubject(name, id, 
				getRandomSample(),
				getRandomEffect(),
				getRandomEffect(),
				getRandomAnimation(),
				getRandomAnimation()));
	}
	
	private static SampleInterface getRandomSample(){
		return Data.getSamples().get(rand.nextInt(Data.getSamples().size()));
	}
	
	private static EffectType getRandomEffect(){
		return Data.getSoundEffects().get(rand.nextInt(Data.getSoundEffects().size()));
	}
	
	private static AnimationInterface getRandomAnimation(){
		return Data.getAnimations().get(rand.nextInt(Data.getAnimations().size()));
	}
}