package database;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import qubject.Qubject;

public class InitialiseProject
{
	public static ArrayList<Qubject> loadQubjectsFromProject(String savePath){
		Properties prop;
		File[] files = InitialiseTools.getFiles(savePath);
		ArrayList<Qubject> list = new ArrayList<Qubject>(files.length);
		for (File entry : files){ //TODO : use fileInputStream
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
		//TODO : initialise default values for Qubjects ?
	}

	private static Qubject loadQubjectFromProps(Properties prop){
		//TODO add try-catch
		//TODO add other params
		return new Qubject(
				prop.getProperty("name"),
				Integer.parseInt(prop.getProperty("bitIdentifier"))
				);
	}

	/**
	 * Update the Qubject lists of old projects, if some new Qubjects are ever added to Qubble (crafted by our hands :) )
	 */
	private static void loadNewQubjects(ArrayList<Qubject> list){
		//TODO
	}
}