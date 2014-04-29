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
    	add(settingsMenu = new SettingsMenu(app, "Propriétés des Qubjects", java.awt.event.KeyEvent.VK_O));
    }
   
    /**
     * Debug Overload
     * @param app
     * @param showAll menu items
     */
    public MenuBar(App ap, boolean showAll){
    	this.app = app;
    	add(fileMenu = new FileMenu(app, "Fichier", "File Menu", java.awt.event.KeyEvent.VK_F));
    	add(settingsMenu = new SettingsMenu(app, "Options", java.awt.event.KeyEvent.VK_O, showAll));
    }

	public void showProjectSettings(boolean projectOpened) {
		settingsMenu.ShowProjectSettings(projectOpened);
	}
}
