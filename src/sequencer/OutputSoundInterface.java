package sequencer;

import java.awt.Point;
import java.util.Hashtable;

import database.Pattern;


public interface OutputSoundInterface {
	
	Hashtable<Pattern, Point> getPositions();
	Hashtable<Pattern, Double> getRotations();

}
