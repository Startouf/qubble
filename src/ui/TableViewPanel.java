package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import debug.ExampleTableAnimation;

/**
 * @author Cyril
 * Displays a simplified view of the current state of the table
 * -> Cursor position
 * -> Elements that are on the table (simple 2D shapes, or maybe even just dots, crosses,...)
 * 
 * Contient aussi un paramètre time qui correspond à la position du curseur sur l'axe du temps
 * (Le temps total est pris égal à 60s)
 *
 */
public class TableViewPanel extends JPanel
{
	private final App app;
	private final ExampleTableAnimation anim = new ExampleTableAnimation(this);
	private float time = 0;

	public TableViewPanel(App app)
	{
		super();

		this.app = app;

		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(512,256));
	}

	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		this.anim.paint(g);
	}

	public float getTime() {
		//Fonction time à générer correctement. Le code ci-dessous est de la "bullshit"
		this.time = (time+1) % 60;
		return this.time;
	}
}
