package audio;

import java.io.File;
import java.util.ArrayList;

import sequencer.*;
import qubject.*;

public class SampleController implements SampleControllerInterface {

	private static final boolean DEBUG = false;
	int offSet; //indice du premier échantillon dans la boucle principale
	int relativeCursor; //echantillon qui sera renvoyé par la fonction getNext.
	ArrayList<Integer> samples;
	ArrayList<Integer> effected;
	private ArrayList<SoundEffect> soundEffects;
	Player player;
	float volume;
	QubbleInterface qi;
	Sample sample;
	
	public SampleController(int offset, File file, QubbleInterface qi, Player player, Sample sample) {
		this.sample = sample;
		this.player = player;
		this.qi = qi;
		relativeCursor = 0;
		offSet = offset;
		volume = 100;
				
		soundEffects = new ArrayList<SoundEffect>();		
		
		try {
			samples = Player.hashTable.get(sample);
			if (samples == null) {
				System.out.println("Acces au disque !");
				samples = AudioUtility.getSamples(file);
				Player.hashTable.put(sample, samples);
			}
			effected = (ArrayList<Integer>)samples.clone();
			//System.out.println("samples.size() : " + samples.size());
		} catch (Exception e) {
			System.out.println("In SampleController Constructor : " + e.getMessage());
		}
		
	}
	/**
	 * les deux méthodes suivante servent plus
	 */
	@Override
	public int getOffset() {
		
		return offSet;
	}
	
	@Override
	public int[] getNextArray(int cursor, int bufferSize) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * Les deux suivantes non plus, le volume est géré comme un effet.
	 */
	@Override
	public float getVolume() {
		// TODO Auto-generated method stub
		return volume;
	}
	
	@Override
	public void changeVolume(float amount) {
		
		this.volume += amount;
		if (volume > 100) {
			volume = 100;
		}
		else if (volume < 0) {
			volume = 0;
		}
	}
	
	/**
	 * 
	 * Copie les échantillons originaux, puis applique tous les effets stockés dans soundEffects
	 */
	@Override
	public void effectNextChunk(int size) {
		ArrayList<Integer> samplesCopy = (ArrayList<Integer>)samples.clone();
		for (int i = 0; i < soundEffects.size(); i++) {
			soundEffects.get(i).effectNextChunk(this, size);
			samples = (ArrayList<Integer>)effected.clone();
		}
		samples = (ArrayList<Integer>)samplesCopy.clone();
	}
	
	/**
	 * 
	 * renvoie l'échantillon suivant. Si on est arrivé au bout, on le signal au player et à la qubble
	 */
	@Override
	public int getNext() {
		//System.out.println("samples.size(bite) : " + samples.size() + "; relativeCursor : " + relativeCursor);
		if (relativeCursor < effected.size()) {
			int res = (effected.get(relativeCursor));
			relativeCursor++;
			//System.out.println(relativeCursor);
			return res;
		}
		else {
			if (DEBUG)
				System.out.println(this.sample.getName() + " has finished playing");
			if (qi != null) {
				qi.soundHasFinishedPlaying(this);
			}
			player.hasFinishedPlaying(this);
			return 0;
		}
	}
	
	/**
	 * 
	 * ajoute un effet.
	 */
	public void addEffect(SoundEffectInterface effect) {
		soundEffects.add((SoundEffect)effect);
	}
	
	/**
	 * 
	 * Bon, bin, pas grand chose à dire
	 */
	public int getRelativeCursor() {
		return relativeCursor;
	}
	
	/**
	 * 
	 * Retourne le indexieme element des echantillons originaux
	 */
	public int get(int index) {
		if (index < samples.size() && index >= 0) {
			return samples.get(index);
		}
		else return 0;
	}
	
	/**
	 * 
	 * permet d'acceder à un élément non entier d'un tableau au moyen d'une interpolation linéaire
	 */
	public int get(double index) { //interpolation linéaire;
		if (index <= samples.size() - 1 && index >= 0) {
			int floor = (int) index;
			int ceil = floor + 1;
			double frac = index - floor;
			double y = (samples.get(ceil) - samples.get(floor))*frac + samples.get(floor);
			//System.out.println("index : " + index + ", floor : " + floor + ", frac : " + frac + ", samples.get(ceil) : " + samples.get(ceil)
				//	 + ", samples.get(floor) : " + samples.get(floor) + ", y : " + y);
			return (int) y;
		}
		else return 0;
	}
	
	public int get(float index) {
		return get((double) index);
	}
	
	/**
	 * 
	 * retourne les indexieme élément des echantillons auxquels on a appliqué les différents effets.
	 */
	public int getEffected(int index) {
		if (index < effected.size() && index >= 0) {
			return effected.get(index);
		}
		else return 0;
	}
	
	public int size() {
		return samples.size();
	}
	
	/**
	 * 
	 * définie la valeur de l'échantillon index des echantillons affectés.
	 */
	public void set(int index, int element) {
		if (index >= effected.size()) {
			//System.out.println("plus grand");
			while (effected.size() < index) {
				effected.add(0);	
			}
			effected.add(element);
			
		} else {
			effected.set(index, element);
		}
	}
	
	/**
	 * 
	 * ajoute value à l'échantillon index des échantillons modifiés
	 */
	public void addTo(int index, int value) {
		//System.out.println(index);
		if (index < size()) {
			set(index, effected.get(index) + value);
		}
		else set(index, value);
	}
	
	/**
	 * 
	 * pareil qu'au dessus, mais multiplie
	 */
	public void multiply(int index, int factor) {
		if (index < samples.size()) {
			effected.set(index, (samples.get(index)*factor)/100);
		}
	}
	
	public Sample getSample() {
		return sample;
	}
	
	/**
	 * 
	 * verifie qu'un certain type d'effet soit déja appliqué à this. renvoie l'effet en question si
	 * c'est le cas, renvoie null sinon.
	 */
	public SoundEffect effectAlreadyIn(EffectType et) {
		for (int i = 0; i < soundEffects.size(); i++) {
			if (soundEffects.get(i).getType() == et) {
				return soundEffects.get(i);
			}
		}
		return null;
	}

}
