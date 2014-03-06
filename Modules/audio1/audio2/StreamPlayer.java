package audio2;

import wav.*;

import java.lang.*;
import java.nio.ByteBuffer;
import java.io.*;
import java.util.ArrayList;

import javax.sound.sampled.*;

/**
 * a faire : une checkbox pour savoir si on joue en boucle
 * une classe abstraite effect, les effets en heritent, et sont attributs de cette classe
 * @author vincentcouteaux
 *
 */
public class StreamPlayer extends Thread {
	private SourceDataLine line;
	private AudioFormat format;
	private final int bufferSize;
	boolean running;
	
	//int drive;
	SliderDrive sliderDrive;
		
	private ArrayList<Integer> samples;
	private int cursor;
	
	//boolean paused = false;
	
	public StreamPlayer(SliderDrive sliderDrive) {
		bufferSize = 2048;
		cursor = 0;
		running = false;
		
		this.sliderDrive = sliderDrive;
		
		samples = new ArrayList<Integer>();
		try {
			//AudioInputStream sound = AudioSystem.getAudioInputStream(file);
			//format = sound.getFormat();
			format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 1, 2, 44100, false);
			System.out.println(format.toString());
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
			line = (SourceDataLine) AudioSystem.getLine(info);
			line.open(format, bufferSize);
			//System.out.println(line.getFormat());
		} catch(Exception e) {
			System.out.println("in StreamPlayer Constructor : " + e.getMessage());
		}
	}
	
	public void run() {
		running = true;
		line.start();
		System.out.println("cursor : " + cursor + ", bufferSize : " + bufferSize + ", samples.size() : " + samples.size());
		while(running) {
			if (cursor + bufferSize/2 < samples.size()) {
				effectNextChunk();
				writeNext();
			} else {
				cursor = 0;
			}
		}
	}
	
	public void pause() {
		System.out.println("pause");
		running = false;
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
		write(nextArray(cursor, bufferSize/2));
		//System.out.println(sliderDrive.getValue());
		//write(Effect.disto(nextArrayList(cursor, bufferSize/2), sliderDrive.getValue(), 10000));
		cursor += bufferSize/2;
	}
	
	
	public void addSamples(int[] toAdd) {
		for (int i = 0 ; i < toAdd.length; i++) {
			samples.add(toAdd[i]);
		}
	}
	public void addSamples(ArrayList<Integer> toAdd) {
		samples.addAll(toAdd);
	}
	public void addSample(int toAdd) {
		samples.add(toAdd);
	}
	
	private void effectNextChunk() {
		ArrayList<Integer> newArray;
		newArray = Effect.distoArray(nextArrayList(cursor, bufferSize/2), sliderDrive.getValue(), 10000);
		for(int i = 0; i < newArray.size(); i++) {
			samples.set(cursor + i, newArray.get(i));
		}
	}
	
	
	private int[] nextArray(int off, int length) {
		int[] res;
		if (samples.size() - off > length) {
			res = new int[length];
		}
		else {
			res = new int[samples.size() - off];
		}
		for (int i = 0 ; i < res.length ; i++) {
			res[i] = samples.get(i+off);
		}
		return res;	
	}
	
	private ArrayList<Integer> nextArrayList(int off, int length) {
		ArrayList<Integer> res = new ArrayList<Integer>();
		int longueur;
		if (samples.size() - off > length) {
			longueur = length;
		}
		else {
			longueur = samples.size() - off;
		}
		for (int i = 0 ; i < longueur ; i++) {
			res.add(samples.get(i+off));
		}
		return res;	
	}
	private void printArray(short[] array) {
		for (int i = 0 ; i < array.length ; i++) {
			System.out.println(array[i]);
		}
	}

}
