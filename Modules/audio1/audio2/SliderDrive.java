package audio2;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.*;

public class SliderDrive extends JSlider implements ChangeListener {

	WaveForm window;
	
	public SliderDrive(WaveForm w) {
		super(10, 70, 10);
		window = w;
		addChangeListener(this);
	}
	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub

	}

}
