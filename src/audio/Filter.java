package audio;

public abstract class Filter extends SoundEffect {
	
	protected float[] h; //h est la réponse impulsionnelle
	protected final int N = 30; //2*N + 1 est la taille de la fenetre
	protected float cutoff; //fréquence de coupure
	
	public Filter(int amount) {
		super(EffectType.LPFilter, amount);
		cutoff = 100*amount;
		//calcCoefs();
		h = calch(N);
		
	}
	
	@Override
	public void effectNextChunk(SampleControllerInterface sc, int size) {

		SampleController s = (SampleController) sc;
		
		for (int i = s.getRelativeCursor(); i < s.getRelativeCursor() + size && i < s.size(); i++) {
			int res = 0;
			/*
			for (int j = 0; j < b.length; j++) {
				res += b[j]*s.getEffected(i - j -1);
				//s.set(i, (int)(s.getEffected(i) - b[j]*s.getEffected(i-j-1)));
			}
			for (int j = 0; j < a.length; j++) {
				res += a[j]*s.get(i - j);
				//s.set(i, (int)(s.getEffected(i) + a[j]*s.get(i-j)));
			}
			s.set(i, res/9);
			
			//y_n = b_0*y_{n-1} + ... + b_{q-1}*y_{n-q} + a_0*x_n + a_1*x_{n-1} + ... + a_p*x_{n-p}
			
			*/
			/*
			final int avg = amount;
			for (int j = -avg ; j <= avg ; j++) {
				res += s.get(j + i);
			}
			s.set(i, res / (2*avg + 1));
			*/
			
			/**
			 * Simple convolution
			 */
			for (int m = 0; m < h.length; m++) {
				res += s.get(i - m)*h[m];
			}
			s.set(i, res);
		}
		
		
		
		
	}
/*
	private void calcCoefs() {
		
		float fracFreq = cutoff / 44100;
		/*
		float x = (float) Math.exp(-2 * Math.PI * fracFreq);
		a = new float[] { (float) Math.pow(1 - x, 4) };
		b = new float[] { 4 * x, -6 * x * x, 4 * x * x * x, -x * x * x * x };
		
		float r2 = (float)Math.sqrt(2);
		//float omega0 = (float)Math.tan(Math.PI*fracFreq);
		a = new float[8];
		a[0] = 1;
		a[1] = 1;
		a[2] = 1;
		a[3] = 1;
		a[4] = 1;
		a[5] = 1;
		a[6] = 1;
		
		b = new float[3];
		b[0] = -(float)0.9 * (1 + r2);
		b[1] = ((float)0.81 * (1 + r2));
		b[2] = -((float)0.729);
		
	}
	*/
	public static int[] convol(int[] a, int[] b) {
		int[] res = new int[a.length + b.length - 1];
		int max = (a.length > b.length)?a.length:b.length;
		for (int n = 0; n < res.length; n++) {
			for (int m = 0; m < max; m++) {
				if (m < a.length) {
					if (m <= n && n - m < b.length) {
						res[n] += a[m] * b[n - m];
					}
					else {
						res[n] += a[m];
					}
				}
				else if (m <= n && n - m < b.length) {
					res[n] += b[n - m];
				}
				else System.out.println("bizarre");
			}
		}
		return res;
	}
	
	protected abstract float[] calch(int N); /* {
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
	*/
	
	@Override
	public void setAmount(int amount) {
		this.amount = amount>100?100:amount;
		this.amount = amount<0?0:amount;
		cutoff = 100*(this.amount);
		h = calch(N);
	}

}
