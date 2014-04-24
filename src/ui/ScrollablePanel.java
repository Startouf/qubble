package ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.Scrollable;

public final class ScrollablePanel extends JPanel implements Scrollable{

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
		final Container viewport = getParent();
		//TODO : value should be a function of QubjectList.size
		return viewport.getHeight() > 400;			
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		final Container viewport = getParent();
		//TODO : value should be a function of EXTRA_COLS and QubjectProperty.values().size
		return viewport.getWidth() > 800;
	}

	@Override
	public int getScrollableUnitIncrement(Rectangle arg0, int arg1,
			int arg2) {
		// TODO Auto-generated method stub
		return 200;
	}
}