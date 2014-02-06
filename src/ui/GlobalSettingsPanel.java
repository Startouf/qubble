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
	private JLabel activeProject = new JLabel();

	public GlobalSettingsPanel(App app)
	{
		super();
		this.app = app;

		setLayout(new GridLayout(2,4));

		//Headings
		JLabel project = new JLabel("Active project:");
		project.setHorizontalAlignment(JLabel.CENTER);
		JLabel volume = new JLabel("Volume");
		volume.setHorizontalAlignment(JLabel.CENTER);
		JLabel time = new JLabel("Time");
		time.setHorizontalAlignment(JLabel.CENTER);

		//Volume Slider : (, min, max, initial) Volume
		volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
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
		timeBar = new JProgressBar(0,240);
		timeBar.setStringPainted(true);

		//add in order (left to right)
		add(project);
		add(volume);
		add(time);

		add(activeProject);
		add(volumeSlider);
		add(timeBar);

		setBackground(Color.WHITE);
	}
}
