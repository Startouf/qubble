package audio;


public class Delay extends SoundEffect {

	private int rate;
	private float decay;
	private int feedback;
	
	public Delay(int amount) {
		super(EffectType.Delay, amount);
		
		/*
		rate = 1000;
		decay = (float)1.6;
		feedback = 7;
		//son metalique 
		 *//*
		rate = 2000;
		decay = (float)1.5;  //reverb
		feedback = 10;
		*/
		rate = 15000;
		decay = (float)3;  //echo
		feedback = 3;
	}

	@Override
	public void effectNextChunk(SampleControllerInterface sc, int size) {
		SampleController s = (SampleController) sc;
		
		for (int i = s.getRelativeCursor(); i < s.getRelativeCursor() + size && i < s.size(); i++) {
			
			for (int j = 1; j <= feedback; j++) {
				s.addTo(j*rate + i, (int)(s.get(i)/Math.pow(decay, j)));
			}
		
		}
		
	}

}
