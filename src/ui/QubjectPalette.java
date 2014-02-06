package ui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * @author bertoli
 *
 */
public class QubjectPalette extends JFrame
{

	private App app;
	private final JPanel patternSelection = new JPanel();
    GridLayout patternLayout = new GridLayout(6,2);
    private ArrayList<JButton> listCube;
	public QubjectPalette(App app) {
		
		super("Selection de pattern");
		this.app=app;
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
        this.initPatternSelectionFrame();
        setContentPane(patternSelection);
        pack();
        setVisible(true);
        

	}
    
	private void initPatternSelectionFrame()
	{
		
		patternSelection.setLayout(patternLayout);
		
		for (int i =0;i<12;i++)
		{
		JButton cube = new JButton("Cube "+Integer.toString(i+1));
        patternSelection.add(cube);
        listCube.add(cube);
        
        //TODO: faire le "retour" des cube
		}
	}
}
