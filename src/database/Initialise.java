package database;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * @author Cyril
 * Note : should implement DataInterface interface, 
 * however java 1.6 and 1.7 don't allow implementing static methods !
 *
 */
public class Initialise
{
	public static ArrayList<Sample> loadSamples(){
		Properties prop;
		File[] files = getFiles("/data/sound_effects/.");
		ArrayList<Sample> list = new ArrayList<Sample>(files.length);
		for (File entry : files){ //TODO : use fileInputStream
			prop = new Properties();
			try {
				prop.load(new FileInputStream(entry));	
				list.add(new Sample(prop.getProperty("name"), new File(prop.getProperty("file"))));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return list;
	}
	
	public static ArrayList<SoundEffect> loadSoundEffects(){
		Properties prop;
		File[] files = getFiles("/data/sound_effects/.");
		ArrayList<SoundEffect> list = new ArrayList<SoundEffect>(files.length);
		for (File entry : files){ //TODO : use fileInputStream
			prop = new Properties();
			try {
				prop.load(new FileInputStream(entry));	
				list.add(new SoundEffect(prop.getProperty("name"), prop.getProperty("file")));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return list;
	}
	
	public static ArrayList<Animation> loadAnimations(){
		Properties prop;
		File[] files = getFiles("/data/animation/.");
		ArrayList<Animation> list = new ArrayList<Animation>(files.length);
		for (File entry : files){ //TODO : use fileInputStream
			prop = new Properties();
			try {
				prop.load(new FileInputStream(entry));	
				list.add(new Animation(prop.getProperty("name"), new File(prop.getProperty("file"))));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return list;
	}
	
	public static File[] getFiles(String directory){
		File dir = new File(directory);
		File [] files = dir.listFiles(
				new FilenameFilter() 
					{
					    @Override
					    public boolean accept(File dir, String name) {
					        return name.endsWith(".properties");
					    }
					});
		return files;
	}
}
