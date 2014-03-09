package audio;

public class Volume extends SoundEffect {

	public Volume(int amount) {
		super(EffectType.Volume, amount);
	}

	@Override
	public void effectNextChunk(SampleControllerInterface sc, int size) {
		SampleController s = (SampleController) sc;
		for (int i = s.getRelativeCursor(); i < s.getRelativeCursor()+size; i++) {
			s.multiply(i, amount);
		}

	}

}
