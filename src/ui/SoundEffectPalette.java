package ui;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.Hashtable;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import audio.EffectType;
import qubject.MediaInterface;
import qubject.QubjectModifierInterface;
import qubject.SampleInterface;


public class SoundEffectPalette extends QubjectModifierPalette 
	implements ChangeListener, WindowFocusListener{

	private int amount = 50;
	private SampleInterface activeSample;
	
	/**
	 * Using static variable because otherwise no way to instanciate those before call to super
	 * (Call to super() invoke this class addPrevisualisation());
	 */
	private static final JLabel sampleSelectedLabel = new JLabel("Error should display selecetd sample");
	private static final JPanel corePanel = new JPanel();
	private static final JLabel amountLabel = new JLabel("50");
	private static final JSlider amountSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
	
	public SoundEffectPalette(App app) {
		super(app, "Palette de choix d'effet sonore");
		addWindowFocusListener(this);
	}
	
	@Override
	protected JPanel addPrevisualisation() {
		amountLabel.setHorizontalAlignment(JLabel.CENTER);
		amountSlider.setMajorTickSpacing(25);
		amountSlider.setMinorTickSpacing(5);
		amountSlider.setPaintTicks(true);
		Hashtable<Integer, JLabel> volumeLabels = new Hashtable<Integer, JLabel>();
		volumeLabels.put(0, new JLabel("0%"));
		volumeLabels.put(50, new JLabel("50%"));
		volumeLabels.put(100,  new JLabel("100%"));
		amountSlider.setLabelTable(volumeLabels);
		amountSlider.setPaintLabels(true);
		amountSlider.addChangeListener(this);
		
		updateActiveModifiers();
		
		corePanel.add(sampleSelectedLabel);
		corePanel.add(amountSlider);
		corePanel.add(amountLabel);
		return corePanel;
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
	protected JLabel labelPalette() {
		return (new JLabel("Choix de l'effet sonore"));
	}

	@Override
	public QubjectModifierInterface getSelectedModifier() {
		return (QubjectModifierInterface) combo.getSelectedItem();
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider)e.getSource();
        if (!source.getValueIsAdjusting()) {
            amount = (int)source.getValue();
            amountLabel.setText("Effet à " +Integer.toString(amount) +"%");
        }
	}
	
	public void updateActiveModifiers(){
		try {
			MediaInterface qubject = this.app.getActiveViewQubjectsTab().getActiveQubject();
			this.activeSample = qubject.getSampleWhenPlayed();
			this.sampleSelectedLabel.setText(activeSample.getName());
			
		} catch (NotViewQubjectsTabException e) {
		}
	}
	
	public int getAmount(){
		return amount;
	}

	@Override
	public void windowGainedFocus(WindowEvent arg0) {
		updateActiveModifiers();		
	}

	@Override
	public void windowLostFocus(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
