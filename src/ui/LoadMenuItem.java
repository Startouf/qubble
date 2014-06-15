package ui;

import javax.swing.JMenuItem;

public class LoadMenuItem extends JMenuItem
{
	private final App app;
	
	public LoadMenuItem(App app) {
		super(app.getLoadAction());
		this.app = app;
		this.setText("Charger un projet");
	}
}
