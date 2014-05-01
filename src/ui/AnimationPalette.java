package ui;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import qubject.AnimationInterface;
import qubject.QubjectModifierInterface;
import qubject.SampleInterface;
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
	protected JComboBox fillCombo() {
		JComboBox combo = new JComboBox();
		combo.setRenderer(new ModifierComboBoxRenderer());
	  	for (AnimationInterface anim : this.app.getGlobalController().getAnimations())
    	{
    		combo.addItem(anim);
    	}
	  	return combo;
	}

	@Override
	protected JLabel labelPalette() {
		return new JLabel("Choisissez une animation");
	}

	public QubjectModifierInterface getSelectedModifier() {
		return (QubjectModifierInterface) combo.getSelectedItem();
	}

}
