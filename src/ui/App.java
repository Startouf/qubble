package ui;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JFrame;

import actions.OpenIndividualSettingsAction;
import table.ExampleIncrementTimeAction;

/**
 * @author Cyril
 * The main window that allows the user to choose his settings
 * 
 * Visual representation :
 * https://docs.google.com/drawings/d/1NOopF11r3Y56fjHIWkAm6jW3N124hpSTVYJpzmWgv50/edit?usp=sharing
 *
 */
public class App extends JFrame
{
	private final MenuBar menu;
	private final MainPanel mainPanel;
	
	private final OpenIndividualSettingsAction oisa = new OpenIndividualSettingsAction(this);

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

	public OpenIndividualSettingsAction getOpenIndividualSettingsAction() {
		return oisa;
	}
	
}
