package ui;

import javax.swing.JComboBox;
import javax.swing.JLabel;

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
	protected JComboBox getCombo() {
		JComboBox combo = new JComboBox();
	  	for (MediaInterface qubject : this.app.getQubjects())
    	{
    		combo.addItem(qubject.getName());
    	}
	  	return combo;
	}

	@Override
	protected JLabel label() {
		JLabel label = new JLabel("Choix de l'effet sonore");
		return label;
	}

}
