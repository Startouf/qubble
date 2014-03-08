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
	protected final JComboBox combo = new JComboBox();

	
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

    	boutonVal = new JButton();
    	boutonVal.setAction(this.app.getChangeQubjectModifierAction());
    	boutonVal.setHideActionText(false);
    	
    	boutonAnn = new JButton("Annuler");
    	boutonAnn.addActionListener(this);
 
    	JPanel south = new JPanel();
    	south.add(boutonVal);
    	south.add(boutonAnn);
    	itemSelectionPanel.add(south, BorderLayout.SOUTH);

    	previsualisation();
    	fillCombo();
    	JPanel top = new JPanel();
    	top.add(label());
    	top.add(combo);    
    	itemSelectionPanel.add(top, BorderLayout.NORTH);
    	this.setContentPane(itemSelectionPanel);
    	pack();
    	this.setVisible(true);
	}
	/**
	 * fill the combo 
	 */
	protected abstract JComboBox<?> fillCombo();
	
	/**
	 * Return le label de la palette
	 * @return
	 */
	protected abstract JLabel label();
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
