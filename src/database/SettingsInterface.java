package database;

import java.io.File;
import java.util.Hashtable;

import opengl.AnimationInterface;
import audio.SoundEffectInterface;
import sequencer.SoundInterface;
import table.Pattern;

public interface SettingsInterface {
	
	//Basic functions of the table
	public double getPeriod();

	//Not really sure about those 2 being here :
	public void PlayPause();
	public void setCurrentTime(double time);
	
	//Pattern Maps
	public Hashtable<Pattern, File> getSoundMap();
	public Hashtable<Pattern, SoundEffectInterface> getYAxisEffectMap();
	public Hashtable<Pattern, AnimationInterface> getAnimationMap();
	

}
