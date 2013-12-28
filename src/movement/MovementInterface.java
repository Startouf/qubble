package movement;

import java.awt.Point;
import java.util.Hashtable;

import table.Pattern;

public interface MovementInterface {
	
	public Hashtable<Pattern, Point> getPatternsRelativeRotation();
}
