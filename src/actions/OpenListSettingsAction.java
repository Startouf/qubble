package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import ui.App;
import ui.ViewIndividualPanel;
import ui.ViewListPanel;

public class OpenListSettingsAction extends AbstractAction {

private final App app;
	
	public OpenListSettingsAction(App app) {
		this.app = app;
		putValue(NAME, "Vue tableur");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		ViewListPanel view = new ViewListPanel(app);
		app.getMainPanel().getSettingsTabs().addTab(
				"Config. list. "+app.getActiveProject().getProjectName(), view);
		app.getMainPanel().getSettingsTabs().setSelectedComponent(view);
	}

}
