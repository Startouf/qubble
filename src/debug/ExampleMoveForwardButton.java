package debug;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.JButton;

import ui.App;

public class ExampleMoveForwardButton extends JButton
{
	private App app;
	
	public ExampleMoveForwardButton(App app)
	{
		super(new ExampleMoveForwardAction(app));
		this.app = app;
		this.setText("Avancer le curseur (redimensionner la fenêtre marche aussi)");
	}
	
}
