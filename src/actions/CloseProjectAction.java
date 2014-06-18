package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import sequencer.Qubble;
import sequencer.QubbleInterface;
import ui.App;
import ui.ProjectController;
import ui.ProjectPanel;
import ui.ReferenceButton;

public class CloseProjectAction extends AbstractAction {
	private App app;
	
	public CloseProjectAction(App app){
		this.app = app;
		putValue(NAME, "Fermer");
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//TODO : close confirmation/save popup
		
		this.app.getPlayPauseAction().setPlay(false);
		
		if(e.getSource() instanceof ReferenceButton){
			ReferenceButton r =((ReferenceButton)e.getSource());
				if(r.getReference() instanceof ProjectPanel)
			this.app.closeProject(((ProjectPanel)r.getReference()).getProject());
		}
		
		this.app.setProjectOpened(false);
	}
}
