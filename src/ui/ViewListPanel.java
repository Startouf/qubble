package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import other.BidirectionalMap;
import qubject.MediaInterface;
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
		this.setOpaque(false);
		
		WIDTH = EXTRA_COLS.length+QubjectProperty.values().length;
		HEIGHT = app.getQubjects().size()+1;
		cell = new JComponent[HEIGHT][WIDTH];
		
		content = new ScrollablePanel(this, new Dimension(600, 100), 800, 2000);
		content.setOpaque(false);
		content.setLayout(new GridLayout(HEIGHT,WIDTH));

		prepare();
		addHeader();
		addRows();
		updateRows();
		
		JScrollPane scroll = new JScrollPane();
		scroll.setOpaque(false);
		
		JViewport view = new JViewport();
		view.setView(content);
		view.setOpaque(false);
		
		scroll.setViewport(view);
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
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if(MainPanel.backgroundImage != null){
			BufferedImage bf = MainPanel.backgroundImage;
			BufferedImage dest = MainPanel.backgroundImage.getSubimage(0, 0, 
					bf.getWidth(), bf.getHeight());
			g.drawImage(dest, 0, 0, getWidth(), getHeight(), this);
		}
	}
	
	private void addRows(){
		int i = 0;
		
		for(i=1; i<app.getQubjects().size()+1; i++){
			//Time (position of Qubject on X axis)
			content.add(cell[i][0] = new ShadowedJLabel("Error not initialized", Color.white,
					new Color(0,0,0,85)));
			//Logo of the qubject -> Put into a JLabel
			content.add(cell[i][1] = new ShadowedJLabel("Error not initialized", Color.white,
					new Color(0,0,0,85)));
			
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
			JLabel text = new JLabel();
			text.setForeground(Color.white);
			text.setText(col);
			content.add(cell[0][j] = new JPanel());
			cell[0][j].setBackground( new Color(0, 0, 0, 85) );
			cell[0][j].add(text);
			cell[0][j].setForeground(Color.white);
			j++;
		}
		
		for(QubjectProperty prop : QubjectProperty.values()){
			JLabel text = new JLabel();
			text.setForeground(Color.white);
			text.setText(prop.getUserFriendlyString());
			content.add(cell[0][j] = new JPanel());
			cell[0][j].setBackground( new Color(0, 0, 0, 85) );
			cell[0][j].add(text);
			cell[0][j].setForeground(Color.white);
			j++;
		}
	}
	
	private void updateRows(){
		//TODO (Check): Assumes the list of Qubjects is sorted !!
		int i=1, j=0;	//1st row is HEADER
		for(MediaInterface qubject:this.app.getQubjects()){
			((ShadowedJLabel) cell[i][j]).setText(Double.toString(this.app.getActiveProject().getQubble().getPosition(qubject).getWidth()));
			j++;
			((ShadowedJLabel) cell[i][j]).setText(qubject.getName());
			((ShadowedJLabel) cell[i][j]).setIcon(new ImageIcon(
					qubject.getImage().getScaledInstance( 20, 20,  java.awt.Image.SCALE_SMOOTH )));
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
							this.app.getChangeQubjectModifierAction().actionPerformed(
									new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
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
