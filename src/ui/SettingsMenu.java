package ui;

import javax.swing.JMenu;

public class SettingsMenu extends JMenu {
	private final App app;
	private final ViewIndividualMenuItem vimi;
	private final ViewListMenuItem vlmi;

	public SettingsMenu(App app, String string, String string2, int vkS) {
		super("Settings");
		//TODO : string & string2 .?????
		this.app = app;
		this.setMnemonic(vkS);
		vimi = new ViewIndividualMenuItem(app);
		vlmi = new ViewListMenuItem(app);
		//Wait for a project being loaded before showing this button
		vimi.setVisible(false);
		vlmi.setVisible(false);
		add(vimi);
		add(vlmi);
	}

	public SettingsMenu(App app, String string, String string2, int vkS,
			boolean showAll) {
		super("Settings");
		this.app = app;
		this.setMnemonic(vkS);
		vimi = new ViewIndividualMenuItem(app);
		vlmi = new ViewListMenuItem(app);
		//Wait for a project being loaded before showing this button
		vimi.setVisible(showAll);
		add(vimi);
		add(vlmi);
	}

	public void ShowProjectSettings(boolean projectOpened) {
		vimi.setVisible(projectOpened);
		vlmi.setVisible(projectOpened);
	}

}
