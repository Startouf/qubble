package opengl;

import java.util.Random;

import org.lwjgl.util.Point;

public abstract class AnimationController implements AnimationControllerInterface
{
	/**
	 * Lwjgl point
	 */
	protected Point sourcePos;
	protected Vertex2f sourceVtx;
	/**
	 * A Random used by many things
	 */
	protected static final Random random = new Random();

	/**
	 * Time since start in milliseconds float
	 */
	protected float timeSinceStart;
	
	public AnimationController(Point source){
		this.sourcePos = source;
		this.sourceVtx = new Vertex2f(source.getX(), source.getY());
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
