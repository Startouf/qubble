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
		//set the active qubject 
		this.app.getActiveTab().setActiveQubject(
		this.app.getQubjectSelectionFrame().getSelectedQubject());
		
		//Hide the Qubject Palette
		this.app.getQubjectPalette().setVisible(false);
	}
}
