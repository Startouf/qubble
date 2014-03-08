package ui;

import javax.swing.JPanel;

import qubject.MediaInterface;
import qubject.QubjectModifierInterface;
import qubject.QubjectModifiers;
/**
 * @author duchon
 * This class should be implemented by all panels that allow the user to 
 * display or alter Qubject Properties
 *
 */
public abstract class ViewQubjects extends JPanel {
	protected MediaInterface activeQubject;
	protected QubjectModifiers activeProperty;

	/**
	 * Change the active Qubject that will be modified by the Palettes
	 * The subclasses should Override this method to refresh their display
	 * @param selectedQubject
	 */
	public abstract void setActiveQubject(MediaInterface selectedQubject);
	
	public abstract void setActiveModifier(QubjectModifierInterface modifier);

	public MediaInterface getActiveQubject(){
		return activeQubject;
	}
	
	public QubjectModifiers getActiveProperty(){
		return activeProperty;
	}
}
