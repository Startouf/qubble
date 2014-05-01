package ui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import qubject.MediaInterface;
import qubject.QubjectModifierInterface;
import qubject.SampleInterface;

public class SamplePalette extends QubjectModifierPalette
{

	public SamplePalette(App app) {
		super(app, "Palette de choix de Sample");
	}

	@Override
	protected JComboBox fillCombo() {
		JComboBox combo = new JComboBox();
		combo.setRenderer(new ModifierComboBoxRenderer());
	  	for (SampleInterface sound : this.app.getGlobalController().getSamples())
    	{
    		combo.addItem(sound);
    	}
	  	return combo;
	}

	@Override
	protected JLabel labelPalette() {
		return new JLabel("Choix du sample joué");
	}

	@Override
	protected JPanel addPrevisualisation() {
		JPanel panel = new JPanel();
		JButton play = new JButton("Play");
		//TODO : use this
		play.setAction(this.app.getPlaySampleAction());
		//....Or override the ActionPerformed(...) of the palette
		panel.add(play);
		return panel;
	}

	@Override
	public QubjectModifierInterface getSelectedModifier() {
		return (QubjectModifierInterface) combo.getSelectedItem();
	}
}
