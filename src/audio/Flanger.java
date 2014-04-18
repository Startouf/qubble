package audio;

public class Flanger extends SoundEffect {

	int LFOfrequency;
	int j;
	public Flanger(int amount) {
		super(EffectType.Flanger, amount);
		j = 0;
		LFOfrequency = 50; //  * 1/100 Hz
	}

	@Override
	public void effectNextChunk(SampleControllerInterface sc, int size) {
		SampleController s = (SampleController) sc;
		int offSet = 0;
		for (int i = s.getRelativeCursor(); i < s.getRelativeCursor() + size && i + 2*amount < s.size(); i++) {
			
			offSet = (int)(2*amount * Math.sin((2*Math.PI*j * LFOfrequency)/4410000));
			s.set(i, (s.get(i) + s.get(i+offSet))/2);
			j++;
			
		}
	}

}
