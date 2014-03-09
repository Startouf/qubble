package audio;

public class Distortion extends SoundEffect {

	private int clip;
	
	public Distortion(int amount) {
		super(EffectType.distortion, amount);
		clip = 10000;
	}

	@Override
	public void effectNextChunk(SampleControllerInterface sc, int size) {
		SampleController s = (SampleController) sc;
		int coef = amount/2;
		float cut = 10*(clip/coef);
		//System.out.println("cut : " + cut);
		for (int i = s.getRelativeCursor(); i < s.getRelativeCursor() + size && i < s.size(); i++) {
			int x = s.get(i) % (int)(2*cut);
			//System.out.println("x : " + x);
			if (x < cut && x > -cut) {
				//res.add(coef * samples.get(i));
				s.set(i, (coef * x) / 10);
			}
			else if (x >= cut) {
				s.set(i, 2*clip - (coef*x)/10);
				//res.add(clip);
			}
			else {
				s.set(i,-2*clip - (coef*x)/10);
				//res.add(-clip);
			}
		}

	}

}
