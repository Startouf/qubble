package database;

import java.io.FileOutputStream;
import java.util.Properties;

/**
 * @author duchon
 * Note : using a static-method-in-interface trick would really be helpful here
 *
 */
public class SaveProject
{
	private Data data;
	
	public void saveTo(String path){
		saveQubjects(path);
		saveGlobalParams(path);
	}
	
	private void saveQubjects(String path){
		String QubjectPath = path + "/qubject";
		for (Qubject qubject : data.getQubjects()){
			saveQubject(qubject, QubjectPath);
		}
	}
	
	private void saveQubject(Qubject qubject, String path){
		Properties prop = new Properties();
		 //TODO
//		try {
//			//set the properties value
//			prop.setProperty("name", qubject.getName());
//			prop.setProperty("bitIdentifier", String.valueOf(qubject.getBitIdentifier()));
//			prop.setProperty("dbpassword", "password");
//
//			//save properties to project root folder
//			prop.store(new FileOutputStream("config.properties"), null);
//		}
//		catch(Exception e){
//			e.printStackTrace();
//		}
	}

	private void saveGlobalParams(String path){
		
	}
}
