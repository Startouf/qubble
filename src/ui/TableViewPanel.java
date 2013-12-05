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
 * Also contains a field corresponding to the current time 
 * ...and the total period of the table
 *
 */
public class TableViewPanel extends JPanel
{
	private final App app;
	private final ExampleTableAnimation anim = new ExampleTableAnimation(this);
	//Set a time and a period
	private float time = 0;
	private float period = 60;

	public TableViewPanel(App app)
	{
		super();

		this.app = app;

		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(512,256));
	}

	@Override
	protected void paintComponent(Graphics g){
		//Incrementing time so it's enough to resize the table to get
		this.time = (time+1) % period;
		
		super.paintComponent(g);
		this.anim.paint(g);
	}
	
	public void setTime(float time){
		this.time = time % period;
	}

	public float getTime() {
		return this.time;
	}
}
