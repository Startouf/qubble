package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import audio.EffectType;
import audio.SoundEffectInterface;
import qubject.AnimationInterface;
import qubject.MediaInterface;
import qubject.Qubject;
import qubject.QubjectModifierInterface;
import qubject.QubjectProperty;
import qubject.SampleInterface;
import ui.App;
import ui.NotViewQubjectsTabException;
import ui.QubjectModifierPalette;
import ui.ReferenceButton;
import ui.ViewQubjects;

public class ChangeQubjectModifierAction extends AbstractAction {
private final App app;
	
	public ChangeQubjectModifierAction(App app){
		this.app = app;
		putValue(NAME, "Valider");
	}

	/**
	 * 1-Get the selected modifier
	 * 2-Change the Qubject so he uses this modifier (internally)
	 * 3-Refresh the QubjectViews of the corresponding project
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof ViewQubjects){
			ViewQubjects view = (ViewQubjects)e.getSource();
			changeModifier(view.getActiveQubject(), view.getActiveProperty(), view.getActiveModifier());
		} else if(e.getSource() instanceof QubjectModifierPalette){
			QubjectModifierPalette palette = (QubjectModifierPalette) e.getSource();
			//La palette correspond à un tab actif : le chercher :
			try {
				ViewQubjects view = app.getActiveViewQubjectsTab();
				//Récupérer le nouvel attribut choisi de la palette correspondant au Qubject actif
				changeModifier(view.getActiveQubject(), view.getActiveProperty(), palette.getSelectedModifier());
				
			} catch (NotViewQubjectsTabException e1) {
				System.err.println("Trying to change properties from Palette but active tab isn't a ViewQubjects Tab !");
				e1.printStackTrace();
			}
		}
	}
	
	public void changeModifier(MediaInterface qubject, QubjectProperty prop, QubjectModifierInterface modifier){
		switch (prop){
		//Proto final
		case AUDIO_EFFECT_ROTATION:
			qubject.setRotationEffect((EffectType) modifier);
			break;
		case SAMPLE_WHEN_PLAYED:
			qubject.setSampleWhenPlayed((SampleInterface) modifier);
			break;
		case ANIM_WHEN_PLAYED:
			qubject.setAnimationWhenPlayed((AnimationInterface) modifier);
			break;
			//Proto final
		case ANIM_WHEN_DETECTED:
			qubject.setAnimationWhenDetected((AnimationInterface) modifier);
			break;
		case AUDIO_EFFECT_Y_AXIS:
			qubject.setYAxisEffect((EffectType) modifier);
			break;
		default:
			//Should never happen !!!
			System.out.println("Incorrect Modifier");
			break;
		}
		this.app.refreshConfigForQubject(qubject, prop, modifier);
	}
}
