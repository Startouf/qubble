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

import drawing.HexagonMazeModel;
import drawing.SquareMazeModel;

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
		if (app.getActiveProject().getProjectName().equals("New Project")){
			//TODO : ask for a project name
		}
		//If it has a name
		else{
			//Ask for overwrite confirmation
			//TODO
			//Save
			JFileChooser chooser = new JFileChooser("save/");
		    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    
		    String path = null;
		    
		    int returnVal = chooser.showOpenDialog(app);
	        if(returnVal == JFileChooser.APPROVE_OPTION) {
	           path = chooser.getSelectedFile().getPath();
	           app.getActiveProject().save(path);
	        } else return;
		}
		
	}
	
}
