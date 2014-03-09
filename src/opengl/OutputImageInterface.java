package opengl;


import org.lwjgl.util.Point;

import qubject.AnimationInterface;

public interface OutputImageInterface 
{
	/**
	 * Toggle on/off the grid
	 * @param spacing
	 */
	public void ShowGrid();

	/**
	 * Render the shadow/contour of a qubject
	 * @param qubject
	 */
	public void triggerQubject(Point qubject);
	
	/**
	 * Trigger a QubjectEffect
	 * @param Point the coords of the qubject which must be rendered
	 * TODO : add shape/rotation information 
	 * @param anim the animation to be played
	 */
	public void triggerEffect(Point coords, AnimationInterface animationWhenPlayed);

	/**
	 * We might want to add Qubject-independant Animations later
	 * @param anim
	 */
	public void triggerOtherEffect(AnimationInterface anim);

	/**
	 * This is going to be a tough one to implement
	 * If thread is in pause, signal Thread t that it must awaken
	 */
	public void playPause(Thread t);

	/**
	 * End everything. Project closed or sthing else happend.
	 */
	public void terminate();
	
}
