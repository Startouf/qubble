package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import ui.App;

public class LoadAction extends AbstractAction
{
	private App app;

	public LoadAction(App app){
		this.app = app;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		this.app.setProjectOpened(true);
		//TODO
	}
}
