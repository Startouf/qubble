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
	private final JButton toggleGrid, playPause, panic, recordButton;
	private JLabel activeProject;

	public GlobalSettingsPanel(App app)
	{
		super();
		this.app = app;
		GridLayout layout = new GridLayout(2,0);
		layout.setHgap(25);
		setLayout(layout);
		this.setPreferredSize(new Dimension(400, 80));

		//Project Name
		JLabel project = new JLabel("Projet Actif:");
		project.setHorizontalAlignment(JLabel.CENTER);
		activeProject = new JLabel();
		activeProject.setHorizontalAlignment(JLabel.CENTER);
		activeProject.setText("Pas de projet");
		activeProject.setFont(new Font(null, Font.BOLD, 18));
		activeProject.setForeground(Color.red);
		//TODO : Il faut un JLabel plus stylé pour le nom de projet!

		//Volume Slider : (, min, max, initial) Volume
		JLabel volume = new JLabel("Volume");
		volume.setHorizontalAlignment(JLabel.CENTER);
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
		
		//Grid Toggle
		JLabel toggleGridLabel = new JLabel("Grille on/off");
		toggleGridLabel.setHorizontalAlignment(JLabel.CENTER);
		toggleGrid = new JButton(this.app.getToggleGridAction());

		//Time Slider : Removed because time synchronization is harddddd
		JLabel time = new JLabel("Time");
		time.setHorizontalAlignment(JLabel.CENTER);
		timeBar = new JProgressBar(0,240);
		timeBar.setStringPainted(true);
		
		//Play/Pause Button
		JLabel playPauseLabel = new JLabel("");
		playPauseLabel.setHorizontalAlignment(JLabel.CENTER);
		playPause = new JButton();
		playPause.setAction(this.app.getPlayPauseAction());
		ImageIcon img = new ImageIcon("data/ui/PauseButton.png");
		playPause.setSize(img.getIconWidth(), img.getIconHeight());
		playPause.setOpaque(false);
		playPause.setContentAreaFilled(false);
		playPause.setBorderPainted(false);
		
		//Panic button
		JLabel panicLabel = new JLabel("Redémarrer");
		panicLabel.setHorizontalAlignment(JLabel.CENTER);
		panic = new JButton(this.app.getPanicAction());
		panic.setPreferredSize(new Dimension(200,50));
		
		//Record button
		recordButton = new JButton(this.app.getRecordAction());
		recordButton.setOpaque(false);
		recordButton.setContentAreaFilled(false);
		recordButton.setBorderPainted(false);
		JLabel recordLabel = new JLabel("");

		//add in order (left to right)
		add(project);
		add(toggleGridLabel);
//		add(volume);
//		add(time);
		add(panicLabel);
		add(playPauseLabel);
		add(recordLabel);

		add(activeProject);
		add(toggleGrid);
//		add(volumeSlider);
//		add(timeBar);
		add(panic);
		add(playPause);
		add(recordButton);

		hideComponentsWhenNoProject();
		setBackground(Color.WHITE);
	}
	
	public void hideComponentsWhenNoProject(){
		toggleGrid.setVisible(false);
		panic.setVisible(false);
		playPause.setVisible(false);
		recordButton.setVisible(false);
	}
	
	public void showComponentsWhenProjectOpened(){
		toggleGrid.setVisible(true);
		panic.setVisible(true);
		playPause.setVisible(true);
		recordButton.setVisible(true);
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

	public void showProjectSettings(boolean projectOpened) {
		if (projectOpened)
			showComponentsWhenProjectOpened();
		else
			hideComponentsWhenNoProject();
	}
}
