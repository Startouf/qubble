package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import ui.App;

public class PanicAction extends AbstractAction
{
	private final App app;

	public PanicAction(App app) {
		this.app = app;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.app.getActiveProject().getQubble().panic();
	}

}
