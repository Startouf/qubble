package ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class GlobalSettingsPanel extends JPanel
{
	private final App app;
	private final JProgressBar timeBar;
	private final JSlider volumeSlider;
	private final JButton toggleGrid, playPause, panic, recordButton, activeProject, mute;
	private JLabel activeProjectLabel, volumeLabel, panicLabel, playPauseLabel, 
	toggleGridLabel, time, recordLabel, muteLabel;

	public GlobalSettingsPanel(App app)
	{
		super();
		this.app = app;
		GridLayout layout = new GridLayout(2,0);
		layout.setHgap(5);
		setLayout(layout);
		this.setPreferredSize(new Dimension(400, 80));

		//Project Name
		activeProjectLabel = new JLabel("Projet Actif:");
		activeProjectLabel.setHorizontalAlignment(JLabel.CENTER);
		activeProject = new JButton(app.getChangeProjectNameAction());
		activeProject.setHorizontalAlignment(JLabel.CENTER);
		activeProject.setFont(new Font(null, Font.BOLD, 18));
		activeProject.setForeground(Color.red);
		activeProject.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//		activeProject.setBorderPainted( false );
//		activeProject.setContentAreaFilled(false);
		

		//Volume Slider : (, min, max, initial) Volume
		volumeLabel = new JLabel("Volume");
		volumeLabel.setHorizontalAlignment(JLabel.CENTER);
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
		toggleGridLabel = new JLabel("Grille on/off");
		toggleGridLabel.setHorizontalAlignment(JLabel.CENTER);
		toggleGrid = new JButton(this.app.getToggleGridAction());
		toggleGrid.setOpaque(false);
		toggleGrid.setContentAreaFilled(false);
		toggleGrid.setBorderPainted(false);

		//Time Slider : Removed because time synchronization is harddddd
		time = new JLabel("Time");
		time.setHorizontalAlignment(JLabel.CENTER);
		timeBar = new JProgressBar(0,240);
		timeBar.setStringPainted(true);
		
		//Mute
		mute = new JButton(this.app.getMuteAction());
		muteLabel = new JLabel("");
		muteLabel.setHorizontalAlignment(JLabel.CENTER);
		
		//Play/Pause Button
		playPauseLabel = new JLabel("");
		playPauseLabel.setHorizontalAlignment(JLabel.CENTER);
		playPause = new JButton();
		playPause.setAction(this.app.getPlayPauseAction());
//		ImageIcon img = new ImageIcon("data/ui/PauseButton.png");
//		playPause.setSize(img.getIconWidth(), img.getIconHeight());
//		playPause.setOpaque(false);
//		playPause.setContentAreaFilled(false);
//		playPause.setBorderPainted(false);
		
		//Panic button
		panicLabel = new JLabel("");
		panicLabel.setHorizontalAlignment(JLabel.CENTER);
		panic = new JButton(this.app.getPanicAction());
		panic.setPreferredSize(new Dimension(200,50));
		
		//Record button
		recordButton = new JButton(this.app.getRecordAction());
		recordButton.setOpaque(false);
//		recordButton.setContentAreaFilled(false);
//		recordButton.setBorderPainted(false);
		recordLabel = new JLabel("");

		//add in order (left to right)
		add(activeProjectLabel);
		add(muteLabel);
//		add(volumeLabel);
//		add(time);
		add(panicLabel);
		add(playPauseLabel);
		add(recordLabel);
		add(toggleGridLabel);

		add(activeProject);
		add(mute);
//		add(volumeSlider);
//		add(timeBar);
		add(panic);
		add(playPause);
		add(recordButton);
		add(toggleGrid);

		hideComponentsWhenNoProject();
		setBackground(Color.WHITE);
	}
	
	public void hideComponentsWhenNoProject(){
		panic.setVisible(false);
		panicLabel.setVisible(false);
		toggleGrid.setVisible(false);
		toggleGridLabel.setVisible(false);
		playPause.setVisible(false);
		playPauseLabel.setVisible(false);
		recordButton.setVisible(false);
		recordLabel.setVisible(false);
	}
	
	public void showComponentsWhenProjectOpened(){
		panic.setVisible(true);
		panicLabel.setVisible(true);
		toggleGrid.setVisible(true);
		toggleGridLabel.setVisible(true);
		playPause.setVisible(true);
		playPauseLabel.setVisible(true);
		recordButton.setVisible(true);
		recordLabel.setVisible(true);
	}
	
	/**
	 * Change the displayed project name and repaint the panel 
	 * @param name
	 */
	public void setActiveProjectName(String name){
		activeProject.setForeground(Color.BLACK);
		repaint();
	}

	public void showProjectSettings(boolean projectOpened) {
		if (projectOpened)
			showComponentsWhenProjectOpened();
		else
			hideComponentsWhenNoProject();
	}
}
