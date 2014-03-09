package ui;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import qubject.AnimationInterface;
import audio.SoundEffectInterface;

public class AnimationPalette extends QubjectModifierPalette {

	public AnimationPalette(App app) {
		super(app, "Palette de choix d'animation");
	}

	@Override
	protected JPanel addPrevisualisation() {
		//TODO
		return(new JPanel());
	}

	@Override
	protected JComboBox<String> fillCombo() {
		JComboBox<String> combo = new JComboBox<String>();
	  	for (AnimationInterface anim : this.app.getGlobalController().getAnimations())
    	{
    		combo.addItem(anim.getName());
    	}
	  	return combo;
	}

	@Override
	protected JLabel label() {
		return new JLabel("Choisissez une animation");
	}

}
