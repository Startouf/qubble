package ui;

import javax.swing.JPanel;

import qubject.MediaInterface;
import qubject.QubjectModifiers;
/**
 * @author duchon
 * This class should be implemented by all panels that allow the user to 
 * display or alter Qubject Properties
 *
 */
public abstract class ViewQubjects extends JPanel {
	protected MediaInterface activeQubject;
	protected QubjectModifiers activeProperty = QubjectModifiers.sampleWhenPlayed;

	/**
	 * Change the active Qubject that will be modified by the Palettes
	 * The subclasses should Override this method to refresh their display
	 * @param selectedQubject
	 */
	public abstract void setActiveQubject(MediaInterface selectedQubject);
	
	public abstract void setActiveProperty(QubjectModifiers modifier);

	public MediaInterface getActiveQubject(){
		return activeQubject;
	}
	
	public QubjectModifiers getActiveProperty(){
		return activeProperty;
	}
}
