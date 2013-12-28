package sequencer;

import java.awt.Point;
import java.util.Hashtable;

import table.Pattern;

public interface SoundInterface {
	
	Hashtable<Pattern, Point> getPositions();
	Hashtable<Pattern, Double> getRotations();

}
