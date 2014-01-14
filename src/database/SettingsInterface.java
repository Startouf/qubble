package database;

import java.util.ArrayList;
import java.util.Properties;

/**
 * @author Cyril
 * All settings are contained in the Pattern classes
 *
 */
public interface SettingsInterface {
	
	public ArrayList<Pattern> getAllPatterns();
	public ArrayList<Pattern> getPatternsOnTable();
	
	public void newPatternOnTable(PatternInterface pattern);
	public void patternRemovedFromTable(PatternInterface pattern);
}
