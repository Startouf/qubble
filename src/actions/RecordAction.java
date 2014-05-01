package actions;

import java.awt.event.ActionEvent;
import java.io.FileInputStream;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import ui.App;

public class RecordAction extends AbstractAction {
	private App app;
	private static final ImageIcon startRecord = new ImageIcon("data/ui/rec small.png");
	private static final ImageIcon endRecord = new ImageIcon("data/ui/recording small.png");
	private boolean recording = false;

	public RecordAction(App app){
		this.app = app;
		putValue(NAME, "Enregistrer");
		putValue(LARGE_ICON_KEY, startRecord);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(recording){
			putValue(NAME, "Arrêter l'enregistrement");
			//TODO
		} else{
			putValue(NAME, "Enregistrer");
			//TODO
		}
	}

}
