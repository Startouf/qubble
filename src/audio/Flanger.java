package audio;

public class Flanger extends SoundEffect {

	int LFOfrequency = 10;
	
	public Flanger(int amount) {
		super(EffectType.Flanger, amount);
	}

	@Override
	public void effectNextChunk(SampleControllerInterface sc, int size) {
		SampleController s = (SampleController) sc;
		int offSet = 0;
		for (int i = s.getRelativeCursor(); i < s.getRelativeCursor() + size && i + 2*amount < s.size(); i++) {
			
			offSet = (int)(2*amount * Math.sin((2*Math.PI*i)/44100));
			s.set(i, (s.get(i) + s.get(i+offSet))/2);
			
		}
	}

}
