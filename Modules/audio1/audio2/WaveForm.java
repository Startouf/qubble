package audio2;

import javax.swing.*;

import java.awt.*;
import java.util.*;
import java.io.*;

import wav.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.*;
import org.jfree.util.Rotation;

public class WaveForm extends JFrame implements Observer {
	private static final long serialVersionUID = 1L;
	
	private File file;
	private ArrayList<Integer> samplesTab;
	private Recorder recorder;
	private Synthesizer synth;
	
	private audioThread stopper;
	
	private PlayButton play;
	private SynthButton synthButton;
	private DelayButton delayButton;
	private JMenuBar menuBar;
	private JMenu menu;
	private ChartPanel chartPanel;
	private JPanel windowPanel;
	
	
	private QuitMenuItem quitMenuItem;
	private SelectMenuItem selectMenuItem;
	private SaveMenuItem saveMenuItem;
	private RecordButton recordButton;
	//private JFileChooser chooser;
	
	public WaveForm(String title) {
		super(title);
		
		this.file = null;
		samplesTab = new ArrayList<Integer>();
		
		menu = new JMenu("File");
		quitMenuItem = new QuitMenuItem(this);
		selectMenuItem = new SelectMenuItem(this);
		saveMenuItem = new SaveMenuItem(this);
		menu.add(quitMenuItem);
		menu.add(selectMenuItem);
		menu.add(saveMenuItem);
		menuBar = new JMenuBar();
		menuBar.add(menu);
		setJMenuBar(menuBar);
		
		play = new PlayButton(this);
		recordButton = new RecordButton(this);
		synthButton = new SynthButton(this);
		delayButton = new DelayButton(this);
		
		XYSeriesCollection collection = new XYSeriesCollection();
		
		collection = createDataset();
		JFreeChart chart = createChart("Forme d'onde", collection);
		
		chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		
		windowPanel = new JPanel();
		windowPanel.setLayout(new BoxLayout(windowPanel, BoxLayout.PAGE_AXIS));
		windowPanel.add(chartPanel);
		windowPanel.add(play);
		windowPanel.add(recordButton);
		windowPanel.add(synthButton);
		windowPanel.add(delayButton);
		
		setContentPane(windowPanel);
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private XYSeriesCollection createDataset() {
		XYSeriesCollection collection = new XYSeriesCollection();
		
		XYSeries serie = new XYSeries("samples");
		/*
		if (file != null) {
			try {
				ArrayList<Integer> samples = Player.getSamples(file);
				//ArrayList<Integer> samples = Player.getSpectrum(Player.getSamples(file));
				samplesTab = samples;
				for (int i = 0; i < samples.size(); i++) {
					serie.add((float) i, (float) samples.get(i));
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		*/
		for (int i = 0; i < samplesTab.size(); i++) {
			serie.add((float) i, (float) samplesTab.get(i));
		}
		collection.addSeries(serie);
		
		return collection;
	}
	
	private JFreeChart createChart(String title, XYSeriesCollection collection) {
		
		JFreeChart res = ChartFactory.createXYLineChart(title, "amp", "t", collection);
		return res;
	}

	public void update(Observable observable, Object parameter) {
		XYSeriesCollection collection = new XYSeriesCollection();
		//file = (File) parameter;
		collection = createDataset();
		JFreeChart chart = createChart("Forme d'onde", collection);
		chartPanel.setChart(chart);
		chartPanel.repaint();
	}

	public void openFile(File file) {
		try {
			samplesTab = Player.getSamples(file);
			update(null, null);
		} catch (Exception e) {
			System.out.println("Error opening a file : " + e.getMessage());
		}
	}
	public void play() {
		//Player.play(file);
		//Player.playStream(file);
		Player.playStream(samplesTab);
		/*
		try {
			Synthesizer.print(Player.getSpectrum(Player.getSamples(file)));
		} catch (Exception e) {
			System.out.println("Dans play : " + e.getMessage());
		}
		*/
	}
	
	public void writeFile(File file) {
		try {
			int[] newSamples = new int[samplesTab.size()];
			for (int i = 0 ; i < newSamples.length ; i++) {
				newSamples[i] = samplesTab.get(i);
			}
			WavFile wavFile = WavFile.newWavFile(file, 1, newSamples.length, 16, 44100);
			
			wavFile.writeFrames(newSamples, newSamples.length);
			
			
			wavFile.close();
			update(null, file);
		}
		catch (Exception e) {
			System.out.println("applyDelay : " + e.getMessage());
		}
	}
	
	public void startRecording(File file) {
		this.file = file;
		recorder = new Recorder(file);
		
		stopper = new audioThread(recorder);
		stopper.start();
		
	}
	
	public void stopRecording() {
		recorder.finish();
		stopper.stopRecord();
		update(null, file);
	}
	
	public void synthesize(int form, int freq, int amp, double length) {
		synth = new Synthesizer(form, freq, amp, 44100);
		//synth.writeFile(file, 3);
		samplesTab = synth.generate(length);
		update(null, null);
	}
	
	public void applyDelay(int rate, int decay, int feedback, int dry, int wet) {
		try {
			int[] newSamples = Effect.delay(Player.getSamples(file), rate, decay, feedback, dry, wet);
			WavFile wavFile = WavFile.newWavFile(file, 1, newSamples.length, 16, 44000);
			
			wavFile.writeFrames(newSamples, newSamples.length);
			
			
			wavFile.close();
			update(null, file);
		}
		catch (Exception e) {
			System.out.println("applyDelay : " + e.getMessage());
		}
	}
	
	public void applyDisto(float drive, int clip) {
		/*
		try {
			int[] newSamples = Effect.disto(Player.getSamples(file), drive, clip);
			WavFile wavFile = WavFile.newWavFile(file, 1, newSamples.length, 16, 44000);
			
			wavFile.writeFrames(newSamples, newSamples.length);
			
			
			wavFile.close();
			update(null, file);
		}
		catch (Exception e) {
			System.out.println("applyDelay : " + e.getMessage());
		}
		*/
		samplesTab = Effect.distoArray(samplesTab, drive, clip);
		update(null, null);
	}
}
