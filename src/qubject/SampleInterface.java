package qubject;

import java.io.File;

public interface SampleInterface extends QubjectModifierInterface
{
	/**
	 * 
	 * @return the .wav file
	 */
	public File getFile();
	
	/**
	 * 
	 * @return the duration in float milliseconds
	 */
	public float getDuration();
	
	//Later, we might also want some getTempo() method  
}