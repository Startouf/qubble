package audio2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class DelayButton extends JButton implements ActionListener {

	private final WaveForm wavForm;
	
	public DelayButton(WaveForm wavForm) {
		super("Delay");
		
		this.wavForm = wavForm;
		
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JTextField rateText = new JTextField("", 5);
		JTextField decayText = new JTextField("", 5);
		JTextField feedbackText = new JTextField("", 5);
		JTextField dryText = new JTextField("", 5);
		JTextField wetText = new JTextField("", 5);
		
		JPanel myPanel = new JPanel();
		myPanel.add(new JLabel("rate :"));
		myPanel.add(rateText);
		myPanel.add(new JLabel("decay :"));
		myPanel.add(decayText);
		myPanel.add(new JLabel("feedback :"));
		myPanel.add(feedbackText);
		myPanel.add(new JLabel("dry :"));
		myPanel.add(dryText);
		myPanel.add(new JLabel("wet :"));
		myPanel.add(wetText);
		
		int result = JOptionPane.showConfirmDialog(null,  myPanel, "Delay", JOptionPane.OK_CANCEL_OPTION);
		
		if (result == JOptionPane.OK_OPTION) {
			wavForm.applyDelay(
			Integer.parseInt(rateText.getText()),
			Integer.parseInt(decayText.getText()),
			Integer.parseInt(feedbackText.getText()),
			Integer.parseInt(dryText.getText()),
			Integer.parseInt(wetText.getText())
			);
		}
		else {
			wavForm.applyDisto(30, 10000);
		}
		

	}

}
