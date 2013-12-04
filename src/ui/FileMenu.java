package ui;

import javax.swing.JMenu;

public class FileMenu extends JMenu
{
	private final App app;
	private final ExitMenuItem exit;

	public FileMenu(App app, String name, String description, Integer key)
	{
		super(name);
		this.app = app;
		if (key != null)
		{
			this.setMnemonic(key);
		}
		this.getAccessibleContext().setAccessibleDescription(description);

		add(exit = new ExitMenuItem(app));
	}
}
