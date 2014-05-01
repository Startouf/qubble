package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import ui.App;

public class ToggleGridAction extends AbstractAction
{
	private final App app;
	private boolean grid = true;
	private final ImageIcon onIcon = new ImageIcon("data/ui/ON.png");
	private final ImageIcon offIcon = new ImageIcon("data/ui/OFF.png");
		
	
	public ToggleGridAction(App app){
		this.app = app;
//		putValue(NAME, "Masquer");
		putValue(LARGE_ICON_KEY, onIcon);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		//set the active qubject 
		this.app.getActiveProject().getQubble().toggleGrid();
		if(grid){
//			putValue(NAME, "Afficher");
			putValue(LARGE_ICON_KEY, offIcon);
		}
		else{
//			putValue(NAME, "Masquer");
			putValue(LARGE_ICON_KEY, onIcon);
		}
		grid = !grid;
	}
}
