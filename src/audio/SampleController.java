package audio;

import java.io.File;
import java.util.ArrayList;
import sequencer.*;

public class SampleController implements SampleControllerInterface {

	int offSet; //indice du premier échantillon dans la boucle principale
	int relativeCursor; //echantillon qui sera renvoyé par la fonction getNext.
	ArrayList<Integer> samples;
	Player player;
	float volume;
	QubbleInterface qi;
	
	public SampleController(int offset, File file, QubbleInterface qi, Player player) {
		this.player = player;
		this.qi = qi;
		relativeCursor = 0;
		offSet = offset;
		volume = 100;
		try {
			samples = AudioUtility.getSamples(file);
			System.out.println(samples.size());
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
	public int getNext() {
		if (relativeCursor < samples.size()) {
			int res = samples.get(relativeCursor)*((int)(volume/100));
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
	
	public int getRelativeCursor() {
		return relativeCursor;
	}

}
