package ui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
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
    
	public QubjectPalette(App app) {
		super("Selection de Qubject");
		this.app=app;
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		qubjectList = new ArrayList<ReferenceButton>();
		qubjectSelection.setLayout(new GridLayout());
        this.listQubjects();
        /**
         * Le bouton valider déclenche l'action de cette classe qui change la référence du qubject actif
         * TODO Le Qubject actif sera mis en surbrillance
         */
        JButton validate = new JButton("Valider");
        validate.setAction(this.app.getChangeQubjectAction());
        
        setContentPane(qubjectSelection);
        pack();
        setVisible(true);
	}
    
	private void listQubjects()
	{
		for (QRInterface qubject : this.app.getQubjects())
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
