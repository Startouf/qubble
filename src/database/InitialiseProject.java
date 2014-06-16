package database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import audio.EffectType;
import qubject.AnimationInterface;
import qubject.Qubject;
import qubject.QubjectProperty;
import qubject.SampleInterface;

public class InitialiseProject
{
	public static String getProjectName(String savePath){
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(savePath + "params.properties"));
		} catch (FileNotFoundException e) {
			System.err.println("Could not find params.properties");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Could not read from params.properties");
			e.printStackTrace();
		}
		return prop.getProperty("project_name");
	}
	
	public static ArrayList<Qubject> loadQubjectsFromProject(String savePath){
		Properties prop;
		
		File[] qubjectProperties = DataTools.getDotProperties(savePath +"\\qubjects");
		ArrayList<Qubject> list = new ArrayList<Qubject>(qubjectProperties.length);
		for (File entry : qubjectProperties){
			prop = new Properties();
			try {
				prop.load(new FileInputStream(entry));	
				list.add(loadQubjectFromProps(prop));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		//Check for new crafted qubjects
		loadNewQubjects(list);
		return list;
	}	

	public static ArrayList<Qubject> loadQubjectsForNewProject(){
		return InitialiseAssets.loadQubjects();
	}

	/**
	 * Instanciates a Qubject from .property that contains entries of QubjectProperties.values()
	 * TODO : add Exceptions and try/catch/throw ?
	 * @param prop
	 * @return
	 */
	private static Qubject loadQubjectFromProps(Properties prop){
		return new Qubject(
				prop.getProperty("name"),
				Integer.parseInt(prop.getProperty("bitIdentifier")),
				Integer.parseInt(prop.getProperty("lastX")),
				Integer.parseInt(prop.getProperty("lastY")),
				findSample(prop.getProperty(QubjectProperty.SAMPLE_WHEN_PLAYED.toString())),
				findEffect(prop.getProperty(QubjectProperty.AUDIO_EFFECT_Y_AXIS.toString())),
				findEffect(prop.getProperty(QubjectProperty.AUDIO_EFFECT_ROTATION.toString())),
				findAnimation(prop.getProperty(QubjectProperty.ANIM_WHEN_DETECTED.toString())),
				findAnimation(prop.getProperty(QubjectProperty.ANIM_WHEN_PLAYED.toString()))
				);
	}
	
	private static SampleInterface findSample(String name){
		//TODO : Hashtable
		for (SampleInterface sample : Data.getSamples()){
			if (name.equals(sample.getName())){
				return sample;
			}
		}
		//If not found, return random value
		System.err.println("Specified anim name " + name + " in .property cannot be found in Sample List");
		return(Data.getSamples().get(0));
	}
	
	private static AnimationInterface findAnimation(String name){
		//TODO : Hashtable
		for (AnimationInterface anim : Data.getAnimations()){
			if (name.equals(anim.getName())){
				return anim;
			}
		}
		//If not found, return random value
		System.err.println("Specified anim name " + name + " in .property cannot be found in Anim List");
		return(Data.getAnimations().get(0));
	}
	
	private static EffectType findEffect(String name){
		//TODO : Hashtable
		for (EffectType effect : EffectType.values()){
			if (name.equals(effect.toString())){
				return effect;
			}
		}
		System.err.println("Specified effect name " + name + " in .property cannot be found in effect List");
		return(EffectType.Volume);
	}

	/**
	 * Update the Qubject lists of old projects, if some new Qubjects are ever added to Qubble (crafted by our hands :) )
	 */
	private static void loadNewQubjects(ArrayList<Qubject> list){
		//TODO
	}
}