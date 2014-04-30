package audio;

public class PitchShifter extends SoundEffect {

	int ruban[]; //mémoire circulaire
	int posWrite; //position de pointeur d'écriture
	float pos1; //position du pointeur de lecture 1
	float pos2;	//position du pointeur de lecture 2
	int tailleRuban; //taille de ruban[]
	float vitesse; //vitesse en nb d'échantillons/boucle de déplacement des pointeurs de lecture
	float volume1;	//volume a laquelle l'échantillon lu par le pointeur 1 est joué
	float volume2;	//  -----------------------------------------------  2 -------
	
	/**
	 * 
	 * On dispose d'un ruban circulaire <----------------->
	 * un pointeur d'écriture écrit les échantillons tels qu'ils arrivent
	 * ceci seront lus par deux pointeurs de lecture qui tournent à une vitesse différente du pointeur d'écriture
	 * sur le ruban. Ainsi, on obtient une variation du pitch du sample, sans variation de la durée.
	 */
	
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
			
			//--------- on écrit l'échantillon à la position posWrite -------
			ruban[posWrite] = s.get(i);
			
			//--------- on déplace le pointeur d'écriture ------------------
			posWrite = (posWrite + 1) % tailleRuban;
			
			
			//--------- on lit les échantillons aux positions pos1 et pos2, pondérés par volume1 et volume2
			s.set(i, (int)(get(ruban, pos1)*volume1 + get(ruban, pos2)*volume2) );
			
			
			//--------- on déplace les deux pointeurs ---------
			pos1 = (pos1 + vitesse) % tailleRuban;
			pos2 = (pos2 + vitesse) % tailleRuban;
			
			pos1 = pos1<0?(tailleRuban + pos1):pos1;
			pos2 = pos2<0?(tailleRuban + pos2):pos2;
			//---------------------------------------------------
			
			//----------- On effectue le crossfade des deux pointeurs de lecture, en fonction de la distance
			//		des pointeurs de lecture par rapport au pointeur d'écriture.
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
	
	//interpolation linéaire
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
