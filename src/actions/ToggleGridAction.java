package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import ui.App;

public class ToggleGridAction extends AbstractAction
{
	private final App app;
	private boolean grid = true;
	
	public ToggleGridAction(App app){
		this.app = app;
		putValue(NAME, "Masquer la grille");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		//set the active qubject 
		this.app.getActiveProject().getQubble().toggleGrid();
		if(grid)
			putValue(NAME, "Afficher la grille");
		else
			putValue(NAME, "Masquer la grille");
	}
}
