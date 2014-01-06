package movement;

import java.awt.Point;
import java.util.Hashtable;

import database.Pattern;


public interface MovementInterface {
	
	public Hashtable<Pattern, Point> getPatternsRelativeRotation();
}
