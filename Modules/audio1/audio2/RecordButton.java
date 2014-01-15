package audio2;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class RecordButton extends JButton implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private final WaveForm window;
	private boolean recording;
	
	public RecordButton(WaveForm window) {
		super("Record");
		this.window = window;
		
		recording = false;
		addActionListener(this);
	}
	
	public final void actionPerformed(ActionEvent evt) {
		
		//stopper.start();
		if (!recording) {
			
			
			final JFileChooser fc = new JFileChooser();

			int returnVal = fc.showSaveDialog(this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				
				window.update(null, file);
				System.out.println("Opening: " + file.getName() + ".");
				
				window.startRecording(file);
				this.setText("Stop");
				recording = true;
			} else {
				System.out.println("Open command cancelled by user.");
			}
			
			
		} else {
			this.setText("Record");
			recording = false;
			window.stopRecording();
		}
	}

}
