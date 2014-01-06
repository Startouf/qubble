package sequencer;

import java.awt.Point;
import java.util.Hashtable;

import database.Pattern;


public interface OutputImageInterface {
	
	float getCursorPosition();
	
	Hashtable<Pattern, Point> getPosition();
	Hashtable<Pattern, Double> getRotations();
}
