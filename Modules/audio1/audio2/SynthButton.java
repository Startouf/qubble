package audio2;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SynthButton extends JButton implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private final WaveForm window;
	
	public SynthButton(WaveForm window) {
		super("Synthesize !");
		this.window = window;
		
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		final JFileChooser fc = new JFileChooser();

		int returnVal = fc.showSaveDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			
			window.update(null, file);
			System.out.println("Opening: " + file.getName() + ".");
			
			window.synthesize(file);
			
		} else {
			System.out.println("Open command cancelled by user.");
		}

	}

}
