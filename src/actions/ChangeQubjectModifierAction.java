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
import ui.ReferenceButton;

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
		switch (this.app.getActiveTab().getActiveProperty()){
		//Proto final
		case AUDIO_EFFECT_ROTATION:
			EffectType effect = (EffectType) this.app.getSoundEffectPalette().getSelectedModifier();
			this.app.getActiveTab().getActiveQubject().setRotationEffect(effect);
			this.app.getActiveTab().setModifierOfActiveProperty(effect);
			this.app.getSoundEffectPalette().setVisible(false);
			break;
		case SAMPLE_WHEN_PLAYED:
			SampleInterface sample = (SampleInterface) this.app.getSamplePalette().getSelectedModifier();
			this.app.getActiveTab().getActiveQubject().setSampleWhenPlayed(sample);
			this.app.getActiveTab().setModifierOfActiveProperty(sample);
			this.app.getSamplePalette().setVisible(false);
			break;
		case ANIM_WHEN_PLAYED:
			AnimationInterface anim = (AnimationInterface) this.app.getAnimationPalette().getSelectedModifier();
			this.app.getActiveTab().getActiveQubject().setAnimationWhenPlayed(anim);
			this.app.getActiveTab().setModifierOfActiveProperty(anim);
			this.app.getAnimationPalette().setVisible(false);
			break;
			//Proto final
		case ANIM_WHEN_DETECTED:
			AnimationInterface anim2 = (AnimationInterface) this.app.getAnimationPalette().getSelectedModifier();
			this.app.getActiveTab().getActiveQubject().setAnimationWhenDetected(anim2);
			this.app.getActiveTab().setModifierOfActiveProperty(anim2);
			this.app.getAnimationPalette().setVisible(false);
			break;
		case AUDIO_EFFECT_Y_AXIS:
			EffectType effect2 = (EffectType) this.app.getSoundEffectPalette().getSelectedModifier();
			this.app.getActiveTab().getActiveQubject().setYAxisEffect(effect2);
			this.app.getActiveTab().setModifierOfActiveProperty(effect2);
			this.app.getSoundEffectPalette().setVisible(false);
			break;
		default:
			//Should never happen !!!
			System.out.println("Incorrect Modifier");
			break;
		}
	}
	
	public void changeModifier(Qubject qubject, QubjectProperty prop, QubjectModifierInterface modif){
		switch (prop){
		//Proto final
		case AUDIO_EFFECT_ROTATION:
			qubject.setRotationEffect((EffectType) modif);
//			this.app.getActiveTab().setModifierOfActiveProperty(effect);
			break;
		case SAMPLE_WHEN_PLAYED:
			qubject.setSampleWhenPlayed((SampleInterface) modif);
//			this.app.getActiveTab().setModifierOfActiveProperty(sample);
			break;
		case ANIM_WHEN_PLAYED:
			qubject.setAnimationWhenPlayed((AnimationInterface) modif);
//			this.app.getActiveTab().setModifierOfActiveProperty(anim);
			break;
			//Proto final
		case ANIM_WHEN_DETECTED:
			qubject.setAnimationWhenDetected((AnimationInterface) modif);
//			this.app.getActiveTab().setModifierOfActiveProperty(anim2);
			break;
		case AUDIO_EFFECT_Y_AXIS:
			qubject.setYAxisEffect((EffectType) modif);
//			this.app.getActiveTab().setModifierOfActiveProperty(effect2);
			break;
		default:
			//Should never happen !!!
			System.out.println("Incorrect Modifier");
			break;
		}
	}
}
