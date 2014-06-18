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
		remove.setText("Enlever");
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
		
		qubble.QubjectDetected(0, new Point(200, 200)); //cercle jaune
		qubble.QubjectDetected(30, new Point(850, 550)); //cercle orange
		qubble.QubjectDetected(511, new Point(450, 350)); //carré rose
		qubble.QubjectDetected(480, new Point(1100, 350)); //cercle bleu
		qubble.QubjectDetected(1, new Point(300, 350)); //cercle bleu
		qubble.QubjectDetected(190, new Point(100, 350)); //cercle bleu
		qubble.QubjectDetected(10, new Point(500, 350)); //cercle bleu
		qubble.QubjectDetected(31, new Point(600, 350)); //cercle bleu

		while(!kill){
			try {
				wait(400);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
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

	@Override
	public void closeGUI() {
		// TODO Auto-generated method stub
		
	}
	
}
