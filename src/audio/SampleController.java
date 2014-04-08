package audio;

import java.io.File;
import java.util.ArrayList;

import sequencer.*;
import qubject.*;

public class SampleController implements SampleControllerInterface {

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
			samples = AudioUtility.getSamples(file);
			effected = (ArrayList<Integer>)samples.clone();
			//System.out.println("samples.size() : " + samples.size());
		} catch (Exception e) {
			System.out.println("In SampleController Constructor : " + e.getMessage());
		}
		
	}
	
	@Override
	public int getOffset() {
		
		return offSet;
	}

	@Override
	public int[] getNextArray(int cursor, int bufferSize) {
		// TODO Auto-generated method stub
		return null;
	}

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
	
	@Override
	public void effectNextChunk(int size) {
		ArrayList<Integer> samplesCopy = (ArrayList<Integer>)samples.clone();
		for (int i = 0; i < soundEffects.size(); i++) {
			soundEffects.get(i).effectNextChunk(this, size);
			samples = (ArrayList<Integer>)effected.clone();
		}
		samples = (ArrayList<Integer>)samplesCopy.clone();
	}
	
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
			System.out.println("samples.size(bite) : " + samples.size());
			System.out.println("effected.size() : " + effected.size());
			if (qi != null) {
				qi.soundHasFinishedPlaying(this);
			}
			player.hasFinishedPlaying(this);
			return 0;
		}
	}
	
	public void addEffect(SoundEffectInterface effect) {
		soundEffects.add((SoundEffect)effect);
	}
	
	public int getRelativeCursor() {
		return relativeCursor;
	}
	
	public int get(int index) {
		if (index < samples.size()) {
			return samples.get(index);
		}
		else return 0;
	}
	
	public int getEffected(int index) {
		if (index < effected.size()) {
			return effected.get(index);
		}
		else return 0;
	}
	
	public int size() {
		return samples.size();
	}
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
	
	public void addTo(int index, int value) {
		if (index < size()) {
			set(index, samples.get(index) + value);
		}
		else set(index, value);
	}
	
	public void multiply(int index, int factor) {
		if (index < samples.size()) {
			effected.set(index, (samples.get(index)*factor)/100);
		}
	}
	
	public Sample getSample() {
		return sample;
	}
	
	public SoundEffect effectAlreadyIn(EffectType et) {
		for (int i = 0; i < soundEffects.size(); i++) {
			if (soundEffects.get(i).getType() == et) {
				return soundEffects.get(i);
			}
		}
		return null;
	}

}
