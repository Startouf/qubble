package ui;

import java.awt.Component;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import qubject.MediaInterface;
import qubject.Qubject;
import qubject.QubjectModifierInterface;
import qubject.QubjectProperty;

public class ViewListPanel extends ViewQubjects {
	
	private final App app;
	private final JComponent[][] cell;
	private int activeCol = 1, activeRow = 3;
	
	/**
	 * Additional columns
	 * (normal columns = the Qubject Properties)
	 */
	private final String[] EXTRA_COLS = {"time", "qubject"};

	public ViewListPanel(App app) {
		super(app.getActiveProject());
		this.app = app;
		cell = new JComponent[app.getQubjects().size()+1][EXTRA_COLS.length+QubjectProperty.values().length];
		this.setLayout(new GridLayout(app.getQubjects().size()+1,2+QubjectProperty.values().length));
		
		addHeader();
		addRows();
		updateRows();
	}
	
	private void addRows(){
		int i = 0;
		for(i=1; i<app.getQubjects().size()+1; i++){
			//Time (position of Qubject on X axis)
			this.add(cell[i][0] = new JLabel("rr"));
			//Logo of the qubject -> Put into a JLabel
			this.add(cell[i][1] = new JLabel("rrr"));
			
			int j=2;
			
			for(QubjectProperty prop : QubjectProperty.values()){
				switch (prop){
				case ANIM_WHEN_DETECTED:
					this.add(cell[i][j] = this.app.getAnimationPalette().getCombo());
					break;
				case ANIM_WHEN_PLAYED:
					this.add(cell[i][j] = this.app.getAnimationPalette().getCombo());
					break;
				case AUDIO_EFFECT_ROTATION:
					this.add(cell[i][j] = this.app.getSoundEffectPalette().getCombo());
					break;
				case AUDIO_EFFECT_Y_AXIS:
					this.add(cell[i][j] = this.app.getSoundEffectPalette().getCombo());
					break;
				case SAMPLE_WHEN_PLAYED:
					this.add(cell[i][j] = this.app.getSamplePalette().getCombo());
					break;
				default:
					System.err.println("mising case in viewlist");
					break;
				}
				j++;
			}
		}
	}
	
	private void addHeader(){
		int j=0;
		for(String col : EXTRA_COLS){
			cell[0][j] = new JLabel(col);
			j++;
		}
		
		for(QubjectProperty prop : QubjectProperty.values()){
			cell[0][j] = new JLabel(prop.getUserFriendlyString());
			j++;
		}
	}
	
	public void updateRows(){
		//TODO synchronize ??
//		int i=1, j=0;	//1st row is HEADER
//		for(Qubject qubject:this.app.getQubjects()){
//			labels[i][j].setText(Double.toString(this.app.getActiveProject().getQubble().getPosition(qubject).getWidth()));
//			j++;
//			labels[i][j].setText(qubject.getName());
//			j++;
//			
//			for(QubjectProperty prop : QubjectProperty.values()){
//				labels[i][j].setText(qubject.getModifierForProperty(prop).getName());
//				j++;
//			}
//			i++;
//			j=0;
//		}
	}

	@Override
	public void setActiveQubject(MediaInterface selectedQubject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setActiveProperty(QubjectProperty modifier) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setModifierOfActiveProperty(QubjectModifierInterface modifier) {
		// TODO Auto-generated method stub
		
	}

}
