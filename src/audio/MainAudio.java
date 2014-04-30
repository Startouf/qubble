package audio;

import java.io.*;

import qubject.*;


public class MainAudio {
	
	private static final int bar = 1875;
	
	public static void main(String[] args) {
		Player player = new Player(null);
		
		Thread thread = new Thread(player);
		thread.start();
		
		//System.out.println(Math.pow(3, 5));
		
		
		sleep(1);
		
		SampleInterface beat = new Sample("a", new File("data/samples/files/VEH1 House Loop - 093Mono.wav"));
		SampleInterface sample = new Sample("b", new File("data/samples/files/VEH1 House Loop - 058Mono.wav"));
		SampleInterface melody = new Sample("c", new File("data/samples/files/VEE Melody Kits 03 128 BPM Root G#Mono.wav"));
		SampleInterface melody2 = new Sample("d", new File("data/samples/files/VEE Melody Kits 18 128 BPM Root EMono.wav"));
		SampleInterface sine = new Sample("e", new File("/Users/vincentcouteaux/wavConvertis/beep.wav"));
		SampleInterface clap = new Sample("e", new File("/Users/vincentcouteaux/wavConvertis/VEC1 Clap 012Mono.wav"));
		SampleInterface saw = new Sample("f", new File("data/samples/saw2.wav"));
		
		try {
			
			player.playSample(beat);
			sleep(2);
			SampleControllerInterface sci = player.playSample(beat);
			player.tweakSample(sci, EffectType.LPFilter, 5);
			sleep(3);
			
			
			/*
			player.playSample(beat);
			sleep(2);
			
			SampleControllerInterface sc3 = player.playSample(beat);
			//player.tweakSample(sc3, EffectType.Distortion, 50);
			player.tweakSample(sc3, EffectType.LPFilter, 10);
			sleep(1);
			player.tweakSample(sc3, EffectType.LPFilter, 5);
			sleep(1);
			
			SampleControllerInterface sc4 = player.playSample(beat);
			
			player.tweakSample(sc4, EffectType.HPFilter, 75);
			sleep(1);
			player.tweakSample(sc4, EffectType.HPFilter, 100);
			sleep(7);
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
