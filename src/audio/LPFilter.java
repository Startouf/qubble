package audio;
/*
import org.apache.commons.math3.complex.*;
import org.apache.commons.math3.transform.*;
*/
public class LPFilter extends Filter {

	public LPFilter(int amount) {
		super(amount);
	}

	/**
	 * 
	 * Pour un filtre passe bas, on utilise la méthode de la fenetre :
	 * la réponse impulsionnelle est un sinus cardinal, décalé de N pour qu'il soit causal.
	 * 
	 */
	@Override
	protected float[] calch(int N) {
		float fracFreq = cutoff/44100;
		float[] res = new float[2*N + 1];
		res[N] = (2 * fracFreq);
		for (int n = 0; n < res.length; n++) {
			if (n != N) {
				res[n] = (float)(Math.sin(2*Math.PI*fracFreq*(n-N))/(Math.PI * (n-N)));
			}
		}
		
		return res;
	}
	
}
