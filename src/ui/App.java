package ui;

import javax.swing.JFrame;

import table.ExampleIncrementTimeAction;

public class App extends JFrame
{
	private final MenuBar menu;
	private final MainPanel mainPanel;
	private final ExampleIncrementTimeAction incrementTimeAction 
		= new ExampleIncrementTimeAction(this);

	public App()
	{
		super("DJ-Table");

		setJMenuBar(menu = new MenuBar(this));

		//Window content

		mainPanel = new MainPanel(this);
		setContentPane(mainPanel);

		//End window
				
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	public MenuBar getMenu() {
		return menu;
	}

	public MainPanel getMainPanel() {
		return mainPanel;
	}

	public ExampleIncrementTimeAction getIncrementTimeAction() {
		return incrementTimeAction;
	}
	
}
