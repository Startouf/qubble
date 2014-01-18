package teacher;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ex1.*;
import ex2.*;



public class BrowseExercices
{
	private static JButton ex11;
	private static JButton ex12;
	private static JButton ex14;
	private static JButton ex15;

	private static JButton ex21;
	private static JButton ex23;
	private static JButton ex24;


	
	public static void main(String[] args){
		
		JFrame frame = new JFrame("select exercises");
		JPanel panel = new JPanel();
		
		ex11 = (new JButton(new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ProjectionPerspective app = new ProjectionPerspective();
				app.start();
			}
		}));
		ex11.setText("Ex 1.1 Projection Perspective");
		
		ex12 = (new JButton(new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ManySquares app = new ManySquares();
				app.start();
			}
		}));
		ex12.setText("Ex 1.2 Affichage de carrés");
		
		ex14 = (new JButton(new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				CubeRotates app = new CubeRotates();
				app.start();
			}
		}));
		ex14.setText("Ex 1.4 Rotation de Modèles");
		
		ex15 = (new JButton(new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				CameraRotates app = new CameraRotates();
				app.start();
			}
		}));
		ex15.setText("Ex 1.5 Rotation de Camera");
		
		
		ex23 = (new JButton(new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Lighting app = new Lighting();
				app.start();
			}
		}));
		ex23.setText("Ex 2.1 Lumière");
		
		ex24 = (new JButton(new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				NotNormalBehaviour app = new NotNormalBehaviour();
				app.start();
			}
		}));
		ex24.setText("Ex 2.1 Normales fluctuantes");
	panel.setLayout(new GridLayout(2,5));	
	panel.add(ex11);
	panel.add(ex12);
	panel.add(ex14);
	panel.add(ex15);
	panel.add(ex23);
	panel.add(ex24);
	
	frame.setContentPane(panel);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.pack();
	frame.setVisible(true);
	}
}
