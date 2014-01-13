
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class PlayButton extends JButton implements ActionListener {
	private final WaveForm window;
	
	public PlayButton(WaveForm window) {
		super("Play !");
		this.window = window;
		
		addActionListener(this);
	}
	
	public final void actionPerformed(ActionEvent evt) {
		window.play();
	}
}
