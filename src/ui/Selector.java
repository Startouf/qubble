package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import qubject.MediaInterface;
import qubject.Qubject;
import qubject.QubjectModifierInterface;
import qubject.QubjectProperty;


/**
 * @author Cyril
 * A box that contains: 
 * ¤ A JLabel indicating the object (Qubject or one of its params)
 * ¤ A visual representation of the object
 * ¤ An arrow to show the Palette to select the object
 * 
 * NOTE : object = Qubject or Qubject modifier
 * Maybe this class should be split into 2 classes !
 * 
 * See https://docs.google.com/drawings/d/1NOopF11r3Y56fjHIWkAm6jW3N124hpSTVYJpzmWgv50/edit?usp=sharing
 * For a visual representation
 *
 */
public class Selector extends JPanel {
	
	private final App app;
	//change the specific object (pattern or patternmodifier)
	private JLabel label;

	/**
	 * Selector for a Qubject
	 * TODO Make it somewhat larger than the Modifiers ?
	 * (Or more distinctive)
	 * @param app
	 * @param qubject
	 */
	public Selector(App app, MediaInterface qubject){
		this.app = app;		
		setPreferredSize(new Dimension(300,40));
		setBorder(BorderFactory.createCompoundBorder(
				new EtchedBorder(), 
				new EmptyBorder(10, 20, 10, 20)));
		setLayout(new BorderLayout());
		
		add(label = new JLabel(qubject.getName()), BorderLayout.WEST);
		//TODO : show a thumbnail of the object
		add(new SelectorButton(app,qubject), BorderLayout.EAST);
	}

	/**
	 * Selector for a Param
	 * @param app
	 * @param modifier
	 */
	public Selector(App app, MediaInterface qubject, QubjectProperty modifier){
		this.app = app;		
		setPreferredSize(new Dimension(250,35));
		setBorder(BorderFactory.createCompoundBorder(
				new EtchedBorder(), 
				new EmptyBorder(5, 10, 5, 10)));
		setLayout(new BorderLayout());
		
		//TODO : name
		add(label = new JLabel(getNameFor(qubject, modifier)), BorderLayout.WEST);
		//TODO : show a thumbnail of the object
		add(new SelectorButton(app, modifier), BorderLayout.EAST);
	}

	
	private String getNameFor(MediaInterface q, QubjectProperty selectedParam){
		switch(selectedParam){
		case ANIM_WHEN_PLAYED:
			return q.getAnimationWhenPlayed().getName();
		case ROTATION:
			return q.getRotationEffect().getName();
		case SAMPLE_WHEN_PLAYED:
			return q.getSampleWhenPlayed().getName();
		case ANIM_WHEN_PUT_ON_TABLE:
			return q.getAnimationWhenDetected().getName();
		case Y_AXIS:
			return q.getYAxisEffect().getName();
		default:
			return ("Should Not exist");
		}
	}
	
	public void setQubject(MediaInterface qubject){
		label.setText(qubject.getName());
	}
	
	public void setModifier(QubjectModifierInterface modifier){
		label.setText(modifier.getName());
	}
}
