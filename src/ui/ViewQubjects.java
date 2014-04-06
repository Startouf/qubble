package ui;

import javax.swing.JPanel;

import qubject.MediaInterface;
import qubject.QubjectModifierInterface;
import qubject.QubjectProperty;
/**
 * @author duchon
 * This class should be implemented by all panels that allow the user to 
 * display or alter Qubject Properties
 *
 */
public abstract class ViewQubjects extends JPanel {
	protected MediaInterface activeQubject;
	protected QubjectProperty activeProperty = QubjectProperty.SAMPLE_WHEN_PLAYED;

	/**
	 * Change the active Qubject that will be modified by the Palettes
	 * The subclasses should Override this method to refresh their display
	 * @param selectedQubject
	 */
	public abstract void setActiveQubject(MediaInterface selectedQubject);
	
	/**
	 * Refresh the display (highlight active property ?)
	 * @param property
	 */
	public abstract void setActiveProperty(QubjectProperty property);
	
	/**
	 * Refresh the display (change JLabels, etc..)
	 * @param modifier
	 */
	public abstract void setModifierOfActiveProperty(QubjectModifierInterface modifier);

	public MediaInterface getActiveQubject(){
		return activeQubject;
	}
	
	public QubjectProperty getActiveProperty(){
		return activeProperty;
	}
}
