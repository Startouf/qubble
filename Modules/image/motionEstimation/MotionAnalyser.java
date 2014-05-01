package motionEstimation;

import imageObject.Point;
import imageTransform.TabImage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import main.ImageDetection;

/**
 * Gestionnaire de mouvement entre 2 images
 *
 */
public class MotionAnalyser {

	private TabImage cur;
	private TabImage ref;
	private BlockMatching param;
	private BufferedImage prediction;
	private int valeurType;
	
	public MotionAnalyser(BlockMatching param){
		this.param = param;
	}
	
	

	/**
	 * Affiche l'image de référence avec un quadrillage des différents blocks
	 * Affiche les vecteurs de mouvements
	 * @return
	 */
	public BufferedImage printMotion(ArrayList<Block> list){
		//prediction = new BufferedImage(cur.getWidth(), cur.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = prediction.getGraphics();
		//g.drawImage(ref, 0, 0, ref.getWidth(), ref.getHeight(), null);

		g.setColor(Color.YELLOW);

		// Affichage du quadrillage
		for(int i =1 ; i <= cur.getHeight()/param.getBlockSizeCol()+1 ; i++){
			g.drawLine(param.getBlockSizeCol()* i, 0, param.getBlockSizeCol()* i, cur.getHeight());
		}
		for(int i =1 ; i <= cur.getWidth()/param.getBlockSizeRow()+1 ; i++){
			g.drawLine(0, param.getBlockSizeRow()* i, cur.getWidth(), param.getBlockSizeRow()* i);
		}

		g.setColor(Color.RED);

		// Affichage des vecteurs
		for(Block bl : list){
			if(bl.getRbest() != null){
				g.setColor(Color.RED);
				g.drawLine(bl.getxCenter() + param.getBlockSizeCol()/2, bl.getyCenter() + param.getBlockSizeRow()/2, bl.getxCenter() + param.getBlockSizeCol()/2 + bl.getMotionX(), bl.getyCenter() + param.getBlockSizeRow()/2 + bl.getMotionY());
				g.setColor(Color.BLUE);
				g.fillOval(bl.getxCenter() + param.getBlockSizeCol()/2 + bl.getMotionX()-1, bl.getyCenter() + param.getBlockSizeRow()/2 + bl.getMotionY()-1, 2, 2);
			}else
				System.out.println("Pas de block correspondant trouvé ...");

		}	
		return prediction;
	}

	/**
	 * @param : La liste contient des points qui représentent le centre du block à analyser
	 * La fonction recherche les meilleurs déplacements pour une liste de quelques blocks de l'image.
	 * Les blocks sont déterminés par les paramètres d'un objet BlockMatching
	 */
	public void searchMotion(Collection<Block> list){
		
		// Parcours des points centraux
		for (Block bl : list){
			if(isActive(bl)){
				// Recherche du mouvement de translation
				getLogMotion(bl);
				//getAllMotion(bl);
				// Recherche de mouvement de rotation
			}else{
				// Le block n'a pas bougé
				if(ImageDetection.PRINTDEBUG)
					System.out.println("Le cube n'a pas bougé.");
			}
		}

	}

