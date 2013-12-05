package debug;

import java.awt.*;
import java.util.Timer;

import ui.TableViewPanel;

public class ExampleTableAnimation
{
	private TableViewPanel panel;
	private Dimension tableDim;
	//échantillonage en temps sur 60 000 ms
	private float time;
	private float period = 60;
	
	public ExampleTableAnimation(TableViewPanel panel){
		this.panel = panel;
	}
	
	public void paint(Graphics g){
		this.tableDim = this.panel.getSize();
		paintFixedObjects(g);
		paintCursor(g);
	}
	
	private void paintFixedObjects(Graphics g){
		//Afficher les bords de la table épaiseur 5
		//Note : on est obligé de caster g en g2d car sinon g ne possède pas la méthode (changer l'épaisseur du trait)
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(5));
		g.drawRect(0, 0, this.tableDim.width-1, this.tableDim.height-1);
		g2d.setStroke(new BasicStroke(1));
		
		//Afficher un carré au 1/4 de la table au 3/4 en haut, de coté 15 pixels en rouge
		g.setColor(Color.RED);
		g.drawRect((int)Math.floor(this.tableDim.width/4), 
				(int)Math.floor(this.tableDim.height)*3/4, 15, 15);
		
		//afficher un cercle aux 3/4 de la table au 1/2 en haut, de rayon 15 pixels en bleu
		g.setColor(Color.BLUE);
		g.drawOval((int)Math.floor(this.tableDim.width*3/4), 
				(int)Math.floor(this.tableDim.height)/2, 15, 15);
		
		g.setColor(Color.BLACK);
		
	}
	
	private void paintCursor(Graphics g){
		this.time = panel.getTime();
		Graphics2D g2d = (Graphics2D) g;
		
		//Tracer le curseur 
		g2d.setStroke(new BasicStroke(5));
		g.setColor(Color.YELLOW);
		g2d.drawLine((int)(time/period*this.tableDim.width), 0, 
				(int)(time/period*this.tableDim.width), this.tableDim.height);
		g.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(1));
	}
}
