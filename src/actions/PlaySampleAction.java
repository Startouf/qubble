package actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.AbstractAction;

import qubject.MediaInterface;
import qubject.SampleInterface;

import ui.App;
import ui.NotViewQubjectsTabException;
import ui.SamplePalette;
import ui.SoundEffectPalette;
import audio.EffectType;
import audio.Player;
import audio.SampleControllerInterface;
import audio.SoundEffect;

/**
 * Use this action to play a sound from the SamplePalette Play button !
 * (also allows to change from play to stop JLabel)
 * @author Cyril
 *
 */
public class PlaySampleAction extends AbstractAction
{
	private App app;
	private static final Player player = new Player(null); 
	
	public PlaySampleAction(App app){
		this.app = app;
		putValue(NAME, "Essayer le son");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0){ 
		if(arg0.getSource() instanceof SamplePalette){
			player.playSample(
					(((SampleInterface)this.app.getSamplePalette().getSelectedModifier())));
		} else if (arg0.getSource() instanceof SoundEffectPalette){
			//Grab the current qubject of the active view and play its sampleWhenPlayed Effect
			MediaInterface qubject;
			try {
				qubject = this.app.getActiveViewQubjectsTab().getActiveQubject();
			} catch (NotViewQubjectsTabException e) {
				//TODO : Show error message no activeViewQUbjects
				return;
			}
			
			SampleControllerInterface controller = player.playSample(
					qubject.getSampleWhenPlayed());
			EffectType effect = (EffectType) this.app.getSoundEffectPalette().getSelectedModifier();
			int amount = this.app.getSoundEffectPalette().getAmount();
			player.tweakSample(controller, effect, amount);
		}
	}
}
