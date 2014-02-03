package database;

import java.io.File;

public interface SampleInterface extends QubjectModifierInterface
{
	public File getFile();
	public String getName();

	//Retrieve useful information about the sound file
	public float getDuration();

	//Later, we might also want some getTempo() method  
}