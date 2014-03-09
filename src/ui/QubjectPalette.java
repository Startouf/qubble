package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import actions.ChangeQubjectAction;
import qubject.MediaInterface;
import qubject.QRInterface;


/**
 * @author bertoli
 * @author duchon
 * Not really sure this palette should extend a generic Palette class, 
 * (The QubjectModifierPalette abstract class is more designed for QubjectModifiers). 
 * 
 * TODO : use something else than a JFrame. IT'S VERY BAD.
 */
public class QubjectPalette extends JFrame implements ActionListener
{

	private App app;
	private final JPanel qubjectSelection = new JPanel();
	private MediaInterface selectedQubject;
    private final ArrayList<ReferenceButton> qubjectList;
    private JButton boutonVal;
    private JButton boutonAnn;
    private JLabel label = new JLabel("Choix du Qubject");
    private JComboBox<String> combo = new JComboBox<String>();

	public QubjectPalette(App app) {
         	
        	super("Selection de Qubject");
        	Dimension dim = new Dimension (325,80);
        	this.app=app;
        	this.setMinimumSize(dim);
        	this.setSize(325, 300);
        	this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        	qubjectList = new ArrayList<ReferenceButton>();
        	this.setLocationRelativeTo(null);
        	
        	//Hide on close with a window listener
        	this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    e.getWindow().setVisible(false);
                }
            });
 
        	qubjectSelection.setBackground(Color.white);
        	qubjectSelection.setLayout(new BorderLayout());
   
        	boutonVal = new JButton("Valider");
        	boutonVal.setAction(this.app.getChangeQubjectAction());
        	boutonVal.setHideActionText(false);
       
        	boutonAnn = new JButton("Annuler");
        	boutonAnn.addActionListener(this);
        	
 
        	JPanel south = new JPanel();
        	south.add(boutonVal);
        	south.add(boutonAnn);
        	qubjectSelection.add(south, BorderLayout.SOUTH);
   
        	for (MediaInterface qubject : this.app.getQubjects())
        	{
        		combo.addItem(qubject.getName());
        	}

        	JPanel top = new JPanel();
        	top.add(label);
        	top.add(combo);    
        	qubjectSelection.add(top, BorderLayout.NORTH);
        	this.setContentPane(qubjectSelection);
        	pack();
        	this.setVisible(true); 
        
	}
    
	private void listQubjects()
	{
		for (MediaInterface qubject : this.app.getQubjects())
		{
			ReferenceButton cube = new ReferenceButton(qubject.getName(), qubject);
			cube.addActionListener(this);
			qubjectSelection.add(cube);
			qubjectList.add(cube);
		}
	}

	public MediaInterface getSelectedQubject() {
		String str = (String) combo.getSelectedItem();
		for (MediaInterface qubject : this.app.getQubjects()){
			if(qubject.getName().equals(str)){
				return qubject;
			}
		}
		return null;
	}

	/**
	 * Highlights the Qubjetc qubject (but does not validate !)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
	
			this.setVisible(false);
	}
}
