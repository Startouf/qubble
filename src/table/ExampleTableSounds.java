package table;

import java.io.File;
import java.util.Hashtable;

import database.Pattern;

import audio.ExampleWAVReading;

import ui.TableViewPanel;

public class ExampleTableSounds
{
	private static final  Hashtable<Pattern, File> sounds = new Hashtable<Pattern, File>();
	private static final  String SOUNDSPATH = "data/sound/";
	static{
		sounds.put(Pattern.SQUARE, new File(SOUNDSPATH + "hit-01.wav"));
		sounds.put(Pattern.CIRCLE, new File(SOUNDSPATH + "hit-02.wav"));
	}
	
	public static void playSound(Pattern figure){
		ExampleWAVReading.ReadWav(sounds.get(figure));
	}
}
