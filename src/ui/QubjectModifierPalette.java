package ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import qubject.QubjectModifierInterface;
import actions.ChangeQubjectModifierAction;

public abstract class QubjectModifierPalette extends JFrame implements ActionListener{
	private App app;
	private final JPanel itemSelctionPanel = new JPanel();
	private QubjectModifierInterface selectedModifier;
    protected ArrayList<JButton> modifierList;
    
	public QubjectModifierPalette(App app) {
		super("Selection de Qubject");
		this.app=app;
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		itemSelctionPanel.setLayout(new GridLayout());
        this.listModifiers();
        /**
         * Le bouton valider déclenche l'action de cette classe qui change le modifier du Qubject Actif
         */
        JButton validate = new JButton("Valider");
        validate.setAction(this.app.getChangeQubjectModifierAction());
        
        setContentPane(itemSelctionPanel);
        pack();
        setVisible(true);
	}
    
	/**
	 * Add JButtons with the name of the possible modifiers
	 */
	protected abstract void listModifiers();

	public QubjectModifierInterface getSelectedModifier() {
		return selectedModifier;
	}

	/**
	 * Change the active Modifier
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ReferenceButton.class){
			ReferenceButton Rbutton = (ReferenceButton) e.getSource();
			this.selectedModifier = (QubjectModifierInterface) Rbutton.getReference();
		}
	}
}
