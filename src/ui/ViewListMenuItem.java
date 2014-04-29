package ui;

import javax.swing.JMenuItem;

public class ViewListMenuItem extends JMenuItem {
	private final App app;
	
	public ViewListMenuItem(App app) {
		super(app.getOpenListSettingsAction());
		this.app = app;
		this.setText("Vue en tableur");
	}
}
