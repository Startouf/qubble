package database;

import java.io.File;

import audio.SoundEffectInterface;

public enum SoundEffect implements SoundEffectInterface{
	DISTORTION("Distortion", "distortion.se"),
	VOLUME("Volume", "volume.se"),
	PITCH("Hauteur", "pitch.se");
	
	private final String name;
	private final File file;
	
	private SoundEffect(String name, String file){
		this.name = name;
		this.file = new File("/data/sound effect/" + file);
	}

	@Override
	public File getFile() {
		return this.file;
	}

	@Override
	public String getName() {
		return this.name;
	}
}
