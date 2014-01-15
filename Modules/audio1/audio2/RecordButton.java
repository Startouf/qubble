package audio2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RecordButton extends JButton implements ActionListener {

	private final WaveForm window;
	private boolean recording;
	
	public RecordButton(WaveForm window) {
		super("Record");
		this.window = window;
		
		recording = false;
		addActionListener(this);
	}
	
	public final void actionPerformed(ActionEvent evt) {
		//window.play();
		if (!recording) {
			this.setText("Stop");
			recording = true;
			window.startRecording();
		}
		else {
			this.setText("Record");
			recording = false;
			window.stopRecording();
		}
	}

}
