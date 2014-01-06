package database;

import java.io.File;

import opengl.AnimationInterface;

public enum Animation implements AnimationInterface {
	FISSURE("fissure", "fissure.anim"),
	WAVE("Onde", "wave.anim"),
	SHRAPNEL("Eclats", "eclats.anim");
	
	private final String name;
	private final File file;
	
	private Animation(String name, String file){
		this.name = name;
		this.file = new File("/data/animation/" + file);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public File getFile() {
		return file;
	}
	
}
