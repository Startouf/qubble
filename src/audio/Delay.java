package audio;


public class Delay extends SoundEffect {

	private int rate;
	private int decay;
	private int feedback;
	
	public Delay(int amount) {
		
		super(SoundEffect.delay, amount);
		rate = 15000;
		decay = 4;
		feedback = 2;
		
	}

	@Override
	public void effectNextChunk(SampleControllerInterface sc, int size) {
		SampleController s = (SampleController) sc;
		//*//Comme ça ça marche mais c'est pas idéal.
		
		for (int i = s.getRelativeCursor(); i < s.getRelativeCursor() + size; i++) {
			for (int j = 1; j < feedback; j++) {
				s.addTo(j*rate + i, s.get(i)/(j*decay));
			}
		}
		//*/
	}

}
