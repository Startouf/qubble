package ui;

import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import qubject.MediaInterface;
import qubject.Qubject;
import qubject.QubjectModifierInterface;
import qubject.QubjectProperty;

public class ViewListPanel extends ViewQubjects {
	
	private final App app;
	private final JLabel[][] labels;
	private int activeCol = 1, activeRow = 3;
	
	/**
	 * Additional columns
	 * (normal columns = the Qubject Properties)
	 */
	private final String[] EXTRA_COLS = {"time", "qubject"};

	public ViewListPanel(App app) {
		this.app = app;
		labels = new JLabel[app.getQubjects().size()+1][EXTRA_COLS.length+QubjectProperty.values().length];
		this.setLayout(new GridLayout(2+QubjectProperty.values().length,app.getQubjects().size()+1));
		
		addRows();
		addHeader();
		updateRows();
	}
	
	private void addRows(){
		for(int i=0; i<app.getQubjects().size()+1; i++){
			for(int j=0; j<EXTRA_COLS.length+QubjectProperty.values().length; j++){
				this.add(labels[i][j] = new JLabel());
				labels[i][j].setHorizontalAlignment(JLabel.CENTER);
			}
		}
	}
	
	private void addHeader(){
		int j=0;
		for(String col : EXTRA_COLS){
			labels[0][j].setText(col);
			j++;
		}
		
		for(QubjectProperty prop : QubjectProperty.values()){
			labels[0][j].setText(prop.getUserFriendlyString());
			j++;
		}
	}
	
	public void updateRows(){
		//TODO synchronize ??
		int i=1, j=0;	//1st row is HEADER
		for(Qubject qubject:this.app.getQubjects()){
			labels[i][j].setText(Double.toString(this.app.getActiveProject().getQubble().getPosition(qubject).getWidth()));
			j++;
			labels[i][j].setText(qubject.getName());
			j++;
			
			for(QubjectProperty prop : QubjectProperty.values()){
				labels[i][j].setText(qubject.getModifierForProperty(prop).getName());
				j++;
			}
			i++;
			j=0;
		}
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
