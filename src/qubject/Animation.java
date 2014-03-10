package qubject;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.apache.commons.io.FilenameUtils;

import opengl.AnimationController;
import opengl.AnimationControllerInterface;
import opengl.CannotCompileAnimationException;


public class Animation implements AnimationInterface {
	private final String name;
	private final File dotJavaFile;
	private File dotClassFile;
	private boolean mustBeCompiled = false;
	
	public Animation(String name, File dotJavafile){
		this.name = name;
		this.dotJavaFile = dotJavafile;
	}

	/**
	 * Compilation Overload
	 * @param name
	 * @param dotJavafile
	 * @param mustBeCompiled
	 */
	public Animation(String name, File dotJavafile, boolean mustBeCompiled){
		this.name = name;
		this.dotJavaFile = dotJavafile;
		this.mustBeCompiled = true;
	}
	
	public Animation(String name, File dotJavaFile, File dotClassFile){
		this.name = name;
		this.dotJavaFile = dotJavaFile;
		this.dotClassFile = dotClassFile;
		this.mustBeCompiled = false;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public File getFile() {
		return dotJavaFile;
	}

	@Override
	public boolean mustBeCompiled() {
		return mustBeCompiled;
	}

	@Override
	public File getAnimationControllerDotClass() {
		if (!mustBeCompiled){
			return dotClassFile;
		}
		else{
			//TODO compile the java file
		}
		
		//TODO : return a default AnimationController if it fails ???
		return null;
	}
	
	private String getDotClassFromDotJava(File f){
		return (f.getParent()+FilenameUtils.removeExtension(f.getName()) + ".class");
	}
}
