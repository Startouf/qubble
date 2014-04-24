package audio;
/*
import org.apache.commons.math3.complex.*;
import org.apache.commons.math3.transform.*;
*/
public class LPFilter extends Filter {

	public LPFilter(int amount) {
		super(amount);
	}


	@Override
	protected float[] calch(int N) {
		float fracFreq = cutoff/44100;
		float[] res = new float[2*N + 1];
		res[N] = (2 * fracFreq);
		for (int n = 0; n < res.length; n++) {
			if (n != N) {
				res[n] = (float)(Math.sin(2*Math.PI*fracFreq*n)/(Math.PI * n));
			}
		}
		
		return res;
	}
	
}
