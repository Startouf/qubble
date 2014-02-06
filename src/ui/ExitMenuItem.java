package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

public class ExitMenuItem extends JMenuItem
{
	private final App app;

	public ExitMenuItem(App app) 
	{
		super("Quitter");
		this.app = app;
		
		//Note : The following code terminates the JVM (exits the program). 
		//Just ignore it if you don't understand the use of parenthesis or braces.
		this.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e){
		    	//TODO : popup window asking the user to save changes
		        System.exit(0); //0 = normal program termination
		    }
		});
	}
}