package ui;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import audio.EffectType;
import qubject.MediaInterface;
import qubject.QubjectModifierInterface;
import qubject.SampleInterface;


public class SoundEffectPalette extends QubjectModifierPalette {

	public SoundEffectPalette(App app) {
		super(app, "Palette de choix d'effet sonore");
	}
	
	@Override
	protected JPanel addPrevisualisation() {
		//TODO
		return new JPanel();
	}

	@Override
	protected JComboBox fillCombo() {
		JComboBox combo = new JComboBox();
		combo.setRenderer(new ModifierComboBoxRenderer());
	  	for (EffectType effect : this.app.getGlobalController().getSoundEffects())
    	{
    		combo.addItem(effect);
    	}
	  	return combo;
	}

	@Override
	protected JLabel label() {
		return (new JLabel("Choix de l'effet sonore"));
	}


	@Override
	public QubjectModifierInterface getSelectedModifier() {
		return (QubjectModifierInterface) combo.getSelectedItem();
	}

}
