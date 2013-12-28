package ui;

import javax.swing.JMenuBar;

public class MenuBar extends JMenuBar
{
	private App app;
    private FileMenu fileMenu;
    private SettingsMenu settingsMenu;

    public MenuBar(App app){
	this.app = app;
	add(fileMenu = new FileMenu(app, "File", "File Menu", java.awt.event.KeyEvent.VK_F));
	add(settingsMenu = new SettingsMenu(app, "Settings", "Set settings for patterns", java.awt.event.KeyEvent.VK_S));
    }
}
