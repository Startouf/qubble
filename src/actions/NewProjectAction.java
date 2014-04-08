package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import sequencer.Qubble;
import sequencer.QubbleInterface;
import ui.App;
import ui.ProjectController;

public class NewProjectAction extends AbstractAction
{
	private App app;
	
	public NewProjectAction(App app){
		this.app = app;
		putValue(NAME, "Nouveau projet");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.app.setProjectOpened(true);
		//TODO : switch projects
		//<-----
		this.app.getWelcomePanel().disableProjects();
		//--->
		this.app.setActiveProject(new ProjectController(app, (QubbleInterface) new Qubble()));
		this.app.getWelcomePanel().addProjectEntry(this.app.getActiveProject());
	}

}
