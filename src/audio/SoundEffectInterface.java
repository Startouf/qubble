package audio;

import qubject.QubjectModifierInterface;


public interface SoundEffectInterface extends QubjectModifierInterface {

	public int getType();
	public int getAmount();
	public void setAmount(int i);
	public void effectNextChunk(SampleControllerInterface sc, int size);
}
