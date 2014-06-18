package ui;

import javax.swing.JMenuItem;

public class SaveMenuItem extends JMenuItem {
	private final App app;
	
	public SaveMenuItem(App app) {
		super(app.getSaveProjectAction());
		this.app = app;
		this.setText("Sauvegarder");
	}
}
