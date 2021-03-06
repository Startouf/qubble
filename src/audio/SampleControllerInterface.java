package audio;


/**
 * The aim of this interface is to provide a way to alter a streamed sound
 * (Increase volume, trigger some effect, etc...)
 * The sample controller is a container for the sound played
 * @author Cyril
 *
 */
public interface SampleControllerInterface
{
	public int getOffset();
	public void effectNextChunk(int size);
	public int[] getNextArray(int cursor, int bufferSize);
	public int getNext();
	public float getVolume();
	public void changeVolume(float amount);
	public void addEffect(SoundEffectInterface effect);
}
