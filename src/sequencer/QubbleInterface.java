package sequencer;

import java.util.ArrayList;
import java.util.Properties;

import audio.SampleControllerInterface;

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
	 * Dit a Qubble si un objet a été placé ou retiré
	 * @param bitIdentifier l'ID 13 bit de l'objet
	 * @param isOnTable true si ajouté sur la table, false si disparu/enlevé
	 */
	public void setQubjectOnTable(int bitIdentifier, boolean isOnTable);
	
	/**
	 * Trigger all the effects of a given Qubject when it is activated by the cursor
	 * (Might be used as a debug function, otherwise this is done by the sequencer)
	 * @param qubject
	 */
	public void playQubject(Qubject qubject);

	/**
	 * Le module audio dit à Qubble quand un son a fini d'être joué
	 * @param sc le controlleur du son joué en question
	 */
	public void soundHasFinishedPlaying(SampleControllerInterface sc);
}
