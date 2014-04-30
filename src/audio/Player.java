package audio;

import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

import qubject.*;
import sequencer.Qubble;
import wav.WavFile;

public class Player implements PlayerInterface, Runnable {
	
	private ArrayList<SampleController> sampleControllers;
	
	private Qubble qubble;
	
	private ArrayList<Integer> recording;
	private boolean isRecording;
	private File toRecord;
	
	private SourceDataLine line;
	private AudioFormat format;
	private final int bufferSize; //taille en octet du buffer. Contient donc 2*moins d'échantillons
	//private ArrayList<Integer> samples;
	private int cursor;
	boolean running;
	boolean paused;
	
	public Player(Qubble qubble) {
		
		isRecording = false;
		toRecord = null;
		
		
		this.qubble = qubble;
		sampleControllers = new ArrayList<SampleController>();
		bufferSize = 2048;
		running = false;
		paused = false;
		cursor = 0;
		try {
			format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 1, 2, 44100, false);
			System.out.println(format.toString());
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
			line = (SourceDataLine) AudioSystem.getLine(info);
			line.open(format, bufferSize);
			
		} catch(Exception e) {
			System.out.println("in StreamPlayer Constructor : " + e.getMessage());
		}
	}
	
	/**
	 * 
	 * Run : fonction principale tu thread.
	 * les booleans running et paused gerent respectivement l'arret complet et la pause du thread.
	 * à chaque tour de boucle, effectNextChunk() et writeNext() sont appelés
	 * 
	 * writeNext() appelle write(int[]) appelle write(short[])
	 */
	
	
	@Override
	public void run() {
		running = true;
		line.start();
		//System.out.println("cursor : " + cursor + ", bufferSize : " + bufferSize + ", samples.size() : " + samples.size());
		while(running) {
			if (!paused) {
				effectNextChunk();
				writeNext();
			}
		}
	}
	
	/**
	 * 
	 * write est la méthode appelée en dernier. elle prend en argument le tableau de 1024 short,
	 * convertit chaque short en 2 byte et les ecrits dans la SourceDataLine.
	 * La methode SourceDataLine.write ecrit le tableau d'octed donné en argument,
	 * puis attends que tous les échantillons soient joués pour charger les échantillons suivants.
	 * C'est ce bloquage qui garantit la lecture en temps réel.
	 */
	public void write(short[] dataInt) {
		//System.out.println("write");
		byte[] test = new byte[dataInt.length * 2];
		for (int i = 0; i < dataInt.length; i++) {
			/*
			test[2*i] = (byte)(dataInt[i] % 256);
			//test[2*i] = 0;
			test[2*i + 1] = (byte)(dataInt[i]/256);
			*/
			byte byte1 = (byte) (dataInt[i]);
			byte byte2 = (byte) ((dataInt[i] >> 8) & 0xff); //Ca marche trop bieeeennnn !!
			test[2*i] = byte1;
			test[2*i + 1] = byte2;
		}
		line.write(test, 0, test.length);
	}
	///*
	
	/**
	 * 
	 * convertit les int en short, en fixant un seuil
	 */
	public void write(int[] dataInt) {
		short[] d = new short[dataInt.length];
		for (int i = 0; i < dataInt.length; i++) {
			
			if (dataInt[i] >= 32767) {
				d[i] = 32767;
			}
			if (dataInt[i] <= -32767) {
				d[i] = -32767;
			}
			
			else d[i] = (short) (dataInt[i]);
		}
		if (isRecording) {
			for (int i = 0; i < dataInt.length; i++) {
				recording.add((int)d[i]);
			}
		}
		write(d);
	}
	//*/
	
	/**
	 * Selectionne les échantillons à envoyer dans la line
	 */
	public void writeNext() {
		write(nextArray(bufferSize/2));
		cursor += bufferSize/2;
	}
	
	
	/**
	 * Applique les effets à tous les SampleControllers en train d'être joués.
	 */
	public void effectNextChunk() {
		for (int i = 0; i < sampleControllers.size(); i++) {
			sampleControllers.get(i).effectNextChunk(bufferSize/2);
		}
	}
	
	/**
	 * 
	 * Additionne les size échantillons suivants depuis tous les SampleControllers en train d'être joués,
	 * 
	 */
	public int[] nextArray(int size) {
		int[] res = new int[size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < sampleControllers.size(); j++) {
				res[i] += sampleControllers.get(j).getNext();
			}
		}
		return res;
	}
	
	/**
	 * 
	 * Quand un SampleController a fini d'être joué, on le supprime de la liste de SampleControllers en train d'être joués
	 */
	public void hasFinishedPlaying(SampleController sc) {
		sampleControllers.remove(sc);
	}
	
	/**
	 * 
	 * quand l'utilisateur veut jouer un son, on crée un sample controller à partir de ce son,
	 * on l'ajoute aux SampleControllers en train d'être joués, et on le renvoie pour que l'utilisateur
	 * puisse le controller
	 */
	@Override
	public SampleControllerInterface playSample(SampleInterface sample) {
		SampleController res = new SampleController(cursor, sample.getFile(), qubble, this, (Sample) sample);
		sampleControllers.add(res);
		return res;
	}

	/**
	 * 
	 * tweakSample applique un effet effect au SampleController ref
	 */
	@Override
	public void tweakSample(SampleControllerInterface ref, EffectType effect, int amount) {
		SampleController r = (SampleController) ref;
		if (r.effectAlreadyIn(effect) == null) {
			SoundEffect e;
			switch (effect) {
			case Delay:
				e = new Delay(amount);
				break;
			case Distortion:
				e = new Distortion(amount);
				break;
			case Flanger:
				e = new Flanger(amount);
				break;
			case LPFilter:
				e = new LPFilter(amount);
				break;
			case HPFilter:
				e = new HPFilter(amount);
				break;
			case Shifter:
				e = new PitchShifter(amount);
				break;
			case Volume:
				e = new Volume(amount);
			default:
				e = new Volume(amount);
			}
			ref.addEffect(e);
		}
		else {
			r.effectAlreadyIn(effect).setAmount(amount);
		}
	}
	
	@Override
	public void playPause() {
		if (paused) paused = false;
		else paused = true;
		
	}

	/**
	 * 
	 * mets le booleen running à false
	 */
	@Override
	public void destroy() {
		running = false;
		for (int i = 0; i < sampleControllers.size(); i++) {
			System.out.println(sampleControllers.get(i).getRelativeCursor());
		}
	}

	@Override
	public void stopAllSounds() {
		destroy(); //Cyril, je ne vois pas l'interet de cette méthode
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startRecording(File file) {
		isRecording = true;
		recording = new ArrayList<Integer>();
		toRecord = file;
	}

	@Override
	public void stopRecording() {
		isRecording = false;
		writeFile(toRecord, recording);
	}
	
	public static void writeFile(File file, ArrayList<Integer> samplesTab) {
		try {
			int[] newSamples = new int[samplesTab.size()];
			for (int i = 0 ; i < newSamples.length ; i++) {
				newSamples[i] = samplesTab.get(i);
			}
			WavFile wavFile = WavFile.newWavFile(file, 1, newSamples.length, 16, 44100);
			
			wavFile.writeFrames(newSamples, newSamples.length);
			
			
			wavFile.close();
		}
		catch (Exception e) {
			System.out.println("writeFile : " + e.getMessage());
		}
	}
	

}
