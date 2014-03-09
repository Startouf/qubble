package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import ui.App;
import ui.ViewIndividualPanel;

public class PlayPauseAction extends AbstractAction {

private final App app;
private boolean play = true;
	
	public PlayPauseAction(App app) {
		this.app = app;
		putValue(NAME, "Pause");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.app.getActiveProject().getQubble().playPause();
		// If was playing, pause, and show Play button
		if (play){ 
			putValue(NAME, "Play");
			play = !play;
		}
		else{
			putValue(NAME, "Pause");
			play = !play;
		}
			
	}

}
