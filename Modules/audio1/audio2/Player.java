package audio2;

import wav.*;

import java.lang.*;
import java.io.*;
import java.util.ArrayList;

import javax.sound.sampled.*;

//import org.apache.commons.math3.complex.Complex;
//import org.apache.commons.math3.transform.DftNormalization;
//import org.apache.commons.math3.transform.FastFourierTransformer;
//import org.apache.commons.math3.transform.TransformType;
//import org.apache.commons.math3.analysis.function.Sqrt;

public class Player {
	
	private static StreamPlayer player = null;
	
	public static void play(File file) {
		try {
			AudioInputStream sound = AudioSystem.getAudioInputStream(file);
	
			// load the sound into memory (a Clip)
			DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
			Clip clip = (Clip) AudioSystem.getLine(info);
		    clip.open(sound);
		   
			    // play the sound clip
		    clip.start();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void playStream(File file, SliderDrive slider) {
		
		int[] buffer = null;
		ArrayList<Integer> res = null;
		try {
			WavFile wfile;
			
			wfile = WavFile.openWavFile(file);
			res = getSamples(wfile);
			wfile.close();
		} catch(Exception e) {
			System.out.println("Dans PlayStream : " + e.getMessage());
		}
		
		player = new StreamPlayer(slider);
		player.start();
		
		player.addSamples(res);
		
	}

	public static void playStream(ArrayList<Integer> samples, SliderDrive slider) {
		player = new StreamPlayer(slider);
		player.start();
		
		player.addSamples(samples);
	}
	
	public static void pause() {
		player.pause();
	}
	
	public static void play(String fileName) {
		play(new File(fileName));
	}
	
	public static ArrayList<Integer> getSamples(WavFile wfile) {
		ArrayList<Integer> samples = new ArrayList<Integer>();
		int numChannels = wfile.getNumChannels();
		
		int[] buffer = new int[100*numChannels];
		
		try {
			int framesRead = wfile.readFrames(buffer, 100);
			while (framesRead != 0) {
				framesRead = wfile.readFrames(buffer, 100);
				
				
				for (int i = 0; i < framesRead; i++) {
					
					samples.add(new Integer(buffer[i]));
					
				}
				
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return samples;
	}

	public static ArrayList<Integer> getSpectrum(ArrayList<Integer> in) {
		double samples[] = new double[262144];
		int s = 0;
		for (s = 0 ; s < samples.length; s++) {
			samples[s] = in.get(s);
		}
		while (s < in.size()) {
			samples[s] = 0;
		}
//		FastFourierTransformer transformer = new FastFourierTransformer(DftNormalization.STANDARD);
		
		
//		Complex[] spectrum = transformer.transform(samples, TransformType.FORWARD);
		ArrayList<Integer> modspec = new ArrayList<Integer>();
//		
//		for (int i = 0 ; i < spectrum.length ; i++) {
//			//Sqrt sqrt = new Sqrt();
//			double temp = (spectrum[i].getReal()*spectrum[i].getReal() + 
//					spectrum[i].getImaginary()*spectrum[i].getImaginary());
//			modspec.add((int)temp);
//		}
		return modspec;
	}
	
	public static ArrayList<Integer> getSamples(File file) throws Exception {
		WavFile wfile;
		
		wfile = WavFile.openWavFile(file);
		ArrayList<Integer> res = getSamples(wfile);
		wfile.close();
		return res;
	
	}
	
	private static int[] toArray(ArrayList<Integer> data, int length) {
		int[] res = new int[length];
		for (int i = 0; i < length ; i++) {
			res[i] = data.get(i);
		}
		return res;
	}
}