	/**
	 * Recherche parmis tous les blocks de l'image courante
	 * @param i
	 */
	/*
	 * ce que j'ai modifié : rajout d'un attribut list pour réaliser le getAllMotion sur la liste qubbles et pas uniquement sur lref, 
	 * là ou il y a list, remplacer par lref
	 */
	public void getAllMotion(Block bl){
		// Parcours des blocks de l'image de suivante
		int brow = param.getBlockSizeRow();
		int bcol = param.getBlockSizeCol();
		int search = param.getSearch();
		
		int min = Integer.MAX_VALUE;
		int err =0;
		boolean result =false;
		int x = bl.getxCenter();
		int y = bl.getyCenter();
		int bestX = bl.getxCorner(), bestY = bl.getyCorner();
		for (int j = x-search; j <=x+search; j++){
			for (int k = y-search; k<=y+search; k++){
					// Surveiller les bords de l'image
					if(k >= 0 && j >= 0 && k+brow < ref.getHeight() && j+bcol < ref.getWidth()){
						try {
							err = Erreur.errQM(cur, ref, new Block(j, k, cur.getWidth(), cur.getHeight(), bcol, brow), bl);
							if (err < min){
								min = err;
								bestX=j; //-lref.get(i).getX();
								bestY=k; //-lref.get(i).getY();
							}
							if(ImageDetection.PRINTDEBUG)
								System.out.println("Move : " + j + " " + k + "  Erreur : " + err);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
		// Valeur du block central !!!
		x=bestX; //-lref.get(i).getX();
		y=bestY;
		}
		if(ImageDetection.PRINTDEBUG)
			System.out.println("Move : " + x + " " + y);
		bl.move(x, y);
	}
	
	/**
	 * cette fonction determine si un block est en mouvement, pour qu'elle marche convenablement,
	 * il faut faire des tests pour déterminer une valeur type
	 */
	public boolean isActive(Block block){
		boolean result = true;
		valeurType = 600000;
		float test = Erreur.errQM(cur,ref, block, block);
		if(ImageDetection.PRINTDEBUG)
			System.out.println("Is active " + test);
		try {
			// Comparaison de l'erreur sur le même block dans les deux images
			if (Erreur.errQM(cur,ref, block, block) < valeurType){
				result = false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}



	/**
	 * Recherche uniquement les blocks selon la recherche de type log 
	 * @param i, brow, bcol, search
	 */
	public void getLogMotion(Block bl){
		// Parcours des blocks de l'image de suivante
		int brow = param.getBlockSizeRow();
		int bcol = param.getBlockSizeCol();
		int search = param.getSearch();
		
		int min = Integer.MAX_VALUE;
		int err =0;
		boolean result =false;
		int x = bl.getxCenter();
		int y = bl.getyCenter();
		int bestX = bl.getxCorner(), bestY = bl.getyCorner();
		while (result == false){
			for (int j = x-search; j <=x+search; j=j+search){
				for (int k = y-search; k<=y+search; k=k+search){
					// Surveiller les bords de l'image
					if(k >= 0 && j >= 0 && k+brow < ref.getHeight() && j+bcol < ref.getWidth()){
						try {
							err = Erreur.errQM(cur, ref, new Block(j, k, cur.getWidth(), cur.getHeight(), bcol, brow), bl);
							if (err < min){
								min = err;
								bestX=j; //-lref.get(i).getX();
								bestY=k; //-lref.get(i).getY();

							}
							System.out.println("Move : " + j + " " + k + "  Erreur : " + err);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			x=bestX; //-lref.get(i).getX();
			y=bestY;
			if (search >= 2){
				search = (int)(search/2);// ici je veux la partie entiere donc à vérifier
			} else {
				result = true;
			}
		}
		System.out.println("Move : " + x + " " + y);
		bl.move(x, y);
	}

	public void setNewImage(BufferedImage cur) {
		this.ref = this.cur;
		this.cur = (new TabImage(cur)).getGrey(false);
	}



	/*	public int[] getVector(Block a){
		int[] vector = new int[2];
		Block b = a.getRbest();
		vector[0]= (b.getX()-a.getX());
		vector[1]=(b.getY()-a.getY());
		return vector;
	}
	public float[] getSpeed(Block a, BlockMatching param){
		int[] vector = getVector(a);
		float[] speed = new float[2];
		speed[0] = 60*vector[0]/param.getFps();
		speed[1]= 60*vector[1]/param.getFps();
		return speed;

	}
	public float getNormalSPeed(Block a , BlockMatching param){
		float[] speed = getSpeed(a,param);
		return (float)Math.sqrt(speed[0]*speed[0]+speed[1]*speed[1]);
	}*/
	
	

}
