package audio2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.awt.event.*;
import java.util.*;
import java.io.*;

public class SaveMenuItem extends JMenuItem implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final WaveForm window;
	
	public SaveMenuItem(WaveForm window) {
		super("Save a File");
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
			
			window.writeFile(file);
			
		} else {
			System.out.println("Open command cancelled by user.");
		}

	}

}