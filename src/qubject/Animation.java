package qubject;

import java.io.File;


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
