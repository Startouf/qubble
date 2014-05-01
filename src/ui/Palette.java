package ui;

import java.awt.Component;
import java.awt.Point;

import javax.swing.JFrame;

public abstract class Palette extends JFrame {
	
    private static Component latestSpawnedComponent;
    
    private final App app;
    
    public Palette(App app){
    	this.app = app;
    	setPosition();
    }

	public Palette(App app, String paletteName) {
		super(paletteName);
		this.app = app;
		setPosition();
	}

	private void setPosition(){
		if ( latestSpawnedComponent == null){
    		this.setLocationRelativeTo(app);
    		this.setLocation(new Point(app.getWidth(), - app.getHeight()));
    	} else{
    		this.setLocationRelativeTo(latestSpawnedComponent);
    		this.setLocation(new Point(app.getWidth(), latestSpawnedComponent.getHeight()));
    	}
    	latestSpawnedComponent = this;
	}
}
