package motionEstimation;

import imageObject.Point;
import imageTransform.TabImage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Gestionnaire de mouvement entre 2 images
 *
 */
public class MotionDetection {

	private TabImage cur;
	private TabImage ref;
	private BlockMatching param;
	private BufferedImage prediction;
	private int valeurType;
	
	public MotionDetection(BlockMatching param){
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
				// Recherche de mouvement de rotation
			}else{
				// Le block n'a pas bougé
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
	/*public void getAllMotion(int i, int brow, int bcol, int search, ArrayList<Block> list){
		// Parcours des blocks de l'image de suivante
		float min = Float.MAX_VALUE;
		float err =0;
		BufferedImage subImage;
		for (int j = list.get(i).getxCenter()()-search; j <list.get(i).getxCenter()()+search; j++){
			for (int k = list.get(i).getyCenter()()-search; k <list.get(i).getyCenter()()+search; k++){
				// Surveiller les bords de l'image
				if(k >= 0 && j >= 0 && k+brow < ref.getHeight() && j+bcol < ref.getWidth()){
					subImage = cur.getSubimage(j, k, bcol, brow);
					try {
						err = motion.Erreur.errQM(list.get(i).getImage(),subImage);
						if (err < min){
							min = err;
							list.get(i).setRbest(subImage, j-list.get(i).getxCenter()(), k-list.get(i).getyCenter()());

						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}*/
	
	/**
	 * cette fonction determine si un block est en mouvement, pour qu'elle marche convenablement,
	 * il faut faire des tests pour déterminer une valeur type
	 */
	public boolean isActive(Block block){
		boolean result = true;
		
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
		
		float min = Float.MAX_VALUE;
		float err =0;
		boolean result =false;
		int x = bl.getxCenter()-search;
		int y = bl.getyCenter()-search;
		int bestX = bl.getxCorner(), bestY = bl.getyCorner();
		while (result == false){
			for (int j = x; j <x+search; j=j+search){
				for (int k = y; k<y+search; k=k+search){
					// Surveiller les bords de l'image
					if(k >= 0 && j >= 0 && k+brow < ref.getHeight() && j+bcol < ref.getWidth()){
						try {
							err = Erreur.errQM(ref, cur, bl, new Block(j, k, cur.getWidth(), cur.getHeight(), bcol, brow));
							if (err < min){
								min = err;
								System.out.println("Erreur : " + err);
								x=j; //-lref.get(i).getX();
								y=k; //-lref.get(i).getY();

							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						if (search >= 2){
							search = (int)(search/2);// ici je veux la partie entiere donc à vérifier
						} else {result = true;}
					}
				}
			}
		}
		System.out.println("Move : " + bestX + " " + bestY);
		bl.move(x, y);
	}

	public void setNewImage(BufferedImage cur) {
		this.ref = this.cur;
		this.cur = new TabImage(cur);
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
