package audio;


public class Delay extends SoundEffect {

	private int rate;
	private int decay;
	private int feedback;
	
	public Delay(int amount) {
		
		super(EffectType.Delay, amount);
		rate = 15000;
		decay = 2;
		feedback = 5;
		
	}

	@Override
	public void effectNextChunk(SampleControllerInterface sc, int size) {
		SampleController s = (SampleController) sc;
		//*//Comme ça ça marche mais c'est pas idéal.
		
		for (int i = s.getRelativeCursor(); i < s.getRelativeCursor() + size && i < s.size(); i++) {
			for (int j = 1; j < feedback; j++) {
				s.addTo(j*rate + i, s.get(i)/(j*decay));
			}
		}
		//*/
	}

}
