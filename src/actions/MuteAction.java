package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import ui.App;

public class MuteAction extends AbstractAction {

	private final App app;
	private final ImageIcon mutedIcon = new ImageIcon("data/ui/volume orange small.png");
	private final ImageIcon playIcon = new ImageIcon("data/ui/volume vert small.png");
	private boolean muted = false;

	public MuteAction(App app){
		this.app = app;
		this.putValue(NAME, "ON");
		putValue(LARGE_ICON_KEY, playIcon);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.app.getActiveProject().getQubble().mute();
		if (!muted){ 
			putValue(NAME, "OFF");
			putValue(LARGE_ICON_KEY, mutedIcon);
		}
		else{
			putValue(NAME, "ON");
			putValue(LARGE_ICON_KEY, playIcon);
		}
		muted = !muted;
	}
}
