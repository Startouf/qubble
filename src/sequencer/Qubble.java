package sequencer;

import java.util.ArrayList;

import database.Data;
import database.InitialiseProject;

import qubject.QRInterface;
import qubject.Qubject;

public class Qubble implements QubbleInterface {
	private final Data data;
	private final ArrayList<Qubject> configuredQubjects;
	private final ArrayList<Qubject> qubjectsOnTable;

	/**
	 * New project overload
	 * @param data reference to Data assets class
	 */
	public Qubble(Data data){
		super();
		this.data = data;
		configuredQubjects = InitialiseProject.loadQubjectsForNewProject();
		qubjectsOnTable = new ArrayList<Qubject> (configuredQubjects.size());
	}

	/**
	 * Open new project overload
	 * @param data Reference to Data assets class
	 * @param path The path of the saved project
	 */
	public Qubble(Data data, String path){
		super();
		this.data = data;
		configuredQubjects = InitialiseProject.loadQubjectsFromPath(path);
		qubjectsOnTable = new ArrayList<Qubject> (configuredQubjects.size());
	}

	@Override
	public ArrayList<Qubject> getAllQubjects() {
		return configuredQubjects;
	}

	@Override
	public ArrayList<Qubject> getQubjectsOnTable() {
		return qubjectsOnTable;
	}

	/**
	 * This implementation checks if the qubject is already on the list
	 */
	@Override
	public void newQubjectOnTable(QRInterface qubject) {
		//NOTE : if the program works well, this test shouldn't be needed
		if (!qubjectsOnTable.contains((Qubject) qubject)){
			qubjectsOnTable.add((Qubject)qubject);
		}
	}

	@Override
	public void qubjectRemovedFromTable(QRInterface qubject) {
		if (qubjectsOnTable.contains((Qubject) qubject)){
			qubjectsOnTable.remove((Qubject)qubject);
		}
	}
}
