package ui;

import javax.swing.JMenuBar;

public class MenuBar extends JMenuBar
{
	private App app;
    private FileMenu fileMenu;
    private SettingsMenu settingsMenu;

    public MenuBar(App app){
    	this.app = app;
    	add(fileMenu = new FileMenu(app, "Fichier", "File Menu", java.awt.event.KeyEvent.VK_F));
    	add(settingsMenu = new SettingsMenu(app, "Options Qubject", "Set settings for qubjects", java.awt.event.KeyEvent.VK_S));
    }
   
    /**
     * Debug Overload
     * @param app
     * @param showAll menu items
     */
    public MenuBar(App ap, boolean showAll){
    	this.app = app;
    	add(fileMenu = new FileMenu(app, "File", "File Menu", java.awt.event.KeyEvent.VK_F));
    	add(settingsMenu = new SettingsMenu(app, "Options", "Set settings for patterns", java.awt.event.KeyEvent.VK_S, showAll));
    }

	public void showProjectSettings(boolean projectOpened) {
		settingsMenu.ShowProjectSettings(projectOpened);
	}
}
