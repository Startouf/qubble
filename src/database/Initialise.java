package database;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class Initialise
{
	public static ArrayList<SoundEffect> initialiseSoundEffects(){
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
	
	public static ArrayList<Animation> initialiseAnimations(){
		//TODO
		return null;
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
