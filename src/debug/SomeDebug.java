package debug;

import javax.swing.LookAndFeel;
import javax.swing.UIManager;


import ui.App;

public class SomeDebug {
	public static void main(String[] args){
		try
		{
			UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");
		} catch (Exception e) { /* Lazy handling this >.> */ }
		App DJTable = new App();
	}
}
