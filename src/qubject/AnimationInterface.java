package qubject;

import java.io.File;

import opengl.AnimationController;
import opengl.AnimationControllerInterface;


public interface AnimationInterface extends QubjectModifierInterface {

	/**
	 * Returns an AnimationController Class 
	 * @return
	 */
	public Class getAnimationControllerClass();

	/**
	 * Might be useful to save changes (only use the name of the dotJava)
	 * @return dotJavaFile
	 */
	public File getDotJavaFile();
}
