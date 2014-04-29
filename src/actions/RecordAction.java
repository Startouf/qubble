package actions;

import java.awt.event.ActionEvent;
import java.io.FileInputStream;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import ui.App;

public class RecordAction extends AbstractAction {
	private App app;
	private static final ImageIcon startRecord = new ImageIcon("data/ui/record small.png");
	private static final ImageIcon endRecord = new ImageIcon("data/ui/recording small.png");

	public RecordAction(App app){
		this.app = app;
		putValue(LARGE_ICON_KEY, startRecord);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
	}

}
