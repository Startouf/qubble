package camera;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import qubject.AnimationInterface;
import qubject.Qubject;
import main.ImageDetectionInterface;
import imageObject.Point;
import sequencer.QubbleInterface;
import ui.ModifierComboBoxRenderer;
import ui.QubjectComboBoxRenderer;

public class FakeCamera implements ImageDetectionInterface, Runnable
{
	private final QubbleInterface qubble;
	private volatile boolean start = false, kill = false;
	private ArrayList<Qubject> qubjectsToTweak;
	private final JTextField posX, posY;
	private final JComboBox qr;
	private final JButton validate, remove;
	
	public FakeCamera(final QubbleInterface qubble){
		this.qubble = qubble;
		JDialog controller = new JDialog();
		controller.setTitle("Fake Qubjects");
		JPanel content = new JPanel();
		qr = new JComboBox();
		posX= new JTextField("posX");
		posX.setPreferredSize(new Dimension(100, 20));
		posY = new JTextField("posY");
		posY.setPreferredSize(new Dimension(100, 20));
		content.add(qr); content.add(posX); content.add(posY);

		//QR selector
		qr.setRenderer(new QubjectComboBoxRenderer());
	  	for (Qubject qub : this.qubble.getAllQubjects())
    	{
    		qr.addItem(qub);
    	}
		
		//Action
		validate = new JButton(new AbstractAction(){
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//Try to parse
				int posXi = Integer.parseInt(posX.getText());
				int posYi = Integer.parseInt(posY.getText());
				Point p = new Point (posXi, posYi);
				
				int qub = ((Qubject)(qr.getSelectedItem())).getBitIdentifier();
				
				if(qubble.getQubjectsOnTable().contains(qub)){
					qubble.QubjectHasMoved(qub, p);
				} else{
					qubble.QubjectDetected(qub, p);
				}
				
			}
		});
		validate.setText("Bouger/Ajouter");
		validate.setHideActionText(false);
		
		remove = new JButton(new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int qub = ((Qubject)(qr.getSelectedItem())).getBitIdentifier();
				qubble.QubjectRemoved(qub);
			}
		});
		remove.setText("Bouger/Ajouter");
		remove.setHideActionText(false);
		
		content.add(validate); content.add(remove);
		content.setSize(200,400);
		
		controller.setContentPane(content);
		controller.pack();
		controller.setAlwaysOnTop(true);
		controller.setVisible(true);
	}

	@Override
	public synchronized void run() {
		while( start == false){
			try {
				wait(500); //in milliseconds
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		
		while(!kill){
			try {
				wait(100); //in milliseconds
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			qubble.QubjectDetected(0, new Point(800, 800));
			qubble.QubjectDetected(30, new Point(850, 550));
			qubble.QubjectDetected(511, new Point(450, 350));
			qubble.QubjectDetected(0, new Point(760, 550));
			qubble.QubjectDetected(511, new Point(950, 150));
			qubble.QubjectDetected(511, new Point(1100, 250));
			try {
				wait(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
	//		qubble.QubjectHasMoved(100100, new Point(150, 550));
	//		qubble.QubjectHasMoved(100101, new Point(350, 150));
	//		qubble.QubjectHasMoved(100110, new Point(550, 350));
	//		qubble.QubjectHasMoved(100111, new Point(760, 550));
	//		qubble.QubjectHasMoved(101000, new Point(950, 150));
			
			
		}
	}
	

	@Override
	public void setImage(BufferedImage newImage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BufferedImage getLastImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isNewImageQR() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isNewImageMotion() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void qrDetectionDone() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void motionEstimationDone() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setQrDetectionDone(boolean qrDetectionDone) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMotionEstimationDone(boolean motionEstimationDone) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HashMap<Integer, Point> getRemovedQubbleList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<Integer, Point> getAddedQubbleList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void resetRemovedQubbleList() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetAddedQubbleList() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getWidthCamera() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHeightCamera() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void terminate() {
		kill = true;
	}

	@Override
	public void switchCamera() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void switchDetection() {
		this.start  = true;
		
	}

	@Override
	public void switchMotion() {
		// TODO Auto-generated method stub
		
	}
	
}
