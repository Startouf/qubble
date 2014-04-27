package database;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

import qubject.Qubject;
import qubject.QubjectProperty;
import sequencer.Qubble;
import sequencer.QubbleInterface;

/**
 * @author duchon
 * Note : using a static-method-in-interface trick would really be helpful here
 *
 */
public class SaveProject
{	
	public static void saveTo(String path, QubbleInterface qubble){
		DataTools.EnsureDirExists(path);
		saveQubjects(path, qubble);
		saveGlobalParams(path, qubble);
	}
	
	private static void saveQubjects(String path, QubbleInterface qubble){
		String qubjectPath = path + "qubjects/";
		DataTools.EnsureDirExists(qubjectPath);
		for (Qubject qubject : qubble.getAllQubjects()){
			saveQubject(qubject, qubjectPath);
		}
	}
	
	private static void saveQubject(Qubject qubject, String qubjectPath){
		Properties prop = new Properties();
		 //TODO
		try {
			//set the properties value
			prop.setProperty("name", qubject.getName());
			prop.setProperty("bitIdentifier", String.valueOf(qubject.getBitIdentifier()));
			prop.setProperty("X", String.valueOf(qubject.getCoords().getX()));
			prop.setProperty("Y", String.valueOf(qubject.getCoords().getY()));
			
			for(QubjectProperty qubjectProp : QubjectProperty.values()){
				prop.setProperty(qubjectProp.toString(), qubject.getModifierForProperty(qubjectProp).getName());
			}
			
			String fileName = qubjectPath + qubject.getName() + ".properties";
			DataTools.saveProperties(prop, fileName);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	private static void saveGlobalParams(String path, QubbleInterface qubble){
		//TODO
	}
}
