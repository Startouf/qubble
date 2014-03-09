package audio;

public class Flanger extends SoundEffect {

	public Flanger(int amount) {
		super(EffectType.flanger, amount);
	}

	@Override
	public void effectNextChunk(SampleControllerInterface sc, int size) {
		SampleController s = (SampleController) sc;
		for (int i = s.getRelativeCursor(); i < s.getRelativeCursor() + size && i + amount < s.size(); i++) {
			s.set(i, (s.get(i) + s.get(i+amount))/2);
		}
	}

}
