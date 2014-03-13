package audio;

import java.io.File;
import java.util.ArrayList;

import wav.WavFile;

public class AudioUtility {
	
	/**
	 * @param wfile
	 * @return
	 */
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
	
	public static int[] toArray(ArrayList<Integer> data, int length) {
		int[] res = new int[length];
		for (int i = 0; i < length ; i++) {
			res[i] = data.get(i);
		}
		return res;
	}
}
