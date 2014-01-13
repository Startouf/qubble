package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import ui.App;

public class NewAction extends AbstractAction
{
	private App app;
	
	public NewAction(App app){
		this.app = app;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.app.setProjectOpened(true);
		//TODO
	}

}
