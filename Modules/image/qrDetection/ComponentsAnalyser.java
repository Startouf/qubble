package qrDetection;

import imageObject.ConnexeComponent;
import imageObject.Point;
import imageTransform.TabImage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

/**
 * Prend en entrée une image binarisée
 * Enumération les différentes composantes connexes de l'image
 * @author masseran
 *
 */
public class ComponentsAnalyser {
	
	private int imageHeight, imageWidth;
	private ArrayList<ConnexeComponent> listCC;
	private TabImage image;
	
	/**
	 * Analyse l'image pour récupérer les composantes connexes
	 * @param binaryImage
	 */
	public ComponentsAnalyser(TabImage binaryImage){
		
		imageHeight = binaryImage.getHeight();
		imageWidth = binaryImage.getWidth();
		
		// Tableau de référence
		int[][] labels = new int[imageWidth][imageHeight];
		int[] corresp = new int[imageWidth*imageHeight];
		
		int nbLabels = 1;
		
		//premiere case i=0, j=0
		if (binaryImage.getRGB(0, 0) == new Color(0,0,0).getRGB()) {
			labels[0][0] = nbLabels;
			nbLabels ++;
		}
		
		//premiere colonne i=0
		for (int j=1 ; j < imageHeight ; j++) {
			if (binaryImage.getRGB(0, j) == new Color(0,0,0).getRGB()){
				if(labels[0][j-1] == 0) {
					labels[0][j] = 0;
					nbLabels ++;
				}else{
					labels[0][j] = labels[0][j-1];
				}
			}
		}
		
		//premiere ligne j=0
		for (int i=1 ; i < imageWidth ; i++) {
			if (binaryImage.getRGB(i, 0) == new Color(0,0,0).getRGB()){
				if(labels[i-1][0] == 0) {
					labels[i][0] = nbLabels;
					nbLabels ++;
				}else{
					labels[i][0] = labels [i-1][0];
				}
			}
		}

		/*ArrayList<Integer> corresp = new ArrayList<Integer>(nbLabels);
		for (int k=0 ; k<nbLabels ; k++) {
			corresp.add(k);
		}*/
		//Reste du tableau
		for (int j=1 ; j<imageHeight ; j++){
			for (int i=1; i<imageWidth ; i++) {
				if (binaryImage.getRGB(i, j) == new Color(0,0,0).getRGB()){
					if(labels[i][j-1] == 0 && labels[i-1][j] == 0) {
						labels[i][j] = nbLabels;
						nbLabels ++;
					}else if (labels[i][j-1] > 0 && labels[i-1][j] == 0){
						labels[i][j] = labels[i][j-1];
					}else if (labels[i][j-1] == 0 && labels[i-1][j] > 0){
						labels[i][j] = labels[i-1][j];
						
					}else if (labels[i][j-1] > 0 && labels[i-1][j] > 0){
						labels[i][j] = Math.min(labels[i-1][j], labels[i][j-1]);
						//garder en memoire que max doit etre associe � min :
						corresp[Math.max(labels[i-1][j], labels[i][j-1])] = Math.min(labels[i-1][j], labels[i][j-1]);
						
					}
				}	
			}
		}
		   
		
		//Trouver les correspondances finales, code de M Roux
		/*
		for (int objet = 0; objet < nbLabels; objet++) {
			int i = objet;
			int j;
			do {
				j = i;
				i = corresp[i]; 
			} while (i != j);
			
			corresp[objet] = i;
		}
		
		int objet = 1;
		for (int i = 0; i < nbLabels; i++) {
			int j;
			j = corresp[i]; 
			if ( j == i ){
				corresp[i] = corresp[j];
			}
			else {
				corresp[i] = objet++;
			}
		}
		
		//Appliquer les correspondances d�finitives � l'image
		
		for (int i=0 ; i<imageWidth ; i++) {
			for (int j=0 ; j<imageHeight ; j++) {
					labels[i][j] = corresp[labels[i][j]];
			}
		}*/

		
		// Parcours de l'image de haut en bas puis de bas en haut tant qu'il y a des fusions de composantes connexes
		// Je néglige les bords de l'image x = 0 ou y = 0
		boolean modif = true;
		int labelSave = 0;
		int x = 0;
		
		while(modif){
			modif = false;
			x++;
			for (int j=imageHeight-2 ; j >= 0 ; j--){
				for (int i=imageWidth-2; i >= 0 ; i--) {
					labelSave = labels[i][j];
					if (binaryImage.getRGB(i, j) == new Color(0,0,0).getRGB()){
						if (labels[i][j+1] != 0 && labels[i+1][j] == 0){
							labels[i][j] = Math.min(labels[i][j+1], labels[i][j]);
							
						}else if (labels[i][j+1] == 0 && labels[i+1][j] != 0){
							labels[i][j] = Math.min(labels[i+1][j], labels[i][j]);
							
						}else if (labels[i][j+1] != 0 && labels[i+1][j] != 0){
							labels[i][j] = Math.min(labels[i][j],Math.min(labels[i+1][j], labels[i][j+1]));
						}
					}
					if(labelSave != labels[i][j])
						modif = true;					
				}
			}
			
			
			if(modif){
				for (int j= 1 ; j < imageHeight ; j++){
					for (int i= 1; i < imageWidth ; i++) {
						labelSave = labels[i][j];
						if (binaryImage.getRGB(i, j) == new Color(0,0,0).getRGB()){
							if (labels[i][j-1] != 0 && labels[i-1][j] == 0){
								labels[i][j] = Math.min(labels[i][j-1], labels[i][j]);
								
							}else if (labels[i][j-1] == 0 && labels[i-1][j] != 0){
								labels[i][j] = Math.min(labels[i-1][j], labels[i][j]);
								
							}else if (labels[i][j-1] != 0 && labels[i-1][j] != 0){
								labels[i][j] = Math.min(labels[i][j],Math.min(labels[i-1][j], labels[i][j-1]));
							}
						}	
						if(labelSave != labels[i][j])
							modif = true;	
					}
				}
			}
				
		}
		// Affichage du nombre de parcours
		System.out.println("Nombre de parcours dans la recherche des composantes connexes : " + x);
		
		
		HashMap<Integer, ConnexeComponent> component = new HashMap<Integer, ConnexeComponent>();
		/**
		 * Création des différents ensembles connexes grâce à l'indice
		 */
		for (int i=0; i<imageWidth ; i++) {
			for (int j=0 ; j<imageHeight ; j++){
				if(labels[i][j] != 0){
					if(!component.containsKey(labels[i][j])){
						component.put(labels[i][j], new ConnexeComponent());
					}
					component.get(labels[i][j]).addPoint(new Point(i, j));
				}
				
			}
		}
		
		listCC = new ArrayList<ConnexeComponent>();
		listCC.addAll(component.values());
	
	}	

		
	
	
	/**
	 *  Créer une image avec chaque composante connexe d'une autre couleur
	 * @return
	 */
	public BufferedImage getCCMyImage() {
		BufferedImage CCMyImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
		Color compoColor = null;
		
		// Affichage d'un fond blanc
		Graphics g = CCMyImage.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, imageWidth, imageHeight);
		
		
		for (ConnexeComponent listPoint : listCC) {
			compoColor = new Color ((int) (Math.random()*255), ((int) Math.random()*255), (int) (Math.random()*255) );
			
			// Affichage des coins des composantes connexes
			/*for(int i = 0 ; i < 4 ; i++){
				g.fillRect(listPoint.getCorner(i).getX()-1, listPoint.getCorner(i).getY()-1, 2, 2);
			}*/
			
			// Affichages des composantes connexes par couleurs
			if(listPoint.getConnexePoints().size() > 100){
				for (Point pixel : listPoint.getConnexePoints()) {
						if(CCMyImage.getRGB(pixel.getX(), pixel.getY()) == Color.WHITE.getRGB())
							CCMyImage.setRGB(pixel.getX(), pixel.getY(), compoColor.getRGB());
				}
				
			}
			
			if(listPoint.getConnexePoints().size()> 100){
				g.setColor(Color.green);
				g.fillRect(listPoint.getCorner(0).getX()-4, listPoint.getCorner(0).getY()-4, 8, 8);
				
				g.setColor(Color.green);
				g.fillRect(listPoint.getxCenter()-4, listPoint.getyCenter()-4, 8, 8);
				
			}
		}
		
