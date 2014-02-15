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
	private MediaInterface activeQubject;
	private QubjectModifiers activeModifier;
	
	/**
	 * Change the active Qubject that will be modified by the Palettes
	 * The subclasses should Override this method to refresh their display
	 * @param selectedQubject
	 */
	public void setActiveQubject(MediaInterface selectedQubject) {
		this.activeQubject = selectedQubject;
	}

	public MediaInterface getActiveQubject() {
		//TODO get/set
		return activeQubject;
	}
	
	public void setActiveQubject(QubjectModifiers modifier) {
		this.activeModifier= modifier;
	}
	
	public QubjectModifiers getActiveQubjectModifier(){
		//TODO get/set
		return activeModifier;
	}
}
