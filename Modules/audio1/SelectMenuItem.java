import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.awt.event.*;
import java.util.*;
import java.io.*;

public class SelectMenuItem extends JMenuItem implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final WaveForm window;
	
	public SelectMenuItem(WaveForm window) {
		super("Open a File");
		this.window = window;
		
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		final JFileChooser fc = new JFileChooser();

		int returnVal = fc.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			
			window.update(null, file);
			System.out.println("Opening: " + file.getName() + ".");
		} else {
			System.out.println("Open command cancelled by user.");
		}

	}

}
