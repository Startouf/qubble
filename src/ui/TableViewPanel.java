package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import table.ExampleTableAnimation;


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
	
	//Note : time values are "stored" here temporarily (until appropriate model is created)
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
		super.paintComponent(g);
		this.anim.paint(g);
	}

	public void setTime(float time){
		this.time = time % period;
		this.anim.setTime(this.time);
	}

	public float getTime() {
		return this.time;
	}

	public float getPeriod() {
		return period;
	}

	public void setPeriod(float period) {
		this.period = period;
	}

	public App getApp() {
		return app;
	}

	public ExampleTableAnimation getAnim() {
		return anim;
	}

	public void incrementTime() {
		this.setTime(time+1);
	}
	
}
