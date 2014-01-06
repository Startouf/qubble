package database;

import java.io.File;

import audio.SampleInterface;

public enum Sample implements SampleInterface{
	DONG("Dong", "dong"),
	DING("Ding", "ding"),
	PAF("Paf", "paf"),
	CRAC("Crac", "crac");
	
	private String name;
	private File file;
	
	private Sample(String name, String file){
		this.name = name;
		this.file = new File("/data/sample/" + file);
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
