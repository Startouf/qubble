package debug;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import database.Data;

import sequencer.Qubble;
import ui.App;
import ui.MainPanel;
import ui.ProjectController;

public class DebugLaunch
{
	public static void main(String[] args){
		JFrame debug = new JFrame();
		JPanel choices = new JPanel();
		choices.setLayout(new GridLayout());
		
		JButton quickProject = new JButton(new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				App debug = new App(
						new ProjectController(null, //The projectController might not need a ref to the app ?
								new Qubble(new Data())));
				}
		});
		quickProject.setText("Quick New Project with a loaded Qubble");
		quickProject.setPreferredSize(new Dimension (256, 256));
		
		choices.add(quickProject);
		debug.setContentPane(choices);
		debug.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		debug.pack();
		debug.setVisible(true);
	}
}
