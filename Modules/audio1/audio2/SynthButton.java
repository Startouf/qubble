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
		JTextField formText = new JTextField("", 10);
		JTextField freqText = new JTextField("", 5);
		JTextField ampText = new JTextField("", 5);
		JTextField lengthText = new JTextField("", 5);
		
		JPanel myPanel = new JPanel();
		myPanel.add(new JLabel("Forme :"));
		myPanel.add(formText);
		myPanel.add(new JLabel("frequency :"));
		myPanel.add(freqText);
		myPanel.add(new JLabel("amp :"));
		myPanel.add(ampText);
		myPanel.add(new JLabel("length :"));
		myPanel.add(lengthText);
		
		int result = JOptionPane.showConfirmDialog(null,  myPanel, "Delay", JOptionPane.OK_CANCEL_OPTION);
		
		if (result == JOptionPane.OK_OPTION) {
			int form = Synthesizer.sine;
			switch(formText.getText()) {
			case "saw":
				form = Synthesizer.saw;
				break;
			case "triangle":
				form = Synthesizer.triangle;
				break;
			case "square":
				form = Synthesizer.square;
				break;
			default:
				break;
			}
			
			window.synthesize(form,
			Integer.parseInt(freqText.getText()),
			Integer.parseInt(ampText.getText()),
			Integer.parseInt(lengthText.getText())
			);
		}
	}

}
