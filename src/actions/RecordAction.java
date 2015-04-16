package actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

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
		if(!recording){
			//Choose a place where to save the file
			JFileChooser chooser = new JFileChooser("");
		    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		    File f = null;
		    int returnVal = chooser.showOpenDialog(app);
	        if(returnVal == JFileChooser.APPROVE_OPTION) {
	           f = chooser.getSelectedFile();
	        } else return;
	        
			app.getActiveProject().startRecording(f);
			putValue(NAME, "Arrêter l'enregistrement");
		} else{
			putValue(NAME, "Enregistrer");
			app.getActiveProject().stopRecording();
		}
		recording = !recording;
	}

}
