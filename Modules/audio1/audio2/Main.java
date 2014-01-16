package audio2;

import wav.*;
import java.io.*;
//import javax.sound.sampled.*;
import java.util.*;

public class Main {

	public static void main(String[] args) {
		try {
			//File file = new File("/Users/vincentcouteaux/Music/Logic/sampler/Audio Files/tama.wav");
			
			
			WaveForm wf = new WaveForm("forme d'onde");
			wf.pack();
			wf.setVisible(true);
		
			
			//wf.addSamples(samples);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		//Synthesizer synth = new Synthesizer(Synthesizer.sine, 880, 2500, 44000);
		//synth.writeFile(new File("/Users/vincentcouteaux/wavs/synthe.wav"), 3);
		//synth.print(synth.generate(3));
	}

}
      