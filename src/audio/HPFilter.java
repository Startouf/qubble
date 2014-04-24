package audio;

public class HPFilter extends Filter {

	public HPFilter(int amount) {
		super(amount);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected float[] calch(int N) {
		float fracFreq = cutoff/44100;
		float[] res = new float[2*N + 1];
		res[N] = (-2 * fracFreq);
		for (int n = 0; n < res.length; n++) {
			if (n != N) {
				res[n] = (float)(-Math.sin(2*Math.PI*fracFreq*n)/(Math.PI * n));
			}
		}
		res[0] = 1;
		return res;
	}

}
