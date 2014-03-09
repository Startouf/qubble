package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import qubject.MediaInterface;
import qubject.QubjectModifierInterface;
import actions.ChangeQubjectModifierAction;
/**
 * Abstract class for all QubjectModifiers Palettes. (factorisation of validate/cancel button)
 * @author Cyril
 * @author bertolli
 * TODO : use something else than a JFrame. IT'S VERY BAD.
 */
public abstract class QubjectModifierPalette extends JFrame implements ActionListener{
	
	protected App app;
	protected final JPanel itemSelectionPanel = new JPanel();
    private JButton boutonVal;
    private JButton boutonAnn;
	protected QubjectModifierInterface selectedModifier;    
	protected final JComboBox combo;

	
	public QubjectModifierPalette(App app, String paletteName) {
		
	   	super(paletteName);
    	Dimension dim = new Dimension (325,80);
    	this.app=app;
    	this.setMinimumSize(dim);
    	this.setSize(325, 300);
    	this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    	this.setLocationRelativeTo(null);
    	
    	itemSelectionPanel.setBackground(Color.white);
    	itemSelectionPanel.setLayout(new BorderLayout());
    	
    	//Hide on close with a window listener
    	this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                e.getWindow().setVisible(false);
            }
        });

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
    	combo = fillCombo();
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
