package teacher;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ex1.*;
import ex2.*;
import ex3.*;

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
	private static JButton ex22;
	private static JButton ex23;
	private static JButton ex24;
	
	private static JButton ex31;
	private static JButton ex31bis;
	private static JButton ex32;
	private static JButton ex33;
	
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
		ex21.setText("Ex 2.1 Une texture sur cube");
		
		ex22 = (new JButton(new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SingleTexture.main(null);
			}
		}));
		ex22.setText("Ex 2.2 6 faces 6 textures");
		
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
		ex24.setText("Ex 2.4 Normales fluctuantes");
		
		ex31 = (new JButton(new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				CubeVBO.main(null);
			}
		}));
		ex31.setText("Ex 3.1 Cube en VBO");
		
		ex31bis = (new JButton(new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				CubeIBO.main(null);
			}
		}));
		ex31bis.setText("Ex 3.1 Cube en IBO");
		
		ex32 = (new JButton(new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				EnhancedCubesIBO.main(null);
			}
		}));
		ex32.setText("Ex 3.2 Cubes en IBO avec COuleurs/normales");
		
		ex33 = (new JButton(new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				CubeShaders.main(null);
			}
		}));
		ex33.setText("Ex 3.3 Cubes en IBO avec Shaders");
		
	panel.setLayout(new GridLayout(3,4));	
	panel.add(ex11);
	panel.add(ex12);
	panel.add(ex14);
	panel.add(ex15);
	panel.add(ex21);
	panel.add(ex22);
	panel.add(ex23);
	panel.add(ex24);
	panel.add(ex31);
	panel.add(ex31bis);
	panel.add(ex32);
	panel.add(ex33);
	
	frame.setContentPane(panel);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.pack();
	frame.setVisible(true);
	}
}
