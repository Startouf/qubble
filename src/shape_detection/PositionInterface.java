package shape_detection;

import java.awt.Point;
import java.util.Hashtable;

import database.Pattern;


public interface PositionInterface {
	
	//Position of patterns (center)
	public Hashtable<Pattern, Point> getPatternPositions();
	
	//Make sure the image is a square and not out of shape
	public void Calibrate();

}
