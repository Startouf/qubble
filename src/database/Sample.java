package database;

import java.io.File;


public class Sample implements SampleInterface{
	
	private String name;
	private File file;
	
	public Sample(String name, File file){
		this.name = name;
		this.file = file;
	}

	@Override
	public File getFile() {
		return this.file;
	}

	@Override
	public float getDuration() {
		//TODO
		return 60;
	}

	@Override
	public String getName() {
		return this.name;
	}
}
