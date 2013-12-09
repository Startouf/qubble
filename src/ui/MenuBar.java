package ui;

import javax.swing.JMenuBar;

public class MenuBar extends JMenuBar
{
	private App app;
    private FileMenu fileMenu;
    private YAxisMenu yAxisMenu;

    public MenuBar(App app){
	this.app = app;
	add(fileMenu = new FileMenu(app, "File", "File Menu", java.awt.event.KeyEvent.VK_F));
	add(yAxisMenu = new YAxisMenu(app, "Y-Axis", "Set table Y-Axis effect", java.awt.event.KeyEvent.VK_Y));
    }
}
