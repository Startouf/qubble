package audio2;

import javax.sound.sampled.*;

import java.io.*;

public class Recorder {
	private static final long RecordTime = 10000; // Donne la duree de
													// l'enregistrement, en
													// millisecondes

	File wavFile; // = new File("/Users/vincentcouteaux/wavs/test.wav");// Donne l'endroit
																// ou le ficher
																// Wav sera
																// enregistre.

	AudioFileFormat.Type fileType; // = AudioFileFormat.Type.WAVE; // Format du
																// fichier audio

	TargetDataLine line; // La targetDataLine

	public Recorder(File wavFile) {
		this.wavFile = wavFile;
		fileType = AudioFileFormat.Type.WAVE;
		
	}
	
	public AudioFormat getAudioFormat() {
		float sampleRate = 44000;
		int sampleSizeInBits = 16;
		int channels = 2;
		boolean signed = true;
		boolean bigEndian = true;
		AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
				channels, signed, bigEndian);
		return format;
	}// pour definir un format audio

	public void start() {
		try {
			AudioFormat format = getAudioFormat();
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

			if (!AudioSystem.isLineSupported(info)) {
				System.out.println("Line not supported");
				System.exit(0);
			}// Cela regarde si la targetdataline est supportee par le systeme

			line = (TargetDataLine) AudioSystem.getLine(info);
			line.open(format);
			line.start();

			System.out.println("La capture commence");

			AudioInputStream ais = new AudioInputStream(line);

			System.out.println("L'enregistrement commence");

			AudioSystem.write(ais, fileType, wavFile);

		} catch (LineUnavailableException ex) {
			ex.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public void finish() {
		line.stop();
		line.close();
		System.out.println("Termine");
	}// Ferme la targetdataline donc finit l'enregistrement et la capture
/*
	public static void main(String[] args) {
		final Recorder recorder = new Recorder();

		Thread stopper = new Thread(new Runnable() {// Creer un nouveau thread
													// qui attend RecordTime
													// avant de s'arreter
					public void run() {
						try {
							Thread.sleep(RecordTime);
						} catch (InterruptedException ex) {
							ex.printStackTrace();
						}
						recorder.finish();

					}

				});
		stopper.start();

		recorder.start(); // Commence a enregistrer

	}
	*/
}
