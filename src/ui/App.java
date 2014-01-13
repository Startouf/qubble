package ui;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JFrame;

import actions.*;
import table.ExampleIncrementTimeAction;

/**
 * @author Cyril|duchon
 * @author Karl|bertoli
 * The main window that allows the user to choose his settings
 * 
 * Visual representation :
 * https://docs.google.com/drawings/d/1NOopF11r3Y56fjHIWkAm6jW3N124hpSTVYJpzmWgv50/edit?usp=sharing
 *
 */
public class App extends JFrame
{
	//Note : create actions first because menus need them!
	private final NewAction newAction = new NewAction(this);
	private final LoadAction loadAction = new LoadAction(this);
	private final OpenIndividualSettingsAction openIndividualSettingsAction 
		= new OpenIndividualSettingsAction(this);
	
	private PatternSelectionFrame patternSelectionFrame = null;
	private SampleSelectionFrame sampleSelectionFrame = null;
	private final MenuBar menu = new MenuBar(this);
	private final MainPanel mainPanel= new MainPanel(this);

	public App()
	{
		super("DJ-Table");

		setJMenuBar(menu);
		setContentPane(mainPanel);

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
		return openIndividualSettingsAction;
	}

	public NewAction getNewAction() {
		return newAction;
	}

	public LoadAction getLoadAction() {
		return loadAction;
	}

	public PatternSelectionFrame getPatternSelectionFrame() {
		if(patternSelectionFrame != null){
			patternSelectionFrame.setVisible(true);
			return patternSelectionFrame;
		}
		else{
			patternSelectionFrame = new PatternSelectionFrame(this);
			return patternSelectionFrame;
		}
	}

	public SampleSelectionFrame getSampleSelectionFrame() {
		if(sampleSelectionFrame != null){
			sampleSelectionFrame.setVisible(true);
			return sampleSelectionFrame;
		}
		else{
			sampleSelectionFrame = new SampleSelectionFrame(this);
			return sampleSelectionFrame;
		}
	}
	
}
