package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

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
		JFileChooser chooser = new JFileChooser("save/");
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    
	    String path = null;
	    
	    int returnVal = chooser.showOpenDialog(app);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
           path = chooser.getSelectedFile().getPath();
        } else return;
	        
	        
	    this.app.setProjectOpened(true);
		//TODO : switch projects
		//<-----
		this.app.getWelcomePanel().disableProjects();
		//--->
		this.app.setActiveProject(new ProjectController(app, (QubbleInterface) new Qubble(path)));
		this.app.getWelcomePanel().addProjectEntry(this.app.getActiveProject());
	}
}
