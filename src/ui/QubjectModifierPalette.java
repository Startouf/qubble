package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import qubject.QubjectModifierInterface;
/**
 * Abstract class for all QubjectModifiers Palettes. (factorisation of validate/cancel button)
 * @author Cyril
 * @author bertolli
 * TODO : use something else than a JFrame. IT'S VERY BAD 
 * (some guy from StackOverflow says...he had good arguments though) .
 */
public abstract class QubjectModifierPalette extends Palette implements ActionListener{
	
	protected App app;
	protected final JPanel itemSelectionPanel = new JPanel();
    private JButton boutonVal;
    private JButton boutonAnn; 

    /**
     * Note : other components use the combo created by the palettes !
     */
	protected final JComboBox combo;

	
	public QubjectModifierPalette(App app, String paletteName) {
		
	   	super(app, paletteName);
    	Dimension dim = new Dimension (325,80);
    	this.app=app;
    	this.setMinimumSize(dim);
    	this.setSize(325, 300);
    	this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    	
    	
    	itemSelectionPanel.setBackground(Color.white);
    	itemSelectionPanel.setLayout(new BorderLayout());
    	
    	//Hide on close with a window listener
    	this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                e.getWindow().setVisible(false);
            }
        });

    	addBasicButtons();
    	itemSelectionPanel.add(addPrevisualisation(), BorderLayout.CENTER);
    	
    	//The useful stuff :
    	combo = fillCombo();
    	JPanel top = new JPanel();
    	top.add(labelPalette());
    	top.add(combo);    
    	itemSelectionPanel.add(top, BorderLayout.NORTH);
    	
    	this.setContentPane(itemSelectionPanel);
    	pack();
    	this.setVisible(false);
	}

	/**
	 * Add Validate and cancel buttons
	 */
	private void addBasicButtons(){
		boutonVal = new JButton("Valider");
    	boutonVal.addActionListener(this);
    	boutonAnn = new JButton("Annuler");
    	JPanel south = new JPanel();
    	south.add(boutonVal);
    	south.add(boutonAnn);
    	itemSelectionPanel.add(south, BorderLayout.SOUTH);
	}
	
	/**
	 * fill the combo 
	 */
	protected abstract JComboBox fillCombo();
	
	/**
	 * Return le label de la palette
	 * @return
	 */
	protected abstract JLabel labelPalette();
	
	/**
	 * Ajout d'un espace de prévisualisation
	 * @return Le panel qui sera ajouté au milieu de la palette
	 */
	protected abstract JPanel addPrevisualisation();

	/**
	 * Selected modifier grabbed by several actions AND other components
	 * @return
	 */
	public abstract QubjectModifierInterface getSelectedModifier();

	public JComboBox getCombo() {
		return fillCombo();
	}

	/**
	 * Change the active Modifier
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		this.app.getChangeQubjectModifierAction().actionPerformed(
				new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
	}
	
}
