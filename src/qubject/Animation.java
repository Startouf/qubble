package qubject;

import java.io.File;


public class Animation implements AnimationInterface {
	private final Class<?> controllerClass;
	private final String name;
	private final File dotJavaFile;
	private File dotClassFile;
	
	public Animation(String name, File dotJavafile, Class controllerClass){
		this.name = name;
		this.dotJavaFile = dotJavafile;
		this.controllerClass = controllerClass;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public File getDotJavaFile() {
		return dotJavaFile;
	}

	@Override
	public Class getAnimationControllerClass() {
		return controllerClass;
	}
}
