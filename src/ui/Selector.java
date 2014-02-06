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

import qubject.Qubject;
import qubject.QubjectModifierInterface;


/**
 * @author Cyril
 * The box that contains a 
 * A JLabel indicating the object
 * Visual representation of the object
 * An arrow to select the desired object from a custom file chooser
 * 
 * NOTE : object = pattern or pattern modifier
 * 
 * See https://docs.google.com/drawings/d/1NOopF11r3Y56fjHIWkAm6jW3N124hpSTVYJpzmWgv50/edit?usp=sharing
 * For a visual representation
 *
 */
public class Selector extends JPanel {
	
	private final App app;
	//change the specific object (pattern or patternmodifier)
	private Object selectedObject;
	
	public Selector(App app, String title, Qubject pattern){
		this.app = app;
		this.selectedObject = getDefaultChoice(pattern);
		
		setPreferredSize(new Dimension(250,35));
		setBorder(BorderFactory.createCompoundBorder(
				new EtchedBorder(), 
				new EmptyBorder(10, 20, 10, 20)));
		setLayout(new BorderLayout());
		
		add(new JLabel(title), BorderLayout.WEST);
		//TODO : show a thumbnail of the object
		//TODO : show a clickable arrow that pops-up a custom file chooser menu with BorderLayer.EAST
		add(new SelectorButton(app,pattern), BorderLayout.EAST);

	}
	
	public Selector(App app, Qubject pattern, QubjectModifierInterface modifier){
		this.app = app;
		this.selectedObject = getDefaultChoice(modifier);
		
		setPreferredSize(new Dimension(250,35));
		setBorder(BorderFactory.createCompoundBorder(
				new EtchedBorder(), 
				new EmptyBorder(5, 10, 5, 10)));
		setLayout(new BorderLayout());
		
		add(new JLabel(getNameFor(modifier)), BorderLayout.WEST);
		//TODO : show a thumbnail of the object
		//TODO : show a clickable arrow that pops-up a custom file chooser menu with BorderLayer.EAST
		add(new SelectorButton(pattern, modifier), BorderLayout.EAST);
	}

	//Note : maybe a superclass of Pattern and PatternModifier should be created
	private Object getDefaultChoice(Object object){
		//TODO 
		return null;
	}
	
	private String getNameFor(Object selectedObjec){
		//TODO
		return "[Default choice]";
	}
}
