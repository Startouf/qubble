package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import ui.App;

public class PanicAction extends AbstractAction
{
	private final App app;
	private final ImageIcon panicIcon = new ImageIcon("data/ui/stop orange small.png");

	public PanicAction(App app) {
		this.app = app;
		putValue(NAME, "Panic");
		putValue(LARGE_ICON_KEY, panicIcon);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.app.getPlayPauseAction().setPlay(false);
		this.app.getActiveProject().getQubble().panic();
	}

}
