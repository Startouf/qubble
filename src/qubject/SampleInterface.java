package qubject;

import java.io.File;

public interface SampleInterface extends QubjectModifierInterface
{
	//Retrieve useful information about the sound file
	public float getDuration();
	
	//Later, we might also want some getTempo() method  
}