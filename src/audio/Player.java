package audio;

import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

import qubject.*;
import sequencer.Qubble;

public class Player implements PlayerInterface, Runnable {
	
	private ArrayList<SampleController> sampleControllers;
	
	private Qubble qubble;
	
	
	private SourceDataLine line;
	private AudioFormat format;
	private final int bufferSize; //taille en octet du buffer. Contient donc 2*moins d'échantillons
	//private ArrayList<Integer> samples;
	private int cursor;
	boolean running;
	boolean paused;
	
	public Player(Qubble qubble) {
		this.qubble = qubble;
		sampleControllers = new ArrayList<SampleController>();
		bufferSize = 2048;
		running = false;
		paused = false;
		cursor = 0;
		
		
		
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
			if (!paused) {
				effectNextChunk();
				writeNext();
			}
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
	
	public void effectNextChunk() {
		for (int i = 0; i < sampleControllers.size(); i++) {
			sampleControllers.get(i).effectNextChunk(bufferSize/2);
		}
	}
	
	public int[] nextArray(int size) {
		int[] res = new int[size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < sampleControllers.size(); j++) {
				res[i] += sampleControllers.get(j).getNext();
			}
		}
		return res;
	}
	
	public void hasFinishedPlaying(SampleController sc) {
		sampleControllers.remove(sc);
	}
	
	@Override
	public SampleControllerInterface playSample(SampleInterface sample) {
		SampleController res = new SampleController(cursor, sample.getFile(), qubble, this, (Sample) sample);
		sampleControllers.add(res);
		return res;
	}

	@Override
	public void tweakSample(SampleControllerInterface ref, EffectType effect, float amount) {
		ref.addEffect(effect);
		//if effect est deja dedans, on modifie l'amount
		
	}
	
	@Override
	public void playPause() {
		if (paused) paused = false;
		else paused = true;
		
	}

	@Override
	public void destroy() {
		running = false;
		for (int i = 0; i < sampleControllers.size(); i++) {
			System.out.println(sampleControllers.get(i).getRelativeCursor());
		}
	}

	

}
