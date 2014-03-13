package actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.AbstractAction;

import qubject.SampleInterface;

import ui.App;
import audio.Player;

/**
 * Use this action to play a sound from the SamplePalette Play button !
 * (also allows to change from play to stop JLabel)
 * @author Cyril
 *
 */
public class PlaySampleAction extends AbstractAction
{
	private App app;
	
	public PlaySampleAction(App app){
		this.app = app;
		putValue(NAME, "Essayer le son");
	}


	@Override
	public void actionPerformed(ActionEvent arg0){ 
		try {
			AudioInputStream sound = AudioSystem.getAudioInputStream
					(((SampleInterface)this.app.getSamplePalette().getSelectedModifier()).getFile());

			// load the sound into memory (a Clip)
			DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
			Clip clip = (Clip) AudioSystem.getLine(info);
			clip.open(sound);
			// play the sound clip
			clip.start();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
