package ui;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import audio.SoundEffectInterface;
import qubject.MediaInterface;
import qubject.QubjectModifierInterface;
import qubject.SampleInterface;


public class SoundEffectPalette extends QubjectModifierPalette {

	public SoundEffectPalette(App app) {
		super(app, "Palette de choix d'effet sonore");
		// TODO Auto-generated constructor stub
	}

	
	@Override
	protected JPanel addPrevisualisation() {
		//TODO
		return new JPanel();
	}

	@Override
	protected JComboBox fillCombo() {
		JComboBox combo = new JComboBox();
	  	for (SoundEffectInterface effect : this.app.getGlobalController().getSoundEffects())
    	{
    		combo.addItem(effect.getName());
    	}
	  	return combo;
	}

	@Override
	protected JLabel label() {
		return (new JLabel("Choix de l'effet sonore"));
	}


	@Override
	public QubjectModifierInterface getSelectedModifier() {
		String str = (String) combo.getSelectedItem();
		for (SoundEffectInterface effect : this.app.getGlobalController().getSoundEffects()){
			if(effect.getName().equals(str)){
				return effect;
			}
		}
		return null;
	}

}
