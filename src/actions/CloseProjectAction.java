package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import sequencer.Qubble;
import sequencer.QubbleInterface;
import ui.App;
import ui.ProjectController;

public class CloseProjectAction extends AbstractAction {
	private App app;
	
	public CloseProjectAction(App app){
		this.app = app;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//TODO : check if there is still another project opened
		this.app.setProjectOpened(false);
	}
}
