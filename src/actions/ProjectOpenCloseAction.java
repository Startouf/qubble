package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import ui.App;

public class ProjectOpenCloseAction extends AbstractAction
{
		private App app;
		
		public ProjectOpenCloseAction(App app){
			this.app = app;
		}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		//Si un projet est ouvert et qu'on le ferme
		if(this.app.isProjectOpened()){
			this.app.setProjectOpened(false);
			//TODO hide menu items 
		}
		//Sinon on ouvre un projet
		else{
			this.app.setProjectOpened(true);
			//TODO show menu items and initialize
		}
		
	}

}
