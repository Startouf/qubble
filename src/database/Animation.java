package database;

import java.io.File;

import opengl.AnimationInterface;

public class Animation implements AnimationInterface {
	private final String name;
	private final File file;
	
	public Animation(String name, File file){
		this.name = name;
		this.file = file;
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
