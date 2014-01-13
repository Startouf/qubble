package debug;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class FichierProp
{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	Properties prop = new Properties();
	 
	try {
		//set the properties value
		prop.setProperty("database", "localhost");
		prop.setProperty("dbuser", "mkyong");
		prop.setProperty("dbpassword", "password");

		//save properties to project root folder
		prop.store(new FileOutputStream("config.properties"), null);


	
    //load a properties file
		prop.load(new FileInputStream("config.properties"));
		

          //get the property value and print it out
        System.out.println(prop.getProperty("database"));
		System.out.println(prop.getProperty("dbuser"));
		System.out.println(prop.getProperty("dbpassword"));
		
		
		} catch (IOException ex) {
		ex.printStackTrace();
    }
	}


}
