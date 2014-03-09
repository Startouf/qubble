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
	 * When a new Qubject is detected on the table
	 * @param bitIdentifier its ID
	 * @param position its position
	 */
	public void setQubjectOnTable(int bitIdentifier, imageObject.Point position);

	/**
	 * When a Qubject is no longer on the table
	 * @param bitIdentifier its ID
	 */
	public void QubjectGone(int bitIdentifier);
	
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

	/**
	 * Play/Pause. Default = play
	 */
	public void playPause();

	/**
	 * When a project is closed, ask to terminate the Qubble and it's child threads
	 */
	public void close();

	/**
	 * Start the qubble (first click on play)
	 */
	public void start();

	/**
	 * Toggle the grid
	 */
	public void toggleGrid();
}
