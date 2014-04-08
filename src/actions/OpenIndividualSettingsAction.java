package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import ui.App;
import ui.ViewIndividualPanel;

public class OpenIndividualSettingsAction extends AbstractAction {

	private final App app;
	
	public OpenIndividualSettingsAction(App app) {
		this.app = app;
		putValue(NAME, "Vue individuelle");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		ViewIndividualPanel view = new ViewIndividualPanel(app);
		app.getMainPanel().getSettingsTabs().addTab(
				"Config. indiv. "+app.getActiveProject().getProjectName(), view);
		app.getMainPanel().getSettingsTabs().setSelectedComponent(view);
	}

}
