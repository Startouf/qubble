package ui;

import javax.swing.Action;
import javax.swing.JButton;

public class ReferenceButton extends JButton {
	private final Object reference;
	
	public ReferenceButton(Object reference, Action action){
		super(action);
		this.reference = reference;
	}
	
	public ReferenceButton(String text, Object reference){
		super(text);
		this.reference = reference;
	}

	public Object getReference() {
		return reference;
	}
}
