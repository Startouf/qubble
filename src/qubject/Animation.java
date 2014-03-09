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
	
	public Animation(String name, File file){
		this.name = name;
		this.dotJavaFile = file;
	}

	/**
	 * Compilation Overload
	 * @param name
	 * @param file
	 * @param mustBeCompiled
	 */
	public Animation(String name, File file, boolean mustBeCompiled){
		this.name = name;
		this.dotJavaFile = file;
		this.mustBeCompiled = mustBeCompiled;
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
	public AnimationControllerInterface getAnimationController() {
		if (!mustBeCompiled){
			try {
				return (AnimationControllerInterface) ClassLoader.getSystemClassLoader().loadClass(dotClassFile.getPath()).newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Try to ccompile the Java file !!

			}
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
