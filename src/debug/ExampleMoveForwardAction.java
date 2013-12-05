package debug;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import ui.*;

public class ExampleMoveForwardAction extends AbstractAction
{
	private App app;
	
	public ExampleMoveForwardAction(App app){
		this.app = app;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		TableViewPanel tVP = this.app.getMainPanel().getTableViewPanel();
		tVP.setTime(tVP.getTime()+1);
		tVP.repaint();
	}

}
