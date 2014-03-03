package audio2;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class PauseButton extends JButton implements ActionListener {
	private final WaveForm window;
	
	public PauseButton(WaveForm window) {
		super("Pause");
		this.window = window;
		
		addActionListener(this);
	}
	
	public final void actionPerformed(ActionEvent evt) {
		window.pause();
	}
}