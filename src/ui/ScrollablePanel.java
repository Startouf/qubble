package ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.Scrollable;

class ScrollablePanel extends JPanel implements Scrollable{

	/**
	 * 
	 */
	private final ViewListPanel viewList;
	
	public ScrollablePanel(ViewListPanel viewList)
	{
		this.viewList=viewList;
	}
	
	

	@Override
	public Dimension getPreferredScrollableViewportSize() {
		// TODO Auto-generated method stub
		return new Dimension(600, 100);
	}

	@Override
	public int getScrollableBlockIncrement(Rectangle arg0, int arg1,
			int arg2) {
		// TODO Auto-generated method stub
		return 100;
	}

	@Override
	public boolean getScrollableTracksViewportHeight() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		final Container viewport = getParent();
		return viewport.getWidth() > 400;
	}

	@Override
	public int getScrollableUnitIncrement(Rectangle arg0, int arg1,
			int arg2) {
		// TODO Auto-generated method stub
		return 200;
	}
}