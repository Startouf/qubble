package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import qubject.MediaInterface;
import qubject.QubjectModifierInterface;
import actions.ChangeQubjectModifierAction;

public abstract class QubjectModifierPalette extends JFrame implements ActionListener{
	
	protected App app;
	protected final JPanel itemSelectionPanel = new JPanel();
    private JButton boutonVal;
    private JButton boutonAnn;
	protected QubjectModifierInterface selectedModifier;    
	private JComboBox combo = new JComboBox();
	private JLabel label = new JLabel("Choix du Qubject");
	
	public QubjectModifierPalette(App app) {
		
	   	super("modifier");
    	Dimension dim = new Dimension (325,80);
    	this.app=app;
    	this.setMinimumSize(dim);
    	this.setSize(325, 300);
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.setLocationRelativeTo(null);
    	
    	itemSelectionPanel.setBackground(Color.white);
    	itemSelectionPanel.setLayout(new BorderLayout());

    	boutonVal = new JButton("Valider");
    	boutonVal.setAction(this.app.getChangeQubjectModifierAction());
    	//boutonVal.setName("Valider");
    	boutonAnn = new JButton("Annuler");
    	boutonAnn.addActionListener(this);
 
    	JPanel south = new JPanel();
    	south.add(boutonVal);
    	south.add(boutonAnn);
    	itemSelectionPanel.add(south, BorderLayout.SOUTH);

    	//editModifiers();
    	//previsualisation();
    	
    	for (MediaInterface qubject : this.app.getQubjects())
    	{
    		combo.addItem(qubject.getName());
    	}
 
    	JPanel top = new JPanel();
    	top.add(label);
    	top.add(combo);    
    	itemSelectionPanel.add(top, BorderLayout.NORTH);
    	this.setContentPane(itemSelectionPanel);
    	pack();
    	this.setVisible(true);
	}
    
	/**
	 * Edition du modifier choisi pour le Qubject actif
	 */
	protected abstract void editModifiers();

	/**
	 * Ajout d'un espace de prévisualisation
	 */
	protected abstract void previsualisation();

	public QubjectModifierInterface getSelectedModifier() {
		return selectedModifier;
	}

	/**
	 * Change the active Modifier
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
