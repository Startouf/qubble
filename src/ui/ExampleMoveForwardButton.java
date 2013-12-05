package ui;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.JButton;

import table.ExampleIncrementTimeAction;



public class ExampleMoveForwardButton extends JButton
{
	private App app;
	
	public ExampleMoveForwardButton(App app)
	{
		super(app.getIncrementTimeAction());
		this.app = app;
		this.setText("Avancer le curseur");
	}
	
}
