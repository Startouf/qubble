package opengl;

import org.lwjgl.util.Point;

public abstract class AnimationController implements AnimationControllerInterface
{
	protected Point sourcePos;
	protected float timeSinceStart;
	
	public AnimationController(Point source){
		this.sourcePos = source;
	}

	@Override
	public abstract void load();

	@Override
	public abstract void renderAnimation();

	@Override
	public abstract void updateAnimation(float dt);

	@Override
	public void setAnimationOrigin(Point pos) {
		this.sourcePos = pos;
	}

}
