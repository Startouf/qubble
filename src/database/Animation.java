package database;

import java.io.File;

import opengl.AnimationInterface;

public class Animation implements AnimationInterface {
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
