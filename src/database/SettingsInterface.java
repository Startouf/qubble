package database;

import java.util.ArrayList;
import java.util.Properties;

/**
 * @author Cyril
 * All settings are contained in the Pattern classes
 *
 */
public interface SettingsInterface {
	
	public ArrayList<Qubject> getAllQubjects();
	public ArrayList<Qubject> getQubjectsOnTable();
	
	public void newQubjectOnTable(QRInterface pattern);
	public void patternRemovedFromTable(QRInterface pattern);
}
