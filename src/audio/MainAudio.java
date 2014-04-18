package audio;

import java.io.*;

import qubject.*;


public class MainAudio {
	
	private static final int bar = 1875;
	
	public static void main(String[] args) {
		Player player = new Player(null);
		
		Thread thread = new Thread(player);
		thread.start();
		
		int[] a = new int[] {2, 4, 5, 1};
		int[] b = new int[] {2, -3, 1};
		int[] axb = LPFilter.convol(a, b);
		for (int i = 0; i < axb.length; i++) {
			System.out.print(axb[i] + ", ");
		}
		
		
		sleep(1);
		
		SampleInterface beat = new Sample("pute", new File("data/samples/files/VEH1 House Loop - 093Mono.wav"));
		SampleInterface sample = new Sample("pute", new File("data/samples/files/VEH1 House Loop - 058Mono.wav"));
		SampleInterface melody = new Sample("pute2", new File("data/samples/files/VEE Melody Kits 03 128 BPM Root G#Mono.wav"));
		SampleInterface melody2 = new Sample("pute3", new File("data/samples/files/VEE Melody Kits 18 128 BPM Root EMono.wav"));
		SampleInterface sine = new Sample("pute4", new File("/Users/vincentcouteaux/wavs/triangle.wav"));
		SampleInterface saw = new Sample("pute5", new File("data/samples/saw2.wav"));
		
		try {
			
			player.playSample(beat);
			sleep(2);
			
			SampleControllerInterface sc3 = player.playSample(beat);
			player.tweakSample(sc3, EffectType.LPFilter, 5);
			sleep(0.5);
			sleep(3);
			
			//player.playSample(sample); 
			/*
			
			Thread.sleep(3*bar);
			
			SampleControllerInterface sc2 = player.playSample(melody);
			Thread.sleep(3000);
			player.tweakSample(sc2, EffectType.Distortion, 35);
			Thread.sleep(2000);
			player.tweakSample(sc2, EffectType.Distortion, 65);
			Thread.sleep(2500);
			sc2 = player.playSample(melody);
			player.playSample(sample);
			Thread.sleep(2000);
			player.tweakSample(sc2, EffectType.Distortion, 85);
			Thread.sleep(2000);
			player.playPause();
			Thread.sleep(500);
			player.playPause();
			Thread.sleep(3500);
			*/
		
			player.destroy();
		} catch(Exception e) {
			System.out.println("dans MainAudio : " + e.getMessage());
		}
	}
	
	public static void sleep(double i) {
		try {
			Thread.sleep((int)(i*bar));
		} catch(Exception e) {
			System.out.println("Dans sleep : " + e.getMessage());
		}
	}
}
