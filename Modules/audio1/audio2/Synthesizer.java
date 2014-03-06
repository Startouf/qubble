package audio2;

import java.util.ArrayList;
import wav.*;
import java.io.*;

public class Synthesizer {

	public static final int sine = 0;
	public static final int square = 1;
	public static final int saw = 2;
	public static final int triangle = 3;
	
	private int form;
	private int frequency;
	private int amp;
	private int bitrate;
	
	
	public Synthesizer(int form, int freq, int amp, int bitrate) {
		this.form = form;
		frequency = freq;
		this.amp = amp;
		this.bitrate = bitrate;
	}

	public int getForm() {
		return form;
	}

	public void setForm(int form) {
		this.form = form;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public int getAmp() {
		return amp;
	}

	public void setAmp(int amp) {
		this.amp = amp;
	}

	public ArrayList<Integer> generate(double length) {
		// length : duree de la sequence en seconde
		int numberOfSamples = (int) (length * bitrate);
		int samplesIn1Period = bitrate / frequency;
		ArrayList<Integer> res = new ArrayList<Integer>();
		switch (form) {
		case Synthesizer.square:
			for (int i = 0; i < numberOfSamples; i++) {
				if ((i % samplesIn1Period) < samplesIn1Period / 2) {
					res.add(-amp / 2);
				} else
					res.add(amp / 2);
			}
			break;
		case Synthesizer.sine:
			for (int i = 0; i < numberOfSamples; i++) {
				res.add((int) (amp * Math.sin(2 * i * 3.14 * frequency
						/ bitrate)));
			}
			break;
		case Synthesizer.saw:
			for (int i = 0; i < numberOfSamples; i++) {
				res.add(amp*(i % samplesIn1Period));
			}
			break;
		case Synthesizer.triangle:
			
			for (int i = 0; i < numberOfSamples; i++) {
				int x = i % samplesIn1Period;
				if (x < samplesIn1Period / 2) {
					res.add(2*amp * x / samplesIn1Period - amp/2);
				} else
					res.add(-2 * amp * x / samplesIn1Period + 3*amp/2);
			}
			break;
		}
		return res;
	}

	public void writeFile(File file, int length) {
		ArrayList<Integer> samples = generate(length);
		//WavFile wavFile = null;
		try {
			WavFile wavFile = WavFile.newWavFile(file, 1, samples.size(), 16, bitrate);
			
			int buffer[] = new int[samples.size()];
			
			for (int i = 0; i < samples.size() ; i++) {
				buffer[i] = samples.get(i);
			}
				wavFile.writeFrames(buffer, samples.size());
			
			
			wavFile.close();
 		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
		catch (WavFileException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void print(ArrayList<Integer> al) {
		for (int i = 0; i < al.size(); i++) {
			System.out.print(al.get(i) + ", ");
		}
	}
	
}
