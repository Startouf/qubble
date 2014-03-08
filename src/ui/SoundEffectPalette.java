package ui;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import audio.SoundEffectInterface;
import qubject.MediaInterface;


public class SoundEffectPalette extends QubjectModifierPalette {

	public SoundEffectPalette(App app) {
		super(app);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	protected void previsualisation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected JComboBox<String> fillCombo() {
		JComboBox<String> combo = new JComboBox<String>();
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

}
