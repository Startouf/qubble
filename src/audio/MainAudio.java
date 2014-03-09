package audio;

import java.io.*;

import qubject.*;


public class MainAudio {
	
	private static final int bar = 1875;
	
	public static void main(String[] args) {
		Player player = new Player(null);
		
		Thread thread = new Thread(player);
		thread.start();
		
		try {
			Thread.sleep(1000);
		} catch(Exception e) {
		}
		
		//SampleInterface sample = new Sample("pute", new File("/Users/vincentcouteaux/wavs/loop16bits2_converted.wav"));
		SampleInterface sample = new Sample("pute", new File("/Users/vincentcouteaux/wavConvertis/VEH1 House Loop - 093Mono.wav"));
		SampleInterface melody = new Sample("pute2", new File("/Users/vincentcouteaux/wavConvertis/VEE Melody Kits 03 128 BPM Root G#Mono.wav"));
		SampleInterface melody2 = new Sample("pute3", new File("/Users/vincentcouteaux/wavConvertis/VEE Melody Kits 18 128 BPM Root EMono.wav"));
		
		try {
			player.playSample(melody2);
			
			Thread.sleep(4*bar);
			SampleControllerInterface m2 = player.playSample(melody2);
			player.tweakSample(m2, EffectType.Distortion, 40);
			Thread.sleep(4*bar);
			//SampleControllerInterface sc = player.playSample(sample);
			/*
			SampleControllerInterface sc2 = player.playSample(melody);
			Thread.sleep(3000);
			Distortion disto = new Distortion(35);
			player.tweakSample(sc2, disto, 35);
			Thread.sleep(2000);
			disto.setAmount(65);
			Thread.sleep(2500);
			sc2 = player.playSample(melody);
			player.playSample(sample);
			Thread.sleep(2000);
			disto.setAmount(85);
			player.tweakSample(sc2, disto, 85);
			Thread.sleep(2000);
			player.playPause();
			Thread.sleep(500);
			player.playPause();
			Thread.sleep(3500);
			//*/
			m2 = player.playSample(melody2);
			player.tweakSample(m2, EffectType.Volume, 40);
			SampleControllerInterface sc3 = player.playSample(sample);
			player.tweakSample(sc3, EffectType.Delay, 50);
			
			
			Thread.sleep(2*bar);
			player.playSample(sample); 
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
			
			;
			player.destroy();
		} catch(Exception e) {
			System.out.println("dans MainAudio : " + e.getMessage());
		}
	}

}
