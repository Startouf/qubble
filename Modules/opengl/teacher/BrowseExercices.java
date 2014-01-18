package teacher;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ex1.*;
import ex2.*;

/**
 * @author Cyril
 * Quick window to launch any of the exercices
 * Written in an ugly manner, but does the job
 *
 */
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
				ProjectionPerspective.main(null);
			}
		}));
		ex11.setText("Ex 1.1 Projection Perspective");
		
		ex12 = (new JButton(new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ManySquares.main(null);
			}
		}));
		ex12.setText("Ex 1.2 et 1.3 Affichage de carrés");
		
		ex14 = (new JButton(new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				CubeRotates.main(null);
			}
		}));
		ex14.setText("Ex 1.4 Rotation de Modèles");
		
		ex15 = (new JButton(new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				CameraRotates.main(null);
			}
		}));
		ex15.setText("Ex 1.5 Rotation de Camera");
		
		ex21 = (new JButton(new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SingleTexture.main(null);
			}
		}));
		ex21.setText("Ex 2.1 Textures");
		
		ex23 = (new JButton(new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Lighting.main(null);
			}
		}));
		ex23.setText("Ex 2.3 Lumière");
		
		ex24 = (new JButton(new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				NotNormalBehaviour.main(null);
			}
		}));
		ex24.setText("Ex 2.1 Normales fluctuantes");
	panel.setLayout(new GridLayout(2,5));	
	panel.add(ex11);
	panel.add(ex12);
	panel.add(ex14);
	panel.add(ex15);
	panel.add(ex21);
	panel.add(ex23);
	panel.add(ex24);
	
	frame.setContentPane(panel);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.pack();
	frame.setVisible(true);
	}
}
