package audio;

public class HPFilter extends Filter {

	public HPFilter(int amount) {
		super(amount);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * la réponse impulsionnelle est un dirac moins un sinus cardinal (toujours fenetré par une fenetre
	 * rectugulaire de largeur 2*N + 1 et décalé de N) Ainsi en fréquence, on aura bien une multiplication
	 * par 1 moins une porte, soit un filtrage passe haut
	 */
	@Override
	protected float[] calch(int N) {
		float fracFreq = cutoff/44100;
		float[] res = new float[2*N + 1];
		res[N] = (-2 * fracFreq);
		for (int n = 0; n < res.length; n++) {
			if (n != N) {
				res[n] = (float)(-Math.sin(2*Math.PI*fracFreq*(n-N))/(Math.PI * (n-N)));
			}
		}
		res[0] = 1;
		return res;
	}

}
