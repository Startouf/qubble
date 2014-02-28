package opengl;

import org.lwjgl.util.Point;

public abstract class AnimationController implements AnimationControllerInterface
{
	/**
	 * Lwjgl point
	 */
	protected Point sourcePos;
	/**
	 * Time since start in milliseconds float
	 */
	protected float timeSinceStart;
	
	public AnimationController(Point source){
		this.sourcePos = source;
	}

	@Override
	public abstract void load();

	@Override
	public abstract void renderAnimation();

	@Override
	public abstract boolean updateAnimation(float dt);

	@Override
	public void setAnimationOrigin(Point pos) {
		this.sourcePos = pos;
	}
	
	@Override
	public abstract void destroy();
}
