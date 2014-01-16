package ui;

import java.util.ArrayList;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JFrame;

import database.Animation;
import database.PatternInterface;

import actions.*;
import audio.SampleInterface;
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
	/*
	 * ArrayLists pour les paramètres des cubes stockées ici temporairement
	 * Devraient être déplacées ailleurs plus tard
	 */
	private final ArrayList<PatternInterface> patterns = new ArrayList<PatternInterface>();
	private final ArrayList<SampleInterface> samples = new ArrayList<SampleInterface>();
	private final ArrayList<SoundEffectInterface> soundEffects = new ArrayList<SoundEffectInterface>();
	private final ArrayList<Animation> animations = new ArrayList<Animation>();
	
	//Commandes pour ouvrir/ffermet le projet
	private final NewAction newAction = new NewAction(this);
	private final LoadAction loadAction = new LoadAction(this);
	//Ouverture du menu settings individuel dans un onglet
	private final OpenIndividualSettingsAction openIndividualSettingsAction 
		= new OpenIndividualSettingsAction(this);
	
	private boolean projectOpened = false;
	private PatternSelectionFrame patternSelectionFrame = null;
	private SampleSelectionFrame sampleSelectionFrame = null;
	private final MenuBar menu;
	private final MainPanel mainPanel;

	public App()
	{
		super("DJ-Table");

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
	 * Ouverture des frames de sélection :
	 * Idéé : la frame existe toujours, mais est cachée/révélée quand on en a besoin
	 * le getter est parfois uniquement utilisé pour sa fonction setVisible
	 * 
	 * @return Frame de sélection de paramètre
	 */
	public PatternSelectionFrame getPatternSelectionFrame() {
		if(patternSelectionFrame != null){
			patternSelectionFrame.setVisible(true);
			return patternSelectionFrame;
		}
		else{ 
			patternSelectionFrame = new PatternSelectionFrame(this);
			return patternSelectionFrame;
		}
	}

	public SampleSelectionFrame getSampleSelectionFrame() {
		if(sampleSelectionFrame != null){
			sampleSelectionFrame.setVisible(true);
			return sampleSelectionFrame;
		}
		else{
			sampleSelectionFrame = new SampleSelectionFrame(this);
			return sampleSelectionFrame;
		}
	}

	public boolean isProjectOpened() {
		return projectOpened;
	}

	public void setProjectOpened(boolean projectOpened) {
		this.projectOpened = projectOpened;
	}

	public ArrayList<PatternInterface> getPatterns() {
		return patterns;
	}

	public ArrayList<SoundEffectInterface> getSoundEffects() {
		return soundEffects;
	}

	public ArrayList<Animation> getAnimations() {
		return animations;
	}

	public void setPatternSelectionFrame(PatternSelectionFrame patternSelectionFrame) {
		this.patternSelectionFrame = patternSelectionFrame;
	}
	
}
