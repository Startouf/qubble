package ui;

import javax.swing.JMenu;

public class YAxisMenu extends JMenu
{
	private final App app;
	private final DistortionRadioMenuItem distortion;

	public YAxisMenu(App app, String name, String description, Integer key)
	{
		super(name);
		this.app = app;
		if (key != null)
		{
			this.setMnemonic(key);
		}
		this.getAccessibleContext().setAccessibleDescription(description);

		add(distortion = new DistortionRadioMenuItem(app));
	}
}