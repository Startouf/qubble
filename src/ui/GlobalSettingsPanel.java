package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Hashtable;

import javax.swing.*;

public class GlobalSettingsPanel extends JPanel
{
	private final App app;
	private final JProgressBar timeBar;
	private final JSlider volumeSlider;
	private final JButton playPause;
	private JLabel activeProject;

	public GlobalSettingsPanel(App app)
	{
		super();
		this.app = app;
		GridLayout layout = new GridLayout(2,4);
		layout.setHgap(25);
		setLayout(layout);

		//Labels
		JLabel project = new JLabel("Active project:");
		project.setHorizontalAlignment(JLabel.CENTER);
		JLabel volume = new JLabel("Volume");
		volume.setHorizontalAlignment(JLabel.CENTER);
//		JLabel time = new JLabel("Time");
//		time.setHorizontalAlignment(JLabel.CENTER);
		JLabel playPauseLabel = new JLabel("Play/Pause");
		playPauseLabel.setHorizontalAlignment(JLabel.CENTER);
		
		//Project Name
		activeProject = new JLabel();
		activeProject.setHorizontalAlignment(JLabel.CENTER);
		activeProject.setText("No Project");
		activeProject.setFont(new Font(null, Font.BOLD, 18));
		activeProject.setForeground(Color.red);
		//TODO : Il faut un JLabel plus stylé pour le nom de projet!

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
		//TODO : chaîne réactive quand le volume est changé
		//(AbstractAction would be best : able to sync it from external modif (OS modification ?))

		//Time Slider : Removed because time synchronization is harddddd
		//TODO : afficher le temps actuel/temps total
		//With fixed values
		timeBar = new JProgressBar(0,240);
		timeBar.setStringPainted(true);
		
		//Play/Pause Button
		playPause = new JButton("Play/Pause");
		//TODO : play/Pause action !!!!!
		playPause.setAction(this.app.getPlayPauseAction());
		//TODO : Size not adjustable with current layout ??
		playPause.setSize(new Dimension(300,50));

		//add in order (left to right)
		add(project);
		add(volume);
		add(playPauseLabel);
//		add(time);

		add(activeProject);
		add(volumeSlider);
		add(playPause);
//		add(timeBar);

		setBackground(Color.WHITE);
	}
	
	/**
	 * Change the displayed project name and repaint the panel 
	 * @param name
	 */
	public void setActiveProjectName(String name){
		activeProject.setForeground(Color.BLACK);
		activeProject.setText(name);
		repaint();
	}
}
