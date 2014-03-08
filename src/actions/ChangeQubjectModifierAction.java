package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import audio.SoundEffectInterface;
import qubject.AnimationInterface;
import qubject.MediaInterface;
import qubject.SampleInterface;
import ui.App;
import ui.ReferenceButton;

public class ChangeQubjectModifierAction extends AbstractAction {
private final App app;
	
	public ChangeQubjectModifierAction(App app){
		this.app = app;
		putValue(NAME, "Valider");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (this.app.getActiveTab().getActiveQubjectModifier()){
		//Proto final
//		case rotationModifier:
//			this.app.getActiveTab().getActiveQubject().setRotationEffect(
//					(SoundEffectInterface) this.app.getSoundEffectPalette().getSelectedModifier());
//			break;
		case sampleWhenPlayed:
			this.app.getActiveTab().getActiveQubject().setSampleWhenPlayed(
					(SampleInterface) this.app.getSamplePalette().getSelectedModifier());
			break;
		case animationWhenPlayed:
			this.app.getActiveTab().getActiveQubject().setAnimationWhenPlayed(
					(AnimationInterface) this.app.getSoundEffectPalette().getSelectedModifier());
			break;
			//Proto final
//		case whenPutOnTable:
//			this.app.getActiveTab().getActiveQubject().setAnimationWhenDetected(
//					(AnimationInterface) this.app.getAnimationPalette().getSelectedModifier());
//			break;
		case yAxisModifier:
			this.app.getActiveTab().getActiveQubject().setYAxisEffect(
					(SoundEffectInterface) this.app.getSoundEffectPalette().getSelectedModifier());
			break;
		default:
			//Should never happen !!!
			System.out.println("Incorrect Modifier");
			break;
		
		}
	}
}
