package qubject;

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
	 * Allows to quickly make a list of Patterns on table
	 * @return
	 */
	public boolean isHere();
	
	/**
	 * QR-code 16 bit identifier
	 * @return
	 */
	public int getBitIdentifier();
	
	/**
	 * The name corresponds to a human-distinction sign 
	 * (for example a (Qubject) Cube with stars drawn on it would be called "Star")
	 * @return
	 */
	public String getName();

}
