package qubject;

import java.awt.Image;

/**
 * @author duchon
 * Superinterface for Qubject modifiers (Sample, Animations) IMPLEMENTATIONS
 * (i.e. the files/code for a specific modifier)
 * For Qubject Behaviours (ON_DETECTION, ON_PLAY), see QubjectProperties
 *
 */
public interface QubjectModifierInterface {
	
	public String getName();
	
	public Image getImage();
	
}
