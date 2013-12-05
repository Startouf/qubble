package table;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import ui.*;

public class ExampleIncrementTimeAction extends AbstractAction
{
	private final App app;
	
	public ExampleIncrementTimeAction(App app){
		this.app = app;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		 this.app.getMainPanel().getTableViewPanel().incrementTime();
		 this.app.getMainPanel().getTableViewPanel().repaint();
		 //Otherstuff
	}

}
