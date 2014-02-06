package ui;

import java.util.ArrayList;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JFrame;

import org.lwjgl.util.glu.Project;

import qubject.Animation;
import qubject.MediaInterface;
import qubject.QRInterface;
import qubject.Qubject;
import qubject.SampleInterface;
import qubject.SoundEffectInterface;

import actions.*;

/**
 * @author Cyril|duchon
 * @author Karl|bertoli
 * The main window that allows the user to choose his settings
 * 
 * Visual representation :
 * https://docs.google.com/drawings/d/1NOopF11r3Y56fjHIWkAm6jW3N124hpSTVYJpzmWgv50/edit?usp=sharing
 * 
 * Chaine reactive
 * XML -- draw.io
 * https://drive.google.com/file/d/0B8EhxTyDP6M-UDA5YlgtbWV6LTA/edit?usp=sharing
 * JPG
 * https://drive.google.com/file/d/0B8EhxTyDP6M-bW1zcC14QnFZVVE/edit?usp=sharing
 * 
 * Interfaces de programmation (Peut-être pas encore à jour)
 * XML -- draw.io
 * https://drive.google.com/file/d/0B8EhxTyDP6M-NWFfTndOQkFIZmM/edit?usp=sharing
 * JPG
 * https://drive.google.com/file/d/0B8EhxTyDP6M-WVQ3R2NUbjRLeGs/edit?usp=sharing
 *
 */
public class App extends JFrame
{
	/**
	 * Contient références vers les samples, soundEffects... 
	 */
	private final GlobalControllerInterface globalController = new GlobalController(this);
	/**
	 * Références vers les projets chargés (liste des Qubject configurés)
	 */
	private final ArrayList<ProjectController> projects = new ArrayList<ProjectController>();
	private ProjectController activeProject = null;
	
	
	/*
	 * Actions
	 */
	private final NewAction newAction = new NewAction(this);
	private final LoadAction loadAction = new LoadAction(this);
	private final OpenIndividualSettingsAction openIndividualSettingsAction 
		= new OpenIndividualSettingsAction(this);
	
	private boolean projectOpened = false;
	//TODO The palettes should be final. 
	private QubjectPalette qubjectPalette = null;
	private SamplePalette samplePalette = null;
	private final MenuBar menu;
	private final MainPanel mainPanel;

	public App()
	{
		super("Qubble");
		
		setJMenuBar(menu = new MenuBar(this));
		setContentPane(mainPanel = new MainPanel(this));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	/**
	 * Debug Overload
	 * @return
	 */
	public App(ProjectController project)
	{
		super("Qubble");
		this.activeProject = project;
		this.projects.add(project);
		setJMenuBar(menu = new MenuBar(this));
		setContentPane(mainPanel = new MainPanel(this));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	public MenuBar getMenu() {
		return menu;
	}

	public MainPanel getMainPanel() {
		return mainPanel;
	}

	/**
	 * Plus tard, il faudra cacher les options d'ouverture des paramètres
	 * 		tant qu'un projet n'aura pas été ouvert !
	 */
	public OpenIndividualSettingsAction getOpenIndividualSettingsAction() {
		
		return openIndividualSettingsAction;
	}

	public NewAction getNewAction() {
		return newAction;
	}

	public LoadAction getLoadAction() {
		return loadAction;
	}

	/**
	 * Ouverture des Palettes de sélection :
	 * Idéé : la palette existe toujours, mais est cachée/révélée quand on en a besoin
	 * le getter est parfois uniquement utilisé pour sa fonction setVisible
	 * 
	 * @return palette de sélection de Qubject
	 */
	public QubjectPalette getQubjectPalette() {
		if(qubjectPalette != null){
			qubjectPalette.setVisible(true);
			return qubjectPalette;
		}
		else{ 
			qubjectPalette = new QubjectPalette(this);
			return qubjectPalette;
		}
	}

	public SamplePalette getSamplePalette() {
		if(samplePalette != null){
			samplePalette.setVisible(true);
			return samplePalette;
		}
		else{
			samplePalette = new SamplePalette(this);
			return samplePalette;
		}
	}
	
	public ProjectController getActiveProject() {
		return activeProject;
	}

	/**
	 * Change the active project and adds it (if not already done) in the projectsList
	 * @param project
	 */
	public void setActiveProject(ProjectController project) {
		this.activeProject = project;
		if (!this.projects.contains(project))
			projects.add(project);
	}

	public GlobalControllerInterface getGlobalController() {
		return globalController;
	}

	public ArrayList<ProjectController> getProjets() {
		return projects;
	}

	public QubjectPalette getQubjectSelectionFrame() {
		return qubjectPalette;
	}

	public boolean isProjectOpened() {
		return projectOpened;
	}

	public void setProjectOpened(boolean projectOpened) {
		this.projectOpened = projectOpened;
	}

	public ArrayList<Qubject> getQubjects() {
		return activeProject.getQubjects();
	}

	public ArrayList<SoundEffectInterface> getSoundEffects() {
		return globalController.getSoundEffects();
	}

	public ArrayList<Animation> getAnimations() {
		return globalController.getAnimations();
	}

	public void setQubjectPalette(QubjectPalette patternSelectionFrame) {
		this.qubjectPalette = patternSelectionFrame;
	}
	
	/**
	 * TEMP
	 */
	public static void main(String[] args){
		App DJTable = new App();
	}

	public ViewQubjects getActiveTab() {
		// TODO Auto-generated method stub
		return null;
	}
}
