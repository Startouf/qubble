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
 *
 */
public class QubjectPalette extends JFrame implements ActionListener
{

	private App app;
	private final JPanel qubjectSelection = new JPanel();
	private MediaInterface selectedQubject;
    private ArrayList<JButton> listCube;
    private final ChangeQubjectAction changeQubjectAction =
    		new ChangeQubjectAction(app);
    
	public QubjectPalette(App app) {
		super("Selection de Qubject");
		this.app=app;
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		qubjectSelection.setLayout(new GridLayout());
        this.listQubjects();
        JButton validate = new JButton("Valider");
        validate.setAction(this.changeQubjectAction);
        
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
			listCube.add(cube);
		}
	}

	public MediaInterface getSelectedQubject() {
		return selectedQubject;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ReferenceButton.class){
			ReferenceButton Rbutton = (ReferenceButton) e.getSource();
			//Note should also check if the referencedbutton indeed contains a MediaInterface reference
			//but this should be the case
			this.selectedQubject = (MediaInterface) Rbutton.getReference();
		}
	}
}
