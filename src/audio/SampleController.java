package audio;

import java.io.File;
import java.util.ArrayList;

import sequencer.*;
import qubject.*;

public class SampleController implements SampleControllerInterface {

	int offSet; //indice du premier échantillon dans la boucle principale
	int relativeCursor; //echantillon qui sera renvoyé par la fonction getNext.
	ArrayList<Integer> samples;
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
			//System.out.println(samples.size());
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
		for (int i = 0; i < soundEffects.size(); i++) {
			soundEffects.get(i).effectNextChunk(this, size);
		}
	}
	
	@Override
	public int getNext() {
		if (relativeCursor < samples.size()) {
			int res = (samples.get(relativeCursor));
			relativeCursor++;
			//System.out.println(relativeCursor);
			return res;
		}
		else {
			//qi.soundHasFinishedPlaying(this);
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
		return samples.get(index);
	}
	
	public int size() {
		return samples.size();
	}
	public void set(int index, int element) {
		if (index >= samples.size()) {
			
			while (samples.size() < index) {
				samples.add(0);	
			}
			samples.add(element);
			
		} else {
			samples.set(index, element);
		}
	}
	
	public void addTo(int index, int value) {
		set(index, samples.get(index) + value);
	}
	
	public void multiply(int index, int factor) {
		if (index < samples.size()) {
			samples.set(index, (samples.get(index)*factor)/100);
		}
	}
	
	public Sample getSample() {
		return sample;
	}

}
