package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import ui.App;
import ui.NotViewQubjectsTabException;
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
		try {
			this.app.getActiveViewQubjectsTab().setActiveQubject(
			this.app.getQubjectSelectionFrame().getSelectedQubject());
		} catch (NotViewQubjectsTabException e) {
			e.printStackTrace();
		}
		
		//Hide the Qubject Palette
		this.app.getQubjectPalette().setVisible(false);
	}
}
