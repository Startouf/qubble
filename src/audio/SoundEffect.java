package audio;

import java.io.File;


public class SoundEffect implements SoundEffectInterface{
	
	private final String name;
	private final File file;
	
	public SoundEffect(String name, String file){
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
