import javax.swing.*;

import java.util.*;

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

public class WaveFormModel extends Observable {
	
	public WaveFormModel(WaveForm wf) {
		addObserver(wf);
	}
	
	public void playSound() {
		
	}
}
