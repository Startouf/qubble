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
	
	private PlayButton play;
	private JMenuBar menuBar;
	private JMenu menu;
	private ChartPanel chartPanel;
	private JPanel windowPanel;
	
	private QuitMenuItem quitMenuItem;
	private SelectMenuItem selectMenuItem;
	//private JFileChooser chooser;
	
	public WaveForm(String title) {
		super(title);
		
		this.file = null;
		
		menu = new JMenu("File");
		quitMenuItem = new QuitMenuItem(this);
		selectMenuItem = new SelectMenuItem(this);
		menu.add(quitMenuItem);
		menu.add(selectMenuItem);
		menuBar = new JMenuBar();
		menuBar.add(menu);
		setJMenuBar(menuBar);
		
		play = new PlayButton(this);
		
		XYSeriesCollection collection = new XYSeriesCollection();
		
		collection = createDataset();
		JFreeChart chart = createChart("Forme d'onde", collection);
		
		chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		
		windowPanel = new JPanel();
		windowPanel.setLayout(new BoxLayout(windowPanel, BoxLayout.PAGE_AXIS));
		windowPanel.add(chartPanel);
		windowPanel.add(play);
		
		setContentPane(windowPanel);
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private XYSeriesCollection createDataset() {
		XYSeriesCollection collection = new XYSeriesCollection();
		
		XYSeries serie = new XYSeries("samples");
		if (file != null) {
			try {
				ArrayList<Integer> samples = Player.getSamples(file);
				System.out.println(samples.size());

				for (int i = 0; i < samples.size(); i++) {
					serie.add((float) i, (float) samples.get(i));
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
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
		file = (File) parameter;
		collection = createDataset();
		JFreeChart chart = createChart("Forme d'onde", collection);
		chartPanel.setChart(chart);
		chartPanel.repaint();
	}

	public void play() {
		Player.play(file);
	}
}
