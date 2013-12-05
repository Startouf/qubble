package table;

import java.io.File;
import java.util.Hashtable;

import audio.ExampleWAVReading;

import ui.TableViewPanel;

public class ExampleTableSounds
{
	private static final  Hashtable<Figures, File> sounds = new Hashtable<Figures, File>();
	private static final  String SOUNDSPATH = "data/sound/";
	static{
		sounds.put(Figures.SQUARE, new File(SOUNDSPATH + "hit-01.wav"));
		sounds.put(Figures.CIRCLE, new File(SOUNDSPATH + "hit-02.wav"));
	}
	
	public static void playSound(Figures figure){
		ExampleWAVReading.ReadWav(sounds.get(figure));
	}
}
