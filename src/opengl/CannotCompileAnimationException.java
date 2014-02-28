package opengl;

import java.io.File;

public class CannotCompileAnimationException extends Exception {
	private File file;
	
	public CannotCompileAnimationException(File f){
		this.file = f;
	}
	
	public void printError(){
		System.err.println("Cannot compile Animation File " + file.getPath());
	}
}
