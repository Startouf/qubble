package ui;

import javax.swing.JMenu;

public class FileMenu extends JMenu
{
	private final App app;
	
	private final NewMenuItem newProject;
	private final LoadMenuItem load;
	private final ExitMenuItem exit;
	private final SaveMenuItem save;

	public FileMenu(App app, String name, String description, Integer key)
	{
		super(name);
		this.app = app;
		if (key != null)
		{
			this.setMnemonic(key);
		}
		this.getAccessibleContext().setAccessibleDescription(description);

		add(newProject = new NewMenuItem(app));
		add(save = new SaveMenuItem(app));
		add(load = new LoadMenuItem(app));
		add(exit = new ExitMenuItem(app));
	}
}
