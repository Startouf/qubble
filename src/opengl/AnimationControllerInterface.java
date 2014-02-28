package opengl;

import org.lwjgl.util.Point;

/**
 * @author Cyril
 * A sort of API to program Animation effects
 *
 */
public interface AnimationControllerInterface
{
	/**
	 * Load required VBOs, DisplayLists, etc... into CPU/GPU memory
	 */
	public void load();
	
	/**
	 * Render the animation
	 */
	public void renderAnimation();

	/**
	 * Should the time passed since last update be sent via parameter ?
	 * PRO : easier to handle Play/Pause
	 * CON : ... ?
	 * @param dt Time in float-milliseconds 
	 * @return true if the animation is not over, false if this controller can be deleted safely. 
	 */
	public boolean updateAnimation(float dt);

	/**
	 * If the qubject is moved during the animation, move the animation ?
	 * @param pos point lwjgl (float/double ?)
	 */
	public void setAnimationOrigin(Point pos);
	
	/**
	 * Forces the release of any GPU memory held by this animation, given any context
	 * This method is called by main program when Animation is completed or when project is closed
	 */
	public void destroy();
}
