package database;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import qubject.Qubject;

public class InitialiseProject
{
	public static ArrayList<Qubject> loadQubjectsFromPath(String savePath){
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
		return list;
	}	

	public static ArrayList<Qubject> loadQubjectsForNewProject(){
		//TODO
		return null;
	}

	private static Qubject loadQubjectFromProps(Properties prop){
		//TODO add try-catch
		//TODO add other params
		return new Qubject(
				prop.getProperty("name"),
				Integer.parseInt(prop.getProperty("bitIdentifier"))
				);
	}

	private static void loadNewQubjects(){
		//TODO
	}
}