package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Hashtable;

import javax.swing.*;

public class GlobalSettingsPanel extends JPanel
{
	private final App app;
	private final JProgressBar timeBar;
	private final JSlider volumeSlider;

	public GlobalSettingsPanel(App app)
	{
		super();
		this.app = app;
		
		setLayout(new GridLayout(2,2));
		
		JLabel volume = new JLabel("Volume");
		volume.setHorizontalAlignment(JLabel.CENTER);
		JLabel time = new JLabel("Time");
		time.setHorizontalAlignment(JLabel.CENTER);
		add(volume);
		add(time);
		
		//Volume SLider : (, min, max, initial) Volume
		add(volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50));
		volumeSlider.setMajorTickSpacing(25);
		volumeSlider.setMinorTickSpacing(5);
		volumeSlider.setPaintTicks(true);
		Hashtable<Integer, JLabel> volumeLabels = new Hashtable<Integer, JLabel>();
		volumeLabels.put(0, new JLabel("0%"));
		volumeLabels.put(50, new JLabel("50%"));
		volumeLabels.put(100,  new JLabel("100%"));
		volumeSlider.setLabelTable(volumeLabels);
		volumeSlider.setPaintLabels(true);
		
		//Time Slider :
		//With fixed values
		add(timeBar = new JProgressBar(0,240));
		timeBar.setStringPainted(true);

		setBackground(Color.WHITE);
	}
}
