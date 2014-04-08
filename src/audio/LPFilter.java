package audio;
/*
import org.apache.commons.math3.complex.*;
import org.apache.commons.math3.transform.*;
*/
public class LPFilter extends SoundEffect {

	float[] a;
	float[] b;
	float cutoff;
	
	public LPFilter(int amount) {
		super(EffectType.LPFilter, amount);
		cutoff = 100*amount;
		calcCoefs();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void effectNextChunk(SampleControllerInterface sc, int size) {

		SampleController s = (SampleController) sc;
		
		for (int i = s.getRelativeCursor(); i < s.getRelativeCursor() + size && i < s.size(); i++) {
			for (int j = 0; j < a.length; j++) {
				s.set(i, (int)(s.getEffected(i) - b[j]*s.getEffected(i-j-1)));
			}
			for (int j = 0; j < a.length; j++) {
				s.set(i, (int)(s.getEffected(i) + a[j]*s.getEffected(i-j)));
			}
			//y_n = -b_0*y_{n-1} - ... - b_{q-1}*y_{n-q} + a_0*x_n + a_1*x_{n-1} + ... + a_p*x_{n-p}
		}
	}

	private void calcCoefs() {
		float fracFreq = cutoff / 44100;
		float x = (float) Math.exp(-2 * Math.PI * fracFreq);
		a = new float[] { (float) Math.pow(1 - x, 4) };
		b = new float[] { 4 * x, -6 * x * x, 4 * x * x * x, -x * x * x * x };
	}
	
}
