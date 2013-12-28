package database;

import java.io.File;
import java.util.ArrayList;

/**
 * @author Cyril
 * Allow to retrieve Pattern Info, and user settings
 *
 */
public interface DatabaseInterface {
	//Pattern recognition 
	public ArrayList<ImagePatternInterface> getImagePatterns();
	
	//UI settings
	public SettingsInterface getPreviousSettings();
	public SettingsInterface getSettings(File file);
	public void saveSettings(SettingsInterface s);
	
}
