package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
 */
public class QubjectPalette extends JFrame implements ActionListener
{

	private App app;
	 
	private final JPanel qubjectSelection = new JPanel();
	private MediaInterface selectedQubject;
    private final ArrayList<ReferenceButton> qubjectList;
    private JButton boutonVal = new JButton("Valider");
    private JButton boutonAnn = new JButton("Annuler");
    private JLabel label = new JLabel("Choix du Qubject");
    private JComboBox combo = new JComboBox();

	public QubjectPalette(App app) {
//		super("Selection de Qubject");
//		this.app=app;
//		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
//		qubjectList = new ArrayList<ReferenceButton>();
//		qubjectSelection.setLayout(new GridLayout());
//        this.listQubjects();
//        /**
//         * Le bouton valider déclenche l'action de cette classe qui change la référence du qubject actif
//         * TODO Le Qubject actif sera mis en surbrillance
//         */
//        JButton validate = new JButton("Valider");
//        validate.setAction(this.app.getChangeQubjectAction());
//        qubjectSelection.add(validate);
//        setContentPane(qubjectSelection);
//        pack();
//        setVisible(true); 
//        
         	
        	super("Selection de Qubject");
        	Dimension dim = new Dimension (325,80);
        	this.app=app;
        	this.setMinimumSize(dim);
        	this.setSize(325, 300);
        	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        	qubjectList = new ArrayList<ReferenceButton>();
        	this.setLocationRelativeTo(app);
 
        	qubjectSelection.setBackground(Color.white);
        	qubjectSelection.setLayout(new BorderLayout());
   
  // bouton.addActionListener(new BoutonListener());     
   //bouton2.addActionListener(new Bouton2Listener());
    //bouton2.setEnabled(false);    
        	JPanel south = new JPanel();
        	south.add(boutonVal);
        	south.add(boutonAnn);
        	qubjectSelection.add(south, BorderLayout.SOUTH);
   
        	for (MediaInterface qubject : this.app.getQubjects())
        	{
        		combo.addItem(qubject.getName());
        	}
       
    //combo.addActionListener(new FormeListener());
    
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
		return selectedQubject;
	}

	/**
	 * Highlights the Qubjetc qubject (but does not validate !)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ReferenceButton.class){
			ReferenceButton Rbutton = (ReferenceButton) e.getSource();
			this.selectedQubject = (MediaInterface) Rbutton.getReference();
		}
	}
}
