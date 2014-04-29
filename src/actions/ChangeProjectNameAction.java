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
		actionPerformed(arg0, JOptionPane.showInputDialog("Nom pour votre projet: "));
	}
	
	public void actionPerformed(ActionEvent arg0, String name) {
		if (name == null || name.equals("")){
			return;
		}
		app.getActiveProject().setProjectName(name);
		app.getWelcomePanel().refresh();
		putValue(NAME, app.getActiveProject().getProjectName());
		//Bad : should have some kind of PropertyChangeListener
		if(arg0.getSource() instanceof JButton){
			((JButton)(arg0.getSource())).setForeground(Color.black);
		}
	}
}
