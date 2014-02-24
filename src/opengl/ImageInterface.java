package opengl;

import java.awt.Point;

import qubject.AnimationInterface;

public interface ImageInterface
{
	/**
	 * Toggle on/off the grid
	 * @param spacing
	 */
	public void ShowGrid(float spacing);
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
	public void triggerEffect(Point qubjectCoords, AnimationInterface anim);

	/**
	 * We might want to add Qubject-independant Animations later
	 * @param anim
	 */
	public void triggerOtherEffect(AnimationInterface anim);
	
	public void playPause();
}
