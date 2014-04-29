package ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.Scrollable;

public class ScrollablePanel extends JPanel implements Scrollable{

	private final Dimension preferredViewport;
	private final int widthBeforeScrollBarAppears, heightBeforeScrollBarAppears;
	
	private final JPanel container;
	
	public ScrollablePanel(JPanel viewList, Dimension viewport, int widthLimit, int heightLimit)
	{
		this.container=viewList;
		this.preferredViewport = viewport;
		this.widthBeforeScrollBarAppears = widthLimit;
		this.heightBeforeScrollBarAppears = heightLimit;
	}
	
	@Override
	public Dimension getPreferredScrollableViewportSize() {
		return preferredViewport;
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
		return viewport.getHeight() > heightBeforeScrollBarAppears;			
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		final Container viewport = getParent();
		//TODO : value should be a function of EXTRA_COLS and QubjectProperty.values().size
		return viewport.getWidth() > widthBeforeScrollBarAppears;
	}

	@Override
	public int getScrollableUnitIncrement(Rectangle arg0, int arg1,
			int arg2) {
		// TODO Auto-generated method stub
		return 200;
	}
}