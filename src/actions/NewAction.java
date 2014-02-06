package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import ui.App;
import ui.ProjectController;

public class NewAction extends AbstractAction
{
	private App app;
	
	public NewAction(App app){
		this.app = app;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.app.setProjectOpened(true);
		String path = null;
		//TODO : display fileBrowser for a path
		this.app.setActiveProject(new ProjectController(app, path));
	}

}
