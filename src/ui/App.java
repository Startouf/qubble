package ui;

import java.util.ArrayList;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JFrame;

import org.lwjgl.util.glu.Project;

import database.Data;
import qubject.Animation;
import qubject.AnimationInterface;
import qubject.MediaInterface;
import qubject.QRInterface;
import qubject.Qubject;
import qubject.SampleInterface;
import actions.*;
import audio.SoundEffectInterface;

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
	private ProjectController activeProject;
	
	
	/*
	 * Actions
	 */
	private final NewProjectAction newAction = new NewProjectAction(this);
	private final LoadProjectAction loadAction = new LoadProjectAction(this);
	private final OpenIndividualSettingsAction openIndividualSettingsAction 
		= new OpenIndividualSettingsAction(this);
	private final OpenListSettingsAction openListSettingsAction 
		= new OpenListSettingsAction(this);
	private final ChangeQubjectAction changeQubjectAction 
		= new ChangeQubjectAction(this);
	private final ChangeQubjectModifierAction changeQubjectModifierAction 
		= new ChangeQubjectModifierAction(this);
	private final PlaySampleAction playSampleAction = new PlaySampleAction(this);
	private final PlayPauseAction playPauseAction = new PlayPauseAction(this);
	private final ToggleGridAction toggleGridAction = new ToggleGridAction(this);
	private final PanicAction panicAction = new PanicAction(this);
	
	private boolean projectOpened;
	//TODO The palettes should be final and initialised
	private QubjectPalette qubjectPalette = null;
	private SamplePalette samplePalette = null;
	private SoundEffectPalette soundEffectPalette = null;
	private AnimationPalette animationPalette = null;
	private final MenuBar menu;
	/**
	 * This one contains everything important !
	 */
	private final MainPanel mainPanel;

	/**
	 * Normal Overload that should be used in the prototype/final project
	 */
	public App()
	{
		super("Qubble");
		activeProject  = null;
		projectOpened  = false;
		
		//DEBUG :::
		Data.getAnimations();
		Data.getQubjects();
		Data.getSamples();
		Data.getSoundEffects();
		
		setJMenuBar(menu = new MenuBar(this));
		setContentPane(mainPanel = new MainPanel(this));
		
		showWelcomePanel();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	/**
	 * Debug Overload
	 * 
	 */
	public App(ProjectController project)
	{
		super("Qubble");
		projects.add(project);
		activeProject = project;
		projectOpened  = false;
		
		setJMenuBar(menu = new MenuBar(this, true));
		setContentPane(mainPanel = new MainPanel(this));
		
		showWelcomePanel();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	private void showWelcomePanel() {
		this.mainPanel.getSettingsTabs().addTab(
				"Accueil ", new WelcomePanel(this));
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
	
	public OpenListSettingsAction getOpenListSettingsAction() {
		return openListSettingsAction;
	}

	public NewProjectAction getNewAction() {
		return newAction;
	}

	public LoadProjectAction getLoadAction() {
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
	
	public AnimationPalette getAnimationPalette() {
		if(animationPalette != null){
			animationPalette.setVisible(true);
			return animationPalette;
		}
		else{
			animationPalette = new AnimationPalette(this);
			return animationPalette;
		}
	}
	
	public SoundEffectPalette getSoundEffectPalette() {
		if(soundEffectPalette != null){
			soundEffectPalette.setVisible(true);
			return soundEffectPalette;
		}
		else{
			soundEffectPalette = new SoundEffectPalette(this);
			return soundEffectPalette;
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
		mainPanel.getGlobalSettingsPanel().setActiveProjectName(project.getProjectName());
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
		this.menu.showProjectSettings(projectOpened);
	}

	public ArrayList<Qubject> getQubjects() {
		return activeProject.getQubjects();
	}

	public void setQubjectPalette(QubjectPalette patternSelectionFrame) {
		this.qubjectPalette = patternSelectionFrame;
	}
	
	public ViewQubjects getActiveTab() {
		//TODO : handle different type of tab !!!
		return (ViewQubjects) mainPanel.getSettingsTabs().getSelectedComponent();
	}
	
	public ChangeQubjectAction getChangeQubjectAction() {
		return changeQubjectAction;
	}

	public ChangeQubjectModifierAction getChangeQubjectModifierAction() {
		return changeQubjectModifierAction;
	}
	
	public PlaySampleAction getPlaySampleAction() {
		return playSampleAction;
	}

	public PlayPauseAction getPlayPauseAction() {
		return playPauseAction;
	}

	public ToggleGridAction getToggleGridAction() {
		return toggleGridAction;
	}

	public PanicAction getPanicAction() {
		return panicAction;
	}

	/**
	 * DEBUG --> Use debug.DebugLaunch
	 */
	public static void main(String[] args){
		App DJTable = new App();
	}
}
