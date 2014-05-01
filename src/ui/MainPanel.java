package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import qubject.MediaInterface;
import qubject.QubjectModifierInterface;
import qubject.QubjectProperty;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.*;

/**
 * @author Cyril
 * The SuperPanel which contains the interesting sub-panels:
 * -> The TableViewPanel which displays elements(cubes, triangles...) on the table
 * -> A panel with useful buttons (adjust volume, turn off visual effects...)
 * -> ... Other stuff ?
 *
 */
public class MainPanel extends JPanel
{
	private final App app;
	private final JTabbedPane settingsTabs;	//Utilisation d'onglets
	private final GlobalSettingsPanel globalSettingsPanel;
	private static final Icon closeIcon = new ImageIcon("data/ui/closeTab.png"); 
	public static final BufferedImage backgroundImage;
	private final Hashtable<ProjectController, ArrayList<ViewQubjects>> viewQubjectsTab=
			new Hashtable<ProjectController, ArrayList<ViewQubjects>>();
	
	static{
		BufferedImage tryImage = null;
		try {
			tryImage = ImageIO.read(new FileInputStream("data/ui/fond 2.png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		backgroundImage = tryImage;
	}
	
	public MainPanel(App app)
	{
		super();
		this.app = app;
		
		this.setPreferredSize(new Dimension(650, 700));
		this.setLayout(new BorderLayout());

		//Tabs are added by the app
		settingsTabs = new JTabbedPane();
		//Note : code below makes the JTabbedPane transparent (e
		add(settingsTabs, BorderLayout.CENTER);
		add(globalSettingsPanel = new GlobalSettingsPanel(app), BorderLayout.SOUTH);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(backgroundImage != null)
			g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
	}

	public App getApp() {
		return app;
	}

	public void addCloseableTab(String title, final Component tab){
		
		// Add the tab to the pane without any label
		settingsTabs.addTab(null, tab);
		int pos = settingsTabs.indexOfComponent(tab);

		// Create a FlowLayout that will space things 5px apart
		FlowLayout f = new FlowLayout(FlowLayout.CENTER, 5, 0);

		// Make a small JPanel with the layout and make it non-opaque
		JPanel pnlTab = new JPanel(f);
		pnlTab.setOpaque(false);

		// Add a JLabel with title and the left-side tab icon
		JLabel lblTitle = new JLabel(title);
//		lblTitle.setIcon(closeIcon);

		// Create a JButton for the close tab button
		JButton btnClose = new JButton();
		btnClose.setOpaque(false);

		// Configure icon and rollover icon for button
		btnClose.setRolloverIcon(closeIcon);
		btnClose.setRolloverEnabled(true);
		btnClose.setIcon(closeIcon);

		// Set border null so the button doesn't make the tab too big
		btnClose.setBorder(null);

		// Make sure the button can't get focus, otherwise it looks funny
		btnClose.setFocusable(false);

		// Put the panel together
		pnlTab.add(lblTitle);
		pnlTab.add(btnClose);

		// Add a thin border to keep the image below the top edge of the tab
		// when the tab is selected
		pnlTab.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));

		// Now assign the component for the tab
		settingsTabs.setTabComponentAt(pos, pnlTab);

		// Add the listener that removes the tab
		btnClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// The component parameter must be declared "final" so that it can be
				// referenced in the anonymous listener class like this.
				settingsTabs.remove(tab);
			}
		});

		// Optionally bring the new tab to the front
		settingsTabs.setSelectedComponent(tab);
		repaint();
	}

	public JTabbedPane getSettingsTabs() {
		return settingsTabs;
	}

	public GlobalSettingsPanel getGlobalSettingsPanel() {
		return globalSettingsPanel;
	}
	
	public void setConfigForQubject(ProjectController project, MediaInterface qubject, 
			QubjectProperty prop, QubjectModifierInterface modifier){
		for (int i=0; i< settingsTabs.getTabCount(); i++){
			Component p = settingsTabs.getComponentAt(i);
			if (p instanceof ViewQubjects){
				ViewQubjects view = (ViewQubjects)p;
				if(view.isLinkedToProject(project)){
					view.setConfigForQubject(qubject, prop, modifier);
				}
			}
		}
	}

	public void closeTabsRelatedTo(ProjectController project) {
		for(int i=0; i< settingsTabs.getTabCount(); i++){
			Component c = settingsTabs.getComponentAt(i);
			if (c instanceof ViewQubjects){
				ViewQubjects view = (ViewQubjects)settingsTabs.getTabComponentAt(i);
				if(view.isLinkedToProject(project)){
					settingsTabs.remove(i);
				}
			}
		}
	}
}
