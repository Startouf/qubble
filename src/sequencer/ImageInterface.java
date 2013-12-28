package sequencer;

import java.awt.Point;
import java.util.Hashtable;

import table.Pattern;

public interface ImageInterface {
	
	float getCursorPosition();
	
	Hashtable<Pattern, Point> getPosition();
	Hashtable<Pattern, Double> getRotations();
}
