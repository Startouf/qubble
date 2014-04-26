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
	 * 2-Change the Qubject so he uses this modifier
	 * 3-Refresh the ViewQubject to reflect the changes
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			ViewQubjects view = this.app.getActiveViewQubjectsTab();
		
		switch (view.getActiveProperty()){
		//Proto final
		case AUDIO_EFFECT_ROTATION:
			EffectType effect = (EffectType) this.app.getSoundEffectPalette().getSelectedModifier();
			view.getActiveQubject().setRotationEffect(effect);
			view.setModifierOfActiveProperty(effect);
//			this.app.getSoundEffectPalette().setVisible(false);
			break;
		case SAMPLE_WHEN_PLAYED:
			SampleInterface sample = (SampleInterface) this.app.getSamplePalette().getSelectedModifier();
			view.getActiveQubject().setSampleWhenPlayed(sample);
			view.setModifierOfActiveProperty(sample);
//			this.app.getSamplePalette().setVisible(false);
			break;
		case ANIM_WHEN_PLAYED:
			AnimationInterface anim = (AnimationInterface) this.app.getAnimationPalette().getSelectedModifier();
			view.getActiveQubject().setAnimationWhenPlayed(anim);
			view.setModifierOfActiveProperty(anim);
//			this.app.getAnimationPalette().setVisible(false);
			break;
			//Proto final
		case ANIM_WHEN_DETECTED:
			AnimationInterface anim2 = (AnimationInterface) this.app.getAnimationPalette().getSelectedModifier();
			view.getActiveQubject().setAnimationWhenDetected(anim2);
			view.setModifierOfActiveProperty(anim2);
//			this.app.getAnimationPalette().setVisible(false);
			break;
		case AUDIO_EFFECT_Y_AXIS:
			EffectType effect2 = (EffectType) this.app.getSoundEffectPalette().getSelectedModifier();
			view.getActiveQubject().setYAxisEffect(effect2);
			view.setModifierOfActiveProperty(effect2);
//			this.app.getSoundEffectPalette().setVisible(false);
			break;
		default:
			//Should never happen !!!
			System.out.println("Incorrect Modifier");
			break;
		}
		} catch (NotViewQubjectsTabException e1) {
		}
	}
	
	public void changeModifier(Qubject qubject, QubjectProperty prop, QubjectModifierInterface modifier){
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
		this.app.setConfigForQubject(qubject, prop, modifier);
	}
}
