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
import qubject.QubjectModifiers;
import qubject.SampleInterface;


public class SelectorButton extends JButton implements ActionListener{
	
	private final App app;
	private final boolean isQubject;
	private QubjectModifiers modifier;

	//Constructor for the select QUbject selector
	public SelectorButton(App app, MediaInterface Qubject) {
		super(new ImageIcon("data/ui/arrow.png"));
		addActionListener(this);
		this.app=app;
		isQubject=true;
		
		//TODO : add action listener with the pattern
		setPreferredSize(new Dimension(35,35));
	}

	//Constructor for the select modifier selector
	public SelectorButton(App app, QubjectModifiers modifier){
		super(new ImageIcon("data/ui/arrow.png"));
		addActionListener(this);
		this.app = app;
		isQubject=false;
		this.modifier = modifier;
		//TODO : add action listener with the pattern modifier
		setPreferredSize(new Dimension(35,35));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (isQubject == true)
		{
			app.getQubjectPalette();
		}
		else{
			switch (modifier){
			//Proto final
			case rotationModifier:
				this.app.getSoundEffectPalette();
				break;
			case sampleWhenPlayed:
				this.app.getSamplePalette();
				break;
			case animationWhenPlayed:
				this.app.getAnimationPalette();
				break;
				//Proto final
//			case whenPutOnTable:
//				this.app.getActiveTab().getActiveQubject().setAnimationWhenDetected(
//						(AnimationInterface) this.app.getAnimationPalette().getSelectedModifier());
//				break;
			case yAxisModifier:
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