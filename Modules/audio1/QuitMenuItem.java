

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;


public class QuitMenuItem extends JMenuItem implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final WaveForm window;
	
	public QuitMenuItem(WaveForm window) {
		super("Quit");
		this.window = window;
		
		addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		int response = JOptionPane.showInternalOptionDialog(this,
				"Etes vous sur de vouloir quitter ?", "Quitter", 
				JOptionPane.YES_NO_OPTION, 
				JOptionPane.WARNING_MESSAGE, null, null, null);
		
		if (response == JOptionPane.OK_OPTION) {
			System.exit(0);
		}
		else return;

	}

}
