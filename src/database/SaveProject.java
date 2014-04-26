package database;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import qubject.Qubject;
import qubject.QubjectProperty;
import sequencer.Qubble;
import sequencer.QubbleInterface;
import ui.ProjectControllerInterface;

/**
 * @author duchon
 * Note : using a static-method-in-interface trick would really be helpful here
 *
 */
public class SaveProject
{	
	public static void saveTo(String path, ProjectControllerInterface project){
		saveQubjects(path, project);
		saveGlobalParams(path, project);
	}
	
	private static void saveQubjects(String path, ProjectControllerInterface project){
		String QubjectPath = path + "qubjects/";
		for (Qubject qubject : project.getQubjects()){
			saveQubject(qubject, QubjectPath);
		}
	}
	
	private static void saveQubject(Qubject qubject, String qubjectPath){
		Properties prop = new Properties();
		try {
			//set the properties value
			prop.setProperty("name", qubject.getName());
			prop.setProperty("bitIdentifier", Integer.toString(qubject.getBitIdentifier()));
			prop.setProperty("lastX", Integer.toString(qubject.getCoords().getX()));
			prop.setProperty("lastY", Integer.toString(qubject.getCoords().getY()));
			
			for(QubjectProperty qubjectProp : QubjectProperty.values()){
				prop.setProperty(qubjectProp.toString(), qubject.getModifierForProperty(qubjectProp).getName());
			}
			
			//save properties to project root folder
			prop.store(new FileOutputStream(qubject.getBitIdentifier()+".properties"), null);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	private static void saveGlobalParams(String path, ProjectControllerInterface project){
		Properties prop = new Properties();
		prop.setProperty("project_name", project.getProjectName());
		try {
			prop.store(new FileOutputStream(path + "params.properties"), null);
		} catch (FileNotFoundException e) {
			System.err.println("Could not create params.properties");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
