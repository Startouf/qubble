package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import ui.App;
import ui.ViewIndividualPanel;

public class PlayPauseAction extends AbstractAction {

private final App app;
private boolean play = false;
private final ImageIcon playIcon = new ImageIcon("data/ui/play bleu small.png");
private final ImageIcon pauseIcon = new ImageIcon("data/ui/pause rouge small.png");
	
	public PlayPauseAction(App app) {
		this.app = app;
		putValue(NAME, "Play");
		putValue(LARGE_ICON_KEY, playIcon);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.app.getActiveProject().getQubble().playPause();
		//Qubble checks if the table has already been started
//		this.app.getActiveProject().getQubble().start();
		// If was playing, pause, and show Play button
		if (play){ 
			putValue(NAME, "Play");
			putValue(LARGE_ICON_KEY, playIcon);
		}
		else{
			putValue(NAME, "Pause");
			putValue(LARGE_ICON_KEY, pauseIcon);
		}
		play = !play;
			
	}
	
	public void setPlay(boolean play) {
		this.app.getActiveProject().getQubble().playPause();
		if (!play){ 
			putValue(NAME, "Play");
			putValue(LARGE_ICON_KEY, playIcon);
		}
		else{
			putValue(NAME, "Pause");
			putValue(LARGE_ICON_KEY, pauseIcon);
		}
		this.play = play;
	}

}