		return CCMyImage;
	} 

	
	public ArrayList<ConnexeComponent> getCClist(){
		return listCC;
		
	}
	
	
	
	
	/** Les dimensions de l'image ou du tableau traite.*/
	private int width, height, size = -1 ;
	/** Le tableau contenant la numerotation des composantes.*/
	private int[][] Labels = null ;
	 
	/** La derniere image sur laquelle on a compte le nombre de composantes.*/
	private BufferedImage source = null ;
	 
	/** Tableau contenant la taille des composantes connexes.*/
	private int[] Sizes = null ;
	 
	/** Nombre de composantes connexes denombrees lors du dernier appel de la methode Label.*/
	private int Counter = -1 ;
	 
	/** Tableau qui contient les valeurs des racines.*/
	private int[] roots = null ;
	/** Constante du fond.*/
	private final int BACKGROUND = -2 ;
	/** Constante de l'absence de racine.*/
	private final int NOROOT = -1 ;
	 
	 
	 
	 
	 
	 
	 
	 
	/** Calcule le nombre de composantes connexes dans l'image.
	 * @param Original L'image dans laquelle on doit compter les composantes connexes.
	 * @param Background Couleur du fond, donc a ne pas prendre en compte.
	 * @param EightConnex Booleen qui permet de savoir si le calcul se fait en quatre ou huit connexites.
	 * @param Chrono Le chronometre pour mesurer la duree.
	 * @return Le nombre de composantes connexes de l'image.*/
	public int Label(BufferedImage Original, int Background, boolean EightConnex)
		{
		int x, y, pos, root, marker = 0 ;
		WritableRaster wr = Original.getRaster() ;
		width = Original.getWidth() ;
		height = Original.getHeight() ;
		size = width * height ;
		source = Original ;
	 
	 
		//if ( ImageTools.isColored(Original) ) throw new IllegalArgumentException("Only gray level or binary image supported.") ;
	 
		if ( roots == null || roots.length != size )
			{
			roots = null ;
			roots = new int[size] ;
			}
	 
		if ( Labels == null || Labels.length != height || Labels[0].length != width)
			{
			Labels = null ;
			Labels = new int[height][width] ; // copy label to new array
			}
	 
		//ArraysOperations.SetConstant(roots, 0) ;
		//ArraysOperations.SetConstant(Labels, 0) ;
	 
		for (y=pos=0 ; y < height ; y++)
			for (x=0 ; x < width ; x++, pos++)
				if ( wr.getSample(x, y, 0) == Background ) add(pos, BACKGROUND) ;
				else
					{
					root = NOROOT ;
	 
					if ( (x > 0) && (wr.getSample(x-1, y, 0) == wr.getSample(x, y, 0)) ) root = union(find(pos-1), root) ;
					if ( (y > 0) && (wr.getSample(x, y-1, 0) == wr.getSample(x, y, 0)) ) root = union(find(pos-width), root) ;
	 
					if ( EightConnex )
						{
						if ( (x > 0 && y > 0) && (wr.getSample(x-1, y-1, 0) == wr.getSample(x, y, 0)) )
							root = union(find(pos-1-width), root) ;
						if ( (x < width-1 && y > 0) && (wr.getSample(x+1, y-1, 0) == wr.getSample(x, y, 0)) )
							root = union(find(pos+1-width), root) ;
						}
	 
					add(pos, root) ;
					}
	 
		buildLabelArray() ;
	 
	 
		Counter = Sizes.length-1 ; 
		return Counter ;
		}
	 
	 
	 
	/** Calcule le nombre de composantes connexes dans l'image.
	 * @param Original Le tableau dans lequel on doit compter les composantes connexes.
	 * @param Background Couleur du fond, donc a ne pas prendre en compte.
	 * @param EightConnex Booleen qui permet de savoir si le calcul se fait en quatre ou huit connexites.
	 * @param Chrono Le chronometre pour mesurer la duree.
	 * @return Le nombre de composantes connexes de l'image.*/
	public int Label(int[][] Original, int Background, boolean EightConnex)
		{
		int x, y, pos, root, marker = 0 ;
		width = Original[0].length ;
		height = Original.length ;
		size = width * height ;
	 
	 
		if ( roots == null || roots.length != size )
			{
			roots = null ;
			roots = new int[size] ;
			}
	 
		if ( Labels == null || Labels.length != height || Labels[0].length != width)
			{
			Labels = null ;
			Labels = new int[height][width] ; // copy label to new array
			}
	 
		//ArraysOperations.SetConstant(roots, 0) ;
		//ArraysOperations.SetConstant(Labels, 0) ;
	 
		for (y=pos=0 ; y < height ; y++)
			for (x=0 ; x < width ; x++, pos++)
				if ( Original[y][x] == Background ) add(pos, BACKGROUND) ;
				else
					{
					root = NOROOT ;
	 
					if ( (x > 0) && (Original[y][x-1] == Original[y][x]) ) root = union(find(pos-1), root) ;
					if ( (y > 0) && (Original[y-1][x] == Original[y][x]) ) root = union(find(pos-width), root) ;
	 
					if ( EightConnex )
						{
						if ( (x > 0 && y > 0) && (Original[y-1][x-1] == Original[y][x]) ) root = union(find(pos-1-width), root) ;
						if ( (x < width-1 && y > 0) && (Original[y-1][x+1] == Original[y][x]) ) root = union(find(pos+1-width), root) ;
						}
	 
					add(pos, root) ;
					}
	 
		buildLabelArray() ;
	 
	 
		Counter = Sizes.length-1 ; 
		return Counter ;
		}
	 
	 
	 
	 
	 
	 
	 
	 
	 
	/* ----------------------------------------------------- 3D ----------------------------------------------------- */
	/** Calcule le nombre de composantes connexes dans l'image.
	 * @param Original Le tableau dans lequel on doit compter les composantes connexes.
	 * @param Background Couleur du fond, donc a ne pas prendre en compte.
	 * @param TwentySixConnex Booleen qui permet de savoir si le calcul se fait en six ou vingt six connexites.
	 * @param Chrono Le chronometre pour mesurer la duree.
	 * @return Le nombre de composantes connexes de l'image.*/
	public int Label(int[][][] Original, int Background, boolean TwentySixConnex)
		{
		throw new Error("Method not yet implemented.") ;
		}
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	/* ----------------------------------------------------- Méthodes utiles ----------------------------------------------------- */
	/** Find the root of the node at position pos. 
	 * @param pos
	 * @return .*/
	private int find(int pos)
		{
		while ( roots[pos] != pos ) pos = roots[pos] ;
		return pos ;
		}
	 
	/** Union of the 2 path formed by the 2 roots.
	 * @param root0
	 * @param root1
	 * @return .*/
	private int union(int root0, int root1)
		{
		if ( root0 == root1 ) return root0 ;
		if ( root0 == NOROOT ) return root1 ;
		if ( root1 == NOROOT ) return root0 ;
		if ( root0 < root1 )
			{
			roots[root1] = root0 ;
			return root0 ;
			}
		else
			{
			roots[root0] = root1 ;
			return root1 ;
			}
		}
	 
	 
	/** Set the root of the node at position pos.  
	 * @param pos
	 * @param root */
	private void add(int pos, int root)
		{
		if ( root == NOROOT ) roots[pos] = pos ;
		else roots[pos] = root ;
		}
	 
	 
	/** Build the connected component labels array.*/
	private void buildLabelArray()
		{
		int x, y, pos, max ;
	 
		for (pos=0 ; pos < size ; pos++) // remove indirections
			if ( roots[pos] != BACKGROUND )
				roots[pos] = find(pos) ;
	 
		int label = 1 ; // relabel the root
		for (y=pos=0 ; y < height ; y++)
			for (x=0 ; x < width ; x++, pos++)
				if ( roots[pos] == BACKGROUND ) Labels[y][x] = 0 ;
				else
					{
					if ( roots[pos] == pos ) roots[pos] = label++ ;
					else roots[pos] = roots[roots[pos]] ;
	 
					Labels[y][x] = roots[pos] ;
					}
		max = 0;
		for(int i =0; i<width; i++){
			for(int j =0; j<height; j++){
				if(Labels[i][j]> max){
					max =Labels[i][j];
				}
			}
		}
		if ( Sizes == null || max+1 != Sizes.length )
			{
			Sizes = null ;
			Sizes = new int[max+1] ;
			}
	 
		for (y=0 ; y < height ; y++)
			for(x=0 ; x < width ; x++)
				if ( Labels[y][x] > 0 )
					Sizes[Labels[y][x]]++ ;
		}
	 
	 
	 
	 
	 
	 
	/** Calcule le nombre de composantes connexes dans l'image, mais ne compte que les composantes
	 *  ayant une surface superieure a Threshold.
	 * @param Threshold Surface minimum que doit avoir une composante connexe afin d'etre comptabilisee. 
	 * @return Le nombre de composantes connexes de l'image.*/
	public int NumberOfConnectedComponent(int Threshold)
		{
		if ( Labels == null ) throw new NullPointerException("Execution of Label method required before this one.") ;
	 
		int nbccf = 0 ;
		for (int i=1 ; i < Sizes.length ; i++)
			if ( Sizes[i] >= Threshold ) nbccf++ ;
	 
		return nbccf ;
		}
	 
	 
	/** Methode qui renvoit le nombre de composantes connexes denombrees lors du dernier appel de la methode Label.
	 * @return Le nombre de composantes.*/
	public int NumberOfConnectedComponent()
		{
		return Sizes.length-1 ;
		}
	 
	 
	 
	 
	 
	 
	 
	 
	 
	/* ----------------------------------------------------- Les getters ----------------------------------------------------- */
	/** Method which return sizes of connected components.
	 * @return Array.*/
	public int[] Sizes()
		{
		return Sizes ;
		}
	 
	 
	/** Method which return array of labels.
	 * @return Array.*/
	public int[][] Labels()
		{
		return Labels ;
		}
	 
	 
	/** Method which return array of labels.
	 * @return Array.*/
	public int[][][] Labels3D()
		{
		return null ;
		}
	 
	
	
}
