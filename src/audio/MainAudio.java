package audio;

import java.io.*;

import qubject.*;


public class MainAudio {
	
	private static final int bar = 1875;
	
	public static void main(String[] args) {
		Player player = new Player(null);
		
		Thread thread = new Thread(player);
		thread.start();
		
		System.out.println((short) 32767);
		System.out.println((short) 32768);
		System.out.println((short) 32769);
		System.out.println((short) 65535);
		System.out.println((short) 65536);
		System.out.println((short) 65537);
		
		try {
			Thread.sleep(1000);
		} catch(Exception e) {
		}
		
		//SampleInterface sample = new Sample("pute", new File("/Users/vincentcouteaux/wavs/rim16.wav"));
		SampleInterface sample = new Sample("pute", new File("data/samples/files/VEH1 House Loop - 058Mono.wav"));
		SampleInterface melody = new Sample("pute2", new File("data/samples/files/VEE Melody Kits 03 128 BPM Root G#Mono.wav"));
		SampleInterface melody2 = new Sample("pute3", new File("data/samples/files/VEE Melody Kits 18 128 BPM Root EMono.wav"));
		
		try {
			
			SampleControllerInterface sc3 = player.playSample(melody);
			player.tweakSample(sc3, EffectType.Delay, 60);
			//player.tweakSample(sc3, EffectType.Distortion, 60);
			Thread.sleep(1*bar);
			player.tweakSample(sc3, EffectType.Distortion, 20);
			Thread.sleep(1*bar);
			player.tweakSample(sc3, EffectType.Volume, 20);
			
			
			
			Thread.sleep(6*bar);
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

}
