package ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import qubject.MediaInterface;
import qubject.Qubject;
import qubject.QubjectModifierInterface;


public class SelectorButton extends JButton implements ActionListener{
	
	private App app;
	private boolean isPattern;

	//Constructor for the select pattern selector
	public SelectorButton(App app, MediaInterface Qubject) {
		super(new ImageIcon("data/ui/arrow.png"));
		addActionListener(this);
		
		
		this.app=app;
		isPattern=true;
		
		//TODO : add action listener with the pattern
		setPreferredSize(new Dimension(35,35));
	}

	//Constructor for the select modifier selector
	public SelectorButton(Qubject pattern, QubjectModifierInterface modifier){
		super(new ImageIcon("data/ui/arrow.png"));
		isPattern=false;
		//TODO : add action listener with the pattern modifier
		setPreferredSize(new Dimension(35,35));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (isPattern=true)
		{
			app.getQubjectPalette();
		}
		
		
		}


}
