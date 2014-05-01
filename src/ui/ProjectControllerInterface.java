package ui;

import java.util.ArrayList;

import qubject.MediaInterface;
import qubject.Qubject;

public interface ProjectControllerInterface {

	/**
	 * 
	 * @return the list of loaded qubjects
	 */
	public ArrayList<Qubject> getQubjects();

	/**
	 * 
	 * @return the name of the project shown on the GUI
	 */
	public String getProjectName();

	/**
	 * 
	 * @param name new name for the project
	 */
	public void setProjectName(String name);

	/**
	 * Save the current configuration to the selected folder
	 */
	public void save(String path);

	/**
	 * Ask to save on project close ?
	 */
	public void close();
	
	/**
	 * Resets the project (but if possible keep Qubject config)
	 */
	public void panic();
}
