package qubject;

import org.lwjgl.util.Point;
import java.io.File;


/**
 * @author Cyril
 * Note : interface should be split 
 * PatternInterface should keep only methods related to the detection of the QR-code 
 * CubeInterface should have the methods relative to a given user configuration
 *
 */
public interface QRInterface {
	
	/**
	 * QR-code 16 bit identifier
	 * @return
	 */
	public int getBitIdentifier();

	/**
	 * Notclear whether this value is a pixel (int) value, a float
	 * ... leave it a classical java Point (double) for now 
	 * @return Pixel coordinates of the point on the table
	 */
	public Point getCoords();

	/**
	 * Set the position of the QUbject on the table
	 * @param pos currently a Java.awt.Point
	 */
	public void setCoords(Point pos);

	/**
	 * The relative rotation in float radians
	 * @return
	 */
	public float getRotation();
	
	/**
	 * Adjusts the relative rotation in float radians
	 * @param relativeRotation
	 */
	public void setRotation(float floatRadians);
	
}
