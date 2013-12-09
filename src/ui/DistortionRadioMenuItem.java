package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButtonMenuItem;

public class DistortionRadioMenuItem extends JRadioButtonMenuItem
{
	private final App app;

	public DistortionRadioMenuItem(App app) 
	{
		super("Quit");
		this.app = app;
		
		//The following should switch behaviour to distortion
		this.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e){
		    	//TODO : link to distortion
		    }
		});
	}
}