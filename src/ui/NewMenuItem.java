package ui;

import javax.swing.JMenuItem;

public class NewMenuItem extends JMenuItem
{
	private final App app;
	
	public NewMenuItem(App app) {
		super(app.getNewAction());
		this.app = app;
		this.setText("Nouveau Projet");
	}
}
