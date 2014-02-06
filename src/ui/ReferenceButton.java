package ui;

import javax.swing.JButton;

public class ReferenceButton extends JButton {
	private final Object reference;
	
	public ReferenceButton(String text, Object reference){
		super(text);
		this.reference = reference;
	}

	public Object getReference() {
		return reference;
	}
}
