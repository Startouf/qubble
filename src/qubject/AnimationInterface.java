package qubject;

import java.io.File;

import opengl.AnimationController;
import opengl.AnimationControllerInterface;


public interface AnimationInterface extends QubjectModifierInterface {
	/**
	 * Set to false if the animation must be compiled
	 * @return
	 */
	public boolean mustBeCompiled();

	/**
	 * Returns an AnimationController file
	 * @return
	 */
	public File getAnimationControllerDotClass();
}
