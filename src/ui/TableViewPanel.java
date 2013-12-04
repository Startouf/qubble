package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * @author Cyril
 * Displays a simplified view of the current state of the table
 * -> Cursor position
 * -> Elements that are on the table (simple 2D shapes, or maybe even just dots, crosses,...)
 *
 */
public class TableViewPanel extends JPanel
{
	private final App app;

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
	}
}
