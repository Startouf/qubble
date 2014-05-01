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
	 * The position of the qubject in the openGL world (lwjgl point)
	 * @return Qubject center position in the GL world (lwjgl Point)
	 */
	public Point getCoords();

	/**
	 * Set the position of the Qubject in the GL world
	 * @param pos lwjgl Point
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
