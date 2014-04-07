package ui;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import qubject.MediaInterface;
import qubject.Qubject;
import qubject.QubjectModifierInterface;
import qubject.QubjectProperty;

public class ViewListPanel extends ViewQubjects implements ActionListener {
	/**
	 * Look here for info on how to use the same comboBox but show different selectedItems
	 * http://stackoverflow.com/questions/13455767/shared-data-between-two-comboboxes
	 * 
	 * TODO : ajouter des ascenseurs (scrollbar)
	 */
	private final App app;
	private final JComponent[][] cell;
	private int activeCol = 1, activeRow = 3;
	private final int WIDTH, HEIGHT;
	private Hashtable<Integer, QubjectProperty> propertyMap;
	private Hashtable<Integer, Qubject> qubjectMap;
	
	/**
	 * Additional columns
	 * (normal columns = the Qubject Properties)
	 */
	private final String[] EXTRA_COLS = {"time", "qubject"};

	public ViewListPanel(App app) {
		super(app.getActiveProject());
		this.app = app;
		
		WIDTH = EXTRA_COLS.length+QubjectProperty.values().length;
		HEIGHT = app.getQubjects().size()+1;
		cell = new JComponent[HEIGHT][WIDTH];
		this.setLayout(new GridLayout(HEIGHT,WIDTH));
		
		prepare();
		addHeader();
		addRows();
		updateRows();
	}
	
	private void prepare(){
		qubjectMap = new Hashtable<Integer, Qubject>();
		propertyMap = new Hashtable<Integer, QubjectProperty>();
		
		int j = EXTRA_COLS.length;
		for(QubjectProperty prop : QubjectProperty.values()){
			propertyMap.put(j, prop);
		}
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
					System.err.println("missing case in viewlist");
					break;
				}
				((JComboBox) cell[i][j]).addActionListener(this);
				j++;
			}
		}
	}
	
	private void addHeader(){
		int j=0;
		for(String col : EXTRA_COLS){
			this.add(cell[0][j] = new JLabel(col));
			j++;
		}
		
		for(QubjectProperty prop : QubjectProperty.values()){
			this.add(cell[0][j] = new JLabel(prop.getUserFriendlyString()));
			j++;
		}
	}
	
	private void updateRows(){
		//TODO : sort Qubject List
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

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Dynamically change qubject props !
		//Find the i,j of the comboBox
		for(int i=0; i<HEIGHT; i++){
			for(int j=0; j<WIDTH; j++){
				if (arg0.getSource() == cell[i][j]){
					this.activeQubject = qubjectMap.get(i);
					this.activeProperty = propertyMap.get(j);
					JComboBox combo = (JComboBox) (cell[i][j]);
					switch (activeProperty){
					case ANIM_WHEN_DETECTED:
						this.app.getAnimationPalette().getCombo();
						break;
					case ANIM_WHEN_PLAYED:
						this.app.getAnimationPalette().getCombo();
						break;
					case AUDIO_EFFECT_ROTATION:
						this.app.getSoundEffectPalette().getCombo();
						break;
					case AUDIO_EFFECT_Y_AXIS:
						this.app.getSoundEffectPalette().getCombo();
						break;
					case SAMPLE_WHEN_PLAYED:
						this.app.getSamplePalette().getCombo();
						break;
					default:
						System.err.println("missing case in viewlist");
						break;
					}
				}
			}
		}
	}

	@Override
	public void setConfigForQubject(MediaInterface qubject, QubjectProperty prop,
			QubjectModifierInterface modifier) {
		// TODO Auto-generated method stub
		
	}
}
