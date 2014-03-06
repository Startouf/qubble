package audio;

import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

import qubject.SampleInterface;
import sequencer.Qubble;

public class Player implements PlayerInterface, Runnable {
	
	private ArrayList<SampleControllerInterface> sampleControllers;
	
	private Qubble qubble;
	
	private SourceDataLine line;
	private AudioFormat format;
	private final int bufferSize; //taille en octet du buffer. Contient donc 2*moins d'échantillons
	//private ArrayList<Integer> samples;
	private int cursor;
	boolean running;
	
	public Player(Qubble qubble) {
		this.qubble = qubble;
		sampleControllers = new ArrayList<SampleControllerInterface>();
		bufferSize = 2048;
		running = false;
		
		cursor = 0;
		
		//samples = new ArrayList<Integer>();
		try {
			
			format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 1, 2, 44100, false);
			System.out.println(format.toString());
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
			line = (SourceDataLine) AudioSystem.getLine(info);
			line.open(format, bufferSize);
			
		} catch(Exception e) {
			System.out.println("in StreamPlayer Constructor : " + e.getMessage());
		}
	}
	
	@Override
	public void run() {
		running = true;
		line.start();
		//System.out.println("cursor : " + cursor + ", bufferSize : " + bufferSize + ", samples.size() : " + samples.size());
		while(running) {
			/*
			if (cursor + bufferSize/2 < samples.size()) {
				//effectNextChunk();
				writeNext();
			} else {
				cursor = 0;
			}
			*/
			writeNext();
		}
		
	}

	public void write(short[] dataInt) {
		//System.out.println("write");
		byte[] test = new byte[dataInt.length * 2];
		for (int i = 0; i < dataInt.length; i++) {
			test[2*i] = (byte)(dataInt[i] % 256);
			test[2*i + 1] = (byte)(dataInt[i]/256);
		}
		line.write(test, 0, test.length);
	}
	
	public void write(int[] dataInt) {
		short[] d = new short[dataInt.length];
		for (int i = 0; i < dataInt.length; i++) {
			d[i] = (short) (dataInt[i]);
		}
		//printArray(d);
		write(d);
	}
	
	public void writeNext() {
		write(nextArray(bufferSize/2));
		cursor += bufferSize/2;
	}
	
	public int[] nextArray(int size) {
		int[] res = new int[size];
		for (int i = 0; i < sampleControllers.size(); i++) {
			res[i] += sampleControllers.get(i).getNext();
		}
		return res;
	}
	
	public void hasFinishedPlaying(SampleController sc) {
		sampleControllers.remove(sc);
	}
	
	@Override
	public SampleControllerInterface playSample(SampleInterface sample) {
		SampleController res = new SampleController(cursor, sample.getFile(), qubble, this);
		sampleControllers.add(res);
		return res;
	}

	@Override
	public void tweakSample(SampleControllerInterface ref, SoundEffectInterface effect, float amount) {
		ref.changeVolume(amount);
		//pour l'instant tweakSample ne permet que de modifier le volume.
	}

	@Override
	public void playPause() {
		running = false;
		
	}

	@Override
	public void destroy() {
		running = false;
		
	}

	

}
