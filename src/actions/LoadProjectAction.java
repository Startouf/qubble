package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import sequencer.Qubble;
import sequencer.QubbleInterface;
import ui.App;
import ui.ProjectController;

public class LoadProjectAction extends AbstractAction
{
	private App app;

	public LoadProjectAction(App app){
		this.app = app;
		putValue(NAME, "Charger un projet");
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		this.app.setProjectOpened(true);
		//TODO : open FolderSelector popup and pick a path
		String path = null;
		this.app.setActiveProject(new ProjectController(app, (QubbleInterface) new Qubble(path)));
	}
}
