package audio;

public class PitchShifter extends SoundEffect {

	int ruban[];
	int posWrite;
	float pos1;
	float pos2;
	int tailleRuban;
	float vitesse;
	float volume1;
	float volume2;
	
	
	public PitchShifter(int amount) {
		super(EffectType.Shifter, amount);
		tailleRuban = 2000;
		ruban = new int[tailleRuban];
		for (int i = 0; i < tailleRuban; i++) {
			ruban[i] = 0;
		}
		posWrite = 0;
		pos1 = 0;
		pos2 = tailleRuban/2;
		vitesse = (float)amount / 50;
		volume1 = 0;
		volume2 = 1;
	}

	@Override
	public void effectNextChunk(SampleControllerInterface sc, int size) {
		vitesse = (float)amount / 50;
		
		SampleController s = (SampleController) sc;
		
		for (int i = s.getRelativeCursor(); i < s.getRelativeCursor() + size && i < s.size(); i++) {
			
			ruban[posWrite] = s.get(i);
			
			posWrite = (posWrite + 1) % tailleRuban;
			
			
			s.set(i, (int)(get(ruban, pos1)*volume1 + get(ruban, pos2)*volume2) );
			
			pos1 = (pos1 + vitesse) % tailleRuban;
			pos2 = (pos2 + vitesse) % tailleRuban;
			
			pos1 = pos1<0?(tailleRuban + pos1):pos1;
			pos2 = pos2<0?(tailleRuban + pos2):pos2;
			
			float distance1 = posWrite - pos1;
			distance1 = distance1>0?distance1:-distance1;
			float distance2 = posWrite - pos2;
			distance2 = distance2>0?distance2:-distance2;
			float gap = distance1 - distance2;
			int transitionSize = 20;
			float vgap = gap>0?gap:-gap;
			if (vgap < transitionSize) {
				volume1 = (float) (gap / (transitionSize));
				
			}
			else if (gap > 0) volume1 = 1;
			else volume1 = 0;
			//volume1 = distance1 / (float)(tailleRuban/2);
			//System.out.println(volume1);
			volume2 = 1 - volume1;
		}

	}
	
	public static int get(int[] array, double index) {
		if (index <= array.length - 1 && index >= 0) {
			int floor = (int) index;
			floor = floor % array.length;
			int ceil = floor + 1;
			ceil = ceil % array.length;
			double frac = index - floor;
			double y = (array[ceil] - array[floor])*frac + array[floor];
			//System.out.println("index : " + index + ", floor : " + floor + ", frac : " + frac + ", samples.get(ceil) : " + samples.get(ceil)
				//	 + ", samples.get(floor) : " + samples.get(floor) + ", y : " + y);
			return (int) y;
		}
		else return 0;
	}
	
	public static int get(int[] array, float index) {
		return get(array, (double) index);
	}
	
}
