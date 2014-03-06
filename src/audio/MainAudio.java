package audio;

import java.io.*;

import qubject.*;


public class MainAudio {

	public static void main(String[] args) {
		Player player = new Player(null);
		
		Thread thread = new Thread(player);
		thread.start();
		
		try {
			Thread.sleep(1000);
		} catch(Exception e) {
		}
		
		SampleInterface sample = new Sample("pute", new File("/Users/vincentcouteaux/wavs/loop16bits2_converted.wav"));
		player.playSample(sample);
		try {
			Thread.sleep(2000);
		} catch(Exception e) {
		}
		player.playSample(sample);
		try {
			Thread.sleep(1000);
		} catch(Exception e) {
		}
		player.playSample(sample);
		try {
			Thread.sleep(1000);
		} catch(Exception e) {
		}
		//System.out.println();
		player.destroy();
		
	}

}
