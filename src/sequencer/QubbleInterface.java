package sequencer;

import java.util.ArrayList;
import java.util.Properties;

import qubject.QRInterface;
import qubject.Qubject;

/**
 * @author Cyril
 * All settings for a given project :
 * 
 *
 */
public interface QubbleInterface {

	/**
	 * 
	 * @return list of all loaded Qubjects
	 */
	public ArrayList<Qubject> getAllQubjects();
	
	/**
	 * 
	 * @return list of Qubjects on the table that should be processed by the sequencer
	 */
	public ArrayList<Qubject> getQubjectsOnTable();
	
	/**
	 * Add detected Qubject to activeQubjectList
	 * @param qubject The detected qubject 
	 */
	public void newQubjectOnTable(QRInterface qubject);

	/**
	 * Remove given Qubject to active QubjectList
	 * The method checks if the Qubject is on the list
	 * @param qubject Absent qubject
	 */
	public void qubjectRemovedFromTable(QRInterface qubject);
}
