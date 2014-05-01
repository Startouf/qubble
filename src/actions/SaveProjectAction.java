package actions;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import ui.App;

public class SaveProjectAction extends AbstractAction {

private App app;
	
	public SaveProjectAction(App app){
		this.app = app;
		putValue(NAME, "Sauvegarder");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//If the project is unnamed (default name : New Project)
		String newName = app.getActiveProject().getProjectName();
		app.getActiveProject().setProjectName(JOptionPane.showInputDialog("Nom pour votre projet: "));

		app.getActiveProject().save("save/" + app.getActiveProject().getProjectName() + "/");

	}
	
}
