package actions;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Hashtable;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import qubject.QubjectProperty;
import sequencer.Qubble;
import sequencer.QubbleInterface;
import ui.App;
import ui.NewMenuItem;
import ui.ProjectController;

public class NewProjectAction extends AbstractAction
{
	private App app;
	
	//For custom Project generation pop up window
	class PropertyOptionsPanel extends JPanel{
		private final JRadioButton random= new JRadioButton("Aléatoire"), 
				none = new JRadioButton("Aucun"), 
				stable = new JRadioButton("Prédéfini");
		private final ButtonGroup bg = new ButtonGroup();
		
		public PropertyOptionsPanel(){
			bg.add(random); bg.add(none); bg.add(stable);
		}
	}
	
	private Hashtable<QubjectProperty, PropertyOptionsPanel> customizeProps
		= new Hashtable<QubjectProperty, PropertyOptionsPanel>();
	
	public NewProjectAction(App app){
		this.app = app;
		putValue(NAME, "Nouveau projet");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() instanceof NewMenuItem){
			fastNewProject();
		} else if (arg0.getSource() instanceof JButton){
			fastNewProject();
		}
		
		this.app.getWelcomePanel().disableProjects();
		this.app.getWelcomePanel().addProjectEntry(this.app.getActiveProject());
		this.app.setProjectOpened(true);
		this.app.getChangeProjectNameAction().actionPerformed(arg0, "Nouveau projet");
		
		//Tell GUI to remain on top
		Thread t = new Thread(new Runnable() {
		    public synchronized void run() {
		    	try {
					wait(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	app.toFront();
		    }
		});
		t.start();
	}
	
	private void fastNewProject(){
		this.app.setActiveProject(new ProjectController(app, (QubbleInterface) new Qubble()));
	}
	
	private void advancedNewProject(){
		
		//Ways to initialise Qubjects
		for (QubjectProperty prop : QubjectProperty.values()){
			customizeProps.put(prop, new PropertyOptionsPanel());
		}

		JPanel choicePanel = new JPanel(new GridLayout(0, 1));

		int result = JOptionPane.showConfirmDialog(null, choicePanel, "Nouveau Projet",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		//TODO

		//Only create maze if parameters are correct and user presses OK
//		while (!ok){
//			if (result == JOptionPane.OK_OPTION) { //If user presses OK
//				try{
//					int width = Integer.parseInt(row.getText());
//					int height = Integer.parseInt(col.getText());
//					if (width < 2 || height < 2){ //If dimensions >= 2
//						JOptionPane.showMessageDialog(null, "Incorrect Dimensions ! Must be at least 2x2");
//						break;
//					}
//					String mazeType;
//					if (hex.isSelected()){
//						mazeType = "Hexagon";
//						this.app.setDrawingMazeModel(new HexagonMazeModel(this.app, 
//								this.app.getAdaptor().newMaze(mazeType, 
//										new Dimension (width, height)), 
//										this.app.getMazePanel().getSize()));
//					}
//					else{
//						mazeType = "Square";
//						this.app.setDrawingMazeModel(new SquareMazeModel(this.app, 
//								this.app.getAdaptor().newMaze(mazeType, 
//										new Dimension (width, height)), 
//										this.app.getMazePanel().getSize()));
//					}
//
//					this.app.getMazePanel().repaint();
//					ok = true;
//				}
//				catch (NumberFormatException error){
//					error.printStackTrace();
//				}
//			} else { //If user presses cancel
//				ok = true;
//				System.out.println("Cancelled");
//			}
//		}
		choicePanel.removeAll();
	}

}
