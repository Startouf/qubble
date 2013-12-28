package ui;

import javax.swing.JMenu;

public class SettingsMenu extends JMenu {
	private final App app;
	private final ViewIndividualMenuItem vimi;

	public SettingsMenu(App app, String string, String string2, int vkS) {
		super("Settings");
		this.app = app;
		this.setMnemonic(vkS);
		
		add(vimi = new ViewIndividualMenuItem(app));
	}

}
