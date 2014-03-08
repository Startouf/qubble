package debug;

import sequencer.Qubble;
import sequencer.QubbleInterface;
import ui.App;
import ui.ProjectController;
import ui.ViewIndividualPanel;
import database.Data;

public class UIDebug
{
	public static void main(String[] args) {
		//Qubble qubble = new Qubble(new Data(), false);
		App debug = new App();
		debug.setProjectOpened(true);
		debug.setActiveProject(new ProjectController(debug, (QubbleInterface) new Qubble(debug.getGlobalController().getData(), false)));
		debug.getMainPanel().getSettingsTabs().addTab(
				"Config. indiv. "+debug.getActiveProject().getProjectName(), new ViewIndividualPanel(debug));
	}
}
