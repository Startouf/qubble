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

import main.ImageDetection;

/**
 * Prend en entrée une image binarisée
 * Enumération les différentes composantes connexes de l'image
 * @author masseran
 *
 */
public class ComponentsAnalyser {
	
	private ArrayList<ConnexeComponent> listCC;
	private HashMap<ConnexeComponent, Color> signature;
	private TabImage image;
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
	
	/**
	 * Analyse l'image pour récupérer les composantes connexes
	 * @param binaryImage
	 */
	public ComponentsAnalyser(TabImage binaryImage){
		signature = new HashMap<ConnexeComponent, Color>();
		height = binaryImage.getHeight();
		width = binaryImage.getWidth();
		
		
		int nbLabels = 1;		
		
		/**
		 * Création des différents ensembles connexes grâce à l'indice
		 */
		int[][] tab = binaryImage.getImg();
		Label(tab, 0x00ffffff, false);
		HashMap<Integer, ConnexeComponent> component = new HashMap<Integer, ConnexeComponent>();
		for (int i=0; i<width ; i++) {
			for (int j=0 ; j<height ; j++){
				if(Labels[i][j] != 0){
					if(!component.containsKey(Labels[i][j])){
						component.put(Labels[i][j], new ConnexeComponent());
					}
					component.get(Labels[i][j]).addPoint(new Point(i, j));
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
		BufferedImage CCMyImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Color compoColor = null;
		
		// Affichage d'un fond blanc
		Graphics g = CCMyImage.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		/*for (int y=0 ; y < height ; y++)
			for (int x=0 ; x < width ; x++){
				if(Labels[x][y]> 0)
					CCMyImage.setRGB(x, y, Color.black.getRGB());
			}*/
		
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
			
			// Enregistrement de sa signature pour un affichage ultérieur
			if(listPoint.isSquareTest() && ImageDetection.GUI)
				signature.put(listPoint, compoColor);
			
			// Affichage du centre et du point le plus loin
			if(listPoint.getConnexePoints().size()> 100){
				g.setColor(Color.green);
				g.fillRect(listPoint.getCorner().getX()-4, listPoint.getCorner().getY()-4, 8, 8);
				
				g.setColor(Color.green);
				g.fillRect(listPoint.getxCenter()-4, listPoint.getyCenter()-4, 8, 8);
				
			}
			
		}
		
		return CCMyImage;
	} 

	
	public ArrayList<ConnexeComponent> getCClist(){
		return listCC;
		
	}
	
	public HashMap<ConnexeComponent, Color> getSignatureList(){
		return signature;
	}
	
	
	////// Rechercher des composantes connexes
	
	/** Calcule le nombre de composantes connexes dans l'image.
	 * @param Original Le tableau dans lequel on doit compter les composantes connexes.
	 * @param Background Couleur du fond, donc a ne pas prendre en compte.
	 * @param EightConnex Booleen qui permet de savoir si le calcul se fait en quatre ou huit connexites.
	 * @param Chrono Le chronometre pour mesurer la duree.
	 * @return Le nombre de composantes connexes de l'image.*/
	public int Label(int[][] Original, int Background, boolean EightConnex)
		{
		int x, y, pos, root, marker = 0 ;
		/*width = Original[0].length ;
		height = Original.length ;*/
		size = width * height ;
	 
	 
		if ( roots == null || roots.length != size )
			{
			roots = null ;
			roots = new int[size] ;
			}
	 
		/*if ( Labels == null || Labels.length != height || Labels[0].length != width)
			{
			Labels = null ;
			Labels = new int[height][width] ; // copy label to new array
			}*/
		
		Labels = new int[width][height] ;
	 
		//ArraysOperations.SetConstant(roots, 0) ;
		//ArraysOperations.SetConstant(Labels, 0) ;
	 
		for (y=pos=0 ; y < height ; y++)
			for (x=0 ; x < width ; x++, pos++)
				if ( Original[x][y] == Background ) add(pos, BACKGROUND) ;
				else
					{
					root = NOROOT ;
	 
					if ( (x > 0) && (Original[x-1][y] == Original[x][y]) ) root = union(find(pos-1), root) ;
					if ( (y > 0) && (Original[x][y-1] == Original[x][y]) ) root = union(find(pos-width), root) ;
	 
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
				if ( roots[pos] == BACKGROUND ) Labels[x][y] = 0 ;
				else
					{
					if ( roots[pos] == pos ) roots[pos] = label++ ;
					else roots[pos] = roots[roots[pos]] ;
	 
					Labels[x][y] = roots[pos] ;
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
				if ( Labels[x][y] > 0 )
					Sizes[Labels[x][y]]++ ;
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

	
}
