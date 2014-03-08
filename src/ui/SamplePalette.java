package ui;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import qubject.MediaInterface;
import qubject.SampleInterface;

public class SamplePalette extends QubjectModifierPalette
{

	public SamplePalette(App app) {
		super(app);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected JComboBox<String> fillCombo() {
		JComboBox<String> combo = new JComboBox<String>();
	  	for (SampleInterface sound : this.app.getGlobalController().getSamples())
    	{
    		combo.addItem(sound.getName());
    	}
	  	return combo;
	}

	@Override
	protected JLabel label() {
		return new JLabel("Choix du sample joué");
	}

	@Override
	protected void previsualisation() {
		// TODO Auto-generated method stub
		
	}
}
