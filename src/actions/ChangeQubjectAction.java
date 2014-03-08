package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import ui.App;
import ui.QubjectPalette;

public class ChangeQubjectAction extends AbstractAction{
	private final App app;
	
	public ChangeQubjectAction(App app){
		this.app = app;
		putValue(NAME, "Valider");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.app.getActiveTab().setActiveQubject(
				this.app.getQubjectSelectionFrame().getSelectedQubject());
	}
}
