package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import ui.App;

public class SaveProjectAction extends AbstractAction {

private App app;
	
	public SaveProjectAction(App app){
		this.app = app;
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
			//TODO
		}

	}

}
