package ui;

import java.awt.Point;
import java.util.Hashtable;

import javax.swing.JPanel;

import qubject.MediaInterface;
import qubject.QubjectModifierInterface;
import qubject.QubjectProperty;

public class ViewQubble extends ViewQubjects {
	private final App app;
	private final JPanel qubblePanel = new JPanel();
	private final Hashtable<MediaInterface, Point> positions =
			new Hashtable<MediaInterface, Point>();

	public ViewQubble(App app, ProjectController controller){
		super(controller);
		this.app = app;
		
		for(MediaInterface qubject : controller.getQubjects()){
			positions.put(qubject, new Point(-1,-1));
		}
	}
	
	@Override
	public void setActiveQubject(MediaInterface selectedQubject) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setActiveProperty(QubjectProperty property) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setModifierOfActiveProperty(QubjectModifierInterface modifier) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setConfigForQubject(MediaInterface qubject,
			QubjectProperty prop, QubjectModifierInterface modifier) {
		// TODO Auto-generated method stub

	}

}
