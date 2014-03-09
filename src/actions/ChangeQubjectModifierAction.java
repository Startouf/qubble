package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import audio.EffectType;
import audio.SoundEffectInterface;
import qubject.AnimationInterface;
import qubject.MediaInterface;
import qubject.QubjectModifierInterface;
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
		case rotationModifier:
			this.app.getActiveTab().getActiveQubject().setRotationEffect(
					(EffectType) this.app.getSoundEffectPalette().getSelectedModifier());
			break;
		case sampleWhenPlayed:
			SampleInterface sample = (SampleInterface) this.app.getSamplePalette().getSelectedModifier();
			this.app.getActiveTab().getActiveQubject().setSampleWhenPlayed(sample);
			this.app.getActiveTab().setModifierOfActiveProperty(sample);
			break;
		case animationWhenPlayed:
			AnimationInterface anim = (AnimationInterface) this.app.getSoundEffectPalette().getSelectedModifier();
			this.app.getActiveTab().getActiveQubject().setAnimationWhenPlayed(anim);
			this.app.getActiveTab().setModifierOfActiveProperty(anim);
			break;
			//Proto final
		case whenPutOnTable:
			this.app.getActiveTab().getActiveQubject().setAnimationWhenDetected(
					(AnimationInterface) this.app.getAnimationPalette().getSelectedModifier());
			break;
		case yAxisModifier:
			EffectType effect = (EffectType) this.app.getSoundEffectPalette().getSelectedModifier();
			this.app.getActiveTab().getActiveQubject().setYAxisEffect(effect);
			this.app.getActiveTab().setModifierOfActiveProperty(effect);
			break;
		default:
			//Should never happen !!!
			System.out.println("Incorrect Modifier");
			break;
		}
	}
}
