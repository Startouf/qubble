package actions;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import ui.App;

public class ChangeProjectNameAction extends AbstractAction{
private final App app;
	
	public ChangeProjectNameAction(App app){
		this.app = app;
		putValue(NAME, "Pas de projet");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		app.getActiveProject().setProjectName(JOptionPane.showInputDialog("Nom pour votre projet: "));
		putValue(NAME, app.getActiveProject().getProjectName());
	}
}
