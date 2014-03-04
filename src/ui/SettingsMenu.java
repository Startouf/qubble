package ui;

import javax.swing.JMenu;

public class SettingsMenu extends JMenu {
	private final App app;
	private final ViewIndividualMenuItem vimi;

	public SettingsMenu(App app, String string, String string2, int vkS) {
		super("Settings");
		this.app = app;
		this.setMnemonic(vkS);
		vimi = new ViewIndividualMenuItem(app);
		//Wait for a project being loaded before showing this button
		vimi.setVisible(false);
		add(vimi);
	}

	public void ShowProjectSettings(boolean projectOpened) {
		vimi.setVisible(projectOpened);
	}

}
