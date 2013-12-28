package ui;

import javax.swing.JMenuItem;

public class ViewIndividualMenuItem extends JMenuItem {
	private final App app;
	
	public ViewIndividualMenuItem(App app) {
		super(app.getOpenIndividualSettingsAction());
		this.app = app;
		this.setText("Individual settings");
	}
}
