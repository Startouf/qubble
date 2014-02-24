package audio2;

import wav.*;

import java.lang.*;
import java.nio.ByteBuffer;
import java.io.*;
import java.util.ArrayList;

import javax.sound.sampled.*;


public class StreamPlayer {
	private SourceDataLine line;
	private AudioFormat format;
	private final int bufferSize;
	
	private ArrayList<Integer> samples;
	private int cursor;
	
	public StreamPlayer() {
		bufferSize = 2048;
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
	
	public void start() {
		line.start();
	}
	
	public void write(short[] dataInt) {
		/*
		ByteBuffer byteBuffer = ByteBuffer.allocate(dataInt.length*2); //un short est code sur 2 octets
		for (int i = 0 ; i < dataInt.length ; i++) {
			byteBuffer.putShort(dataInt[i]);
		}
		
		byte[] datab = byteBuffer.array();
		//System.out.println(datab.length + " " + cursor);
		AudioInputStream stream =
			    new AudioInputStream(new ByteArrayInputStream(datab), format, bufferSize);
		byte[] test = new byte[bufferSize];
		try {
			stream.read(test);
		} catch (Exception e) {
			System.out.println("Dans StreamPlayer.write(short) :" + e.getMessage());
		}
		*/
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
	
	private void printArray(short[] array) {
		for (int i = 0 ; i < array.length ; i++) {
			System.out.println(array[i]);
		}
	}

}
