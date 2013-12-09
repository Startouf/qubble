package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Hashtable;

import javax.swing.*;

public class SettingsPanel extends JPanel
{
	private final App app;
	private final JSlider timeSlider;
	private final JSlider volumeSlider;

	public SettingsPanel(App app)
	{
		super();
		this.app = app;
		
		//Volume SLider : (, min, max, initial) Volume
		//TODO : Put a "volume" label
		add(volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50));
		volumeSlider.setMajorTickSpacing(25);
		volumeSlider.setPaintTicks(true);
		Hashtable volumeLabels = new Hashtable();
		volumeLabels.put( 0, new JLabel("0%"));
		volumeLabels.put(100,  new JLabel("100%"));
		volumeSlider.setLabelTable(volumeLabels);
		volumeSlider.setPaintLabels(true);
		
		//Time Slider :
		add(timeSlider = new JSlider(JSlider.HORIZONTAL, 10, 240, 60));

		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(256,50));
	}
}
