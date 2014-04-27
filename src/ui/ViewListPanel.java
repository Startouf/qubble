package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.util.Collections;
import java.util.Hashtable;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.Scrollable;

import other.BidirectionalMap;

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
	private BidirectionalMap<Integer, QubjectProperty> propertyMap;
	private BidirectionalMap<Integer, MediaInterface> qubjectMap;
	private JPanel content;
	
	/**
	 * Additional columns
	 * (normal columns = the Qubject Properties)
	 */
	private final String[] EXTRA_COLS = {"Temps", "Qubject"};

	public ViewListPanel(App app) {
		super(app.getActiveProject());
		this.app = app;
		this.setLayout(new BorderLayout(10,10));
		
		WIDTH = EXTRA_COLS.length+QubjectProperty.values().length;
		HEIGHT = app.getQubjects().size()+1;
		cell = new JComponent[HEIGHT][WIDTH];
		
		content = new ScrollablePanel(this, new Dimension(600, 100), 800, 400);
		content.setLayout(new GridLayout(HEIGHT,WIDTH));

		prepare();
		addHeader();
		addRows();
		updateRows();
		
		JScrollPane scroll = new JScrollPane();

		scroll.setViewportView(content);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setViewportBorder(
	                BorderFactory.createLineBorder(Color.black));
		this.add(scroll);
		setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		this.revalidate();
	}
	
	private void prepare(){
		qubjectMap = new BidirectionalMap<Integer, MediaInterface>();
		propertyMap = new BidirectionalMap<Integer, QubjectProperty>();
		
		int j = EXTRA_COLS.length;
		for(QubjectProperty prop : QubjectProperty.values()){
			propertyMap.put(j, prop);
			j++;
		}
	}
	
	private void addRows(){
		int i = 0;
		
		for(i=1; i<app.getQubjects().size()+1; i++){
			//Time (position of Qubject on X axis)
			content.add(cell[i][0] = new JLabel("Error not initialized"));
			//Logo of the qubject -> Put into a JLabel
			content.add(cell[i][1] = new JLabel("Error not initialized"));
			
			int j=2;
			
			for(QubjectProperty prop : QubjectProperty.values()){
				switch (prop){
				case ANIM_WHEN_DETECTED:
					content.add(cell[i][j] = this.app.getAnimationPalette().getCombo());
					break;
				case ANIM_WHEN_PLAYED:
					content.add(cell[i][j] = this.app.getAnimationPalette().getCombo());
					break;
				case AUDIO_EFFECT_ROTATION:
					content.add(cell[i][j] = this.app.getSoundEffectPalette().getCombo());
					break;
				case AUDIO_EFFECT_Y_AXIS:
					content.add(cell[i][j] = this.app.getSoundEffectPalette().getCombo());
					break;
				case SAMPLE_WHEN_PLAYED:
					content.add(cell[i][j] = this.app.getSamplePalette().getCombo());
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
			content.add(cell[0][j] = new JLabel(col));
			j++;
		}
		
		for(QubjectProperty prop : QubjectProperty.values()){
			content.add(cell[0][j] = new JLabel(prop.getUserFriendlyString()));
			j++;
		}
	}
	
	private void updateRows(){
		//TODO (Check): Assumes the list of Qubjects is sorted !!
		int i=1, j=0;	//1st row is HEADER
		for(MediaInterface qubject:this.app.getQubjects()){
			((JLabel) cell[i][j]).setText(Double.toString(this.app.getActiveProject().getQubble().getPosition(qubject).getWidth()));
			j++;
			((JLabel) cell[i][j]).setText(qubject.getName());
			j++;
			qubjectMap.put(i, qubject);
			
			for(QubjectProperty prop : QubjectProperty.values()){
				((JComboBox)cell[i][j]).setSelectedItem(qubject.getModifierForProperty(prop));
				j++;
			}
			i++;
			j=0;
		}
	}
	
	@Override
	public void setActiveQubject(MediaInterface selectedQubject) {

	}

	@Override
	public void setActiveProperty(QubjectProperty modifier) {

	}

	@Override
	public void setModifierOfActiveProperty(QubjectModifierInterface modifier) {
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() instanceof JComboBox){
			for(int i=0; i<HEIGHT; i++){
				for(int j=0; j<WIDTH; j++){
					if (arg0.getSource() == cell[i][j]){
						if(j>=EXTRA_COLS.length){
							JComboBox combo = (JComboBox) (cell[i][j]);
							this.activeQubject = qubjectMap.get(i);
							this.activeProperty = propertyMap.get(j);
							this.activeModifier = (QubjectModifierInterface) combo.getSelectedItem();
							this.app.getChangeQubjectModifierAction().actionPerformed(new ActionEvent(this, arg0.getID(), null));
						}
					}
				}
			}
		}
	}

	@Override
	public void setConfigForQubject(MediaInterface qubject, QubjectProperty prop,
			QubjectModifierInterface modifier) {
			((JComboBox) cell[qubjectMap.getKey(qubject)][propertyMap.getKey(prop)]).setSelectedItem(modifier);
	}

	  
}
