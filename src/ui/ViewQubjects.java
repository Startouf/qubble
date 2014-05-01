package ui;

import javax.swing.JPanel;

import qubject.MediaInterface;
import qubject.QubjectModifierInterface;
import qubject.QubjectProperty;
/**
 * @author duchon
 * This class should be implemented by all panels that allow the user to 
 * display or alter Qubject Properties
 *
 */
public abstract class ViewQubjects extends JPanel {
	/* The changeQubject/QubjectModifier action will query these via getters */
	protected MediaInterface activeQubject;
	protected QubjectProperty activeProperty;
	protected QubjectModifierInterface activeModifier;
	
	/**
	 * Will be used to determine whether this JPanel should be updated or not when a Qubble is changed
	 */
	protected final ProjectController project;
	
	public ViewQubjects(ProjectController controller){
		this.project=controller;
	}

	/**
	 * <html><br /><b> If overriden, must write </b> this.activeQubject= qubject; <b>or override getter</b>
	 * <html><br /> <b>Should be overriden !</b>
	 * @param selectedQubject
	 */
	public void setActiveQubject(MediaInterface selectedQubject){
		this.activeQubject = selectedQubject;
	};
	
	/**
	 * Refresh the display (highlight active property ?)
	 * <html><br /><b> If overriden, must write </b> this.activeProperty = property; <b>or override getter</b>
	 * @param property
	 */
	public void setActiveProperty(QubjectProperty property){
		this.activeProperty = property;
	};
	
	/**
	 * Refresh the display (change JLabels, etc..)
	 * <html><br /><b> If overriden, must write </b> this.activeModifier = modifier; <b>or override getter</b>
	 * <html><br /> <b>Should be overriden !</b>
	 * @param modifier
	 */
	public void setModifierOfActiveProperty(QubjectModifierInterface modifier){
		this.activeModifier = modifier;
	};
	
	public abstract void setConfigForQubject(MediaInterface qubject, QubjectProperty prop, QubjectModifierInterface modifier);

	public MediaInterface getActiveQubject(){
		return activeQubject;
	}
	
	public QubjectProperty getActiveProperty(){
		return activeProperty;
	}
	
	public QubjectModifierInterface getActiveModifier(){
		return activeModifier;
	}
	
	public boolean isLinkedToProject(ProjectController controller){
		return (controller == project);
	}

	public ProjectController getProject() {
		return project;
	}
}
