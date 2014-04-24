package database;

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
		saveQubjects(path, qubble);
		saveGlobalParams(path, qubble);
	}
	
	private static void saveQubjects(String path, QubbleInterface qubble){
		String QubjectPath = path + "qubject/";
		for (Qubject qubject : qubble.getAllQubjects()){
			saveQubject(qubject, QubjectPath);
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
			
			//save properties to project root folder
			prop.store(new FileOutputStream(qubject.getName() + ".properties"), null);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	private static void saveGlobalParams(String path, QubbleInterface qubble){
		//TODO
	}
}
