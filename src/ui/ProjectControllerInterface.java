package ui;

import java.util.ArrayList;

import qubject.MediaInterface;
import qubject.Qubject;

public interface ProjectControllerInterface {

	public ArrayList<Qubject> getQubjects();
	
	public String getProjectName();
	public void setProjectName(String name);
}
