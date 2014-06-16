package sequencer;

import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.Properties;

import org.lwjgl.util.Point;

import audio.SampleControllerInterface;
import qubject.MediaInterface;
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
	 * (may play detected animation/sound)
	 * @param bitIdentifier its ID
	 * @param position its position
	 */
	public void QubjectDetected(int bitIdentifier, imageObject.Point position);

	/**
	 * When a Qubject is moved on the table
	 * (may play a move animation/sound)
	 * @param bitIdentifier
	 * @param position
	 */
	public void QubjectHasMoved(int bitIdentifier, imageObject.Point position);

	/**
	 * 
	 * @param dR a float radian between 0 and 2.pi
	 */
	public void QubjectHasTurned(int bitIdentifier, float dR);

	/**
	 * When a Qubject is no longer on the table
	 * (may play a "missing" sound/animation)
	 * @param bitIdentifier its ID
	 */
	public void QubjectRemoved(int bitIdentifier);
	
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
	public void prepare();

	/**
	 * Toggle the grid
	 */
	public void toggleGrid();
	
	/**
	 * Used in the GUI to show object position on the grid
	 * Convention : Dimension(-1, -1) for NOT_ON_TABLE
	 * @param qubject
	 * @return A dimension
	 */
	public Dimension getPosition(MediaInterface qubject);
	
	/**
	 * Quick restart (keep Qubject config)
	 */
	public void panic();
	
	/**
	 * In case the Qubble is desynced (openGL/audio)
	 */
	public void resynchronize();

	/**
	 * Mute/unmute
	 */
	public void mute();
	
	/**
	 * Asks the Player to record in file f
	 * @param f the file
	 */
	public void startRecording(File f);
	
	/**
	 * Stop recording and save the file
	 */
	public void stopRecording();
}
