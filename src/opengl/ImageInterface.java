package opengl;

import qubject.AnimationInterface;
import qubject.Qubject;

public interface ImageInterface
{
	public void renderCursorAt(float time);
	public void ShowGrid(float spacing);
	public void renderQubject(Qubject qubject);
	public void triggerEffect(Qubject qubject, AnimationInterface anim);

	    //We might want to add Qubject-independant Animations later
	public void triggerOtherEffect(AnimationInterface anim);
}
