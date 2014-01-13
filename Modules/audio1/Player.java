
import wav.*;

import java.io.*;
import java.util.ArrayList;

import javax.sound.sampled.*;

public class Player {
	
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

	public static ArrayList<Integer> getSamples(File file) throws Exception {
		WavFile wfile;
		
		wfile = WavFile.openWavFile(file);
		ArrayList<Integer> res = getSamples(wfile);
		wfile.close();
		return res;
	
	}
}
