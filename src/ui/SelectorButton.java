package ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import audio.SoundEffectInterface;

import qubject.AnimationInterface;
import qubject.MediaInterface;
import qubject.Qubject;
import qubject.QubjectModifierInterface;
import qubject.QubjectProperty;
import qubject.SampleInterface;


public class SelectorButton extends JButton implements ActionListener{

	private final App app;
	private final boolean isQubject;
	private final QubjectProperty modifier;
	private static final ImageIcon arrow = new ImageIcon("data/ui/fleche.png");

	//Constructor for the select Qubject selector
	public SelectorButton(App app, MediaInterface Qubject) {
		super(arrow);
		addActionListener(this);
		this.app=app;
		isQubject=true;
		tune();
		this.modifier = null;
		setPreferredSize(new Dimension(35,35));
	}

	//Constructor for the select modifier selector
	public SelectorButton(App app, QubjectProperty modifier){
		super(arrow);
		tune();
		addActionListener(this);
		this.app = app;
		isQubject=false;
		this.modifier = modifier;
		setPreferredSize(new Dimension(35,35));
	}
	
	public void tune(){
		setContentAreaFilled(false);
		setBorderPainted(false);
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (isQubject == true)
		{
			app.getQubjectPalette().setVisible(true);
		}
		else{
			try {
				this.app.getActiveViewQubjectsTab().setActiveProperty(modifier);
			} catch (NotViewQubjectsTabException e) {
				e.printStackTrace();
			}
			switch (modifier){
			//Proto final
			case AUDIO_EFFECT_ROTATION:
				this.app.getSoundEffectPalette().setVisible(true);
				break;
			case SAMPLE_WHEN_PLAYED:
				this.app.getSamplePalette().setVisible(true);
				break;
			case ANIM_WHEN_PLAYED:
				this.app.getAnimationPalette().setVisible(true);
				break;
				//Proto final
			case ANIM_WHEN_DETECTED:
				this.app.getAnimationPalette().setVisible(true);
				break;
			case AUDIO_EFFECT_Y_AXIS:
				this.app.getSoundEffectPalette().setVisible(true);
				break;
			default:
				//Should never happen !!!
				System.out.println("Incorrect Modifier");
				break;
			}
		}
	}
}