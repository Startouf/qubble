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

	//Constructor for the select QUbject selector
	public SelectorButton(App app, MediaInterface Qubject) {
		super(new ImageIcon("data/ui/arrow.png"));
		addActionListener(this);
		this.app=app;
		isQubject=true;
		this.modifier = null;
		//TODO : add action listener with the pattern
		setPreferredSize(new Dimension(35,35));
	}

	//Constructor for the select modifier selector
	public SelectorButton(App app, QubjectProperty modifier){
		super(new ImageIcon("data/ui/arrow.png"));
		addActionListener(this);
		this.app = app;
		isQubject=false;
		this.modifier = modifier;
		setPreferredSize(new Dimension(35,35));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (isQubject == true)
		{
			app.getQubjectPalette();
		}
		else{
			this.app.getActiveTab().setActiveProperty(modifier);
			switch (modifier){
			//Proto final
			case ROTATION:
				this.app.getSoundEffectPalette();
				break;
			case SAMPLE_WHEN_PLAYED:
				this.app.getSamplePalette();
				break;
			case ANIM_WHEN_PLAYED:
				this.app.getAnimationPalette();
				break;
				//Proto final
			case ANIM_WHEN_PUT_ON_TABLE:
				this.app.getAnimationPalette();
				break;
			case Y_AXIS:
				this.app.getSoundEffectPalette();
				break;
			default:
				//Should never happen !!!
				System.out.println("Incorrect Modifier");
				break;
			}
		}
	}
}