package imageTransform;

//import image.Channel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
/**
 * Hough Transform GUI
 * 
 * @author Xavier Philippeau
 *
 */
// Hough Transform (multilines)
// image size
// threshold for votes
// Constructor
// minimum distance between two peaks in the array
// ---------------------------------------------------------------------------------
//                                   SWING Callback 
// ---------------------------------------------------------------------------------
// called by "Load Image"
// new Hough Transformer
// grayscale conversion
// compute gradient (Sobel) + vote in Hough Space (if gradient>64)
// ||gradient||^2 > 64^2
// display Hough Space
// called by "Perform Hough Transform"
// search extrema in Hough Space
// Hough Space Image
// Copy of the input image.
// colors used
// display each winner (lines + peak)
// print theta/rho value
// cycle colors
// convert (rho,theta) to equation Y=a.X+b
// vertical case, b contains the "x" offset
// other case
// display a box in the Hough Space
// ---------------------------------------------------------------------------------
//                                   GRAPHICS 
// ---------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------
//                                Utility methods
// ---------------------------------------------------------------------------------
// AWT images
// SWING objects
// singleton
// Snake Runnable
//import image.Channel;

public class Hough {

	// image size
	private int width,height;
 
	// threshold for votes
	private int threshold = 0;
	private int radius = 0;
	
	private BufferedImage imgHS;
	
	// max Rho walue (= length of the diagonal)
	private double maxRho;

	// max Theta walue (= 180 degrees = PI)
	private double maxTheta = Math.PI;

	// size of the accumulators array
	private int maxIndexTheta,maxIndexRho;
	
	// accumulators array
	private int[][] acc;
	
	// 8 neighboors offsets
	private int[] dx8 = new int[] {-1, 0, 1, 1, 1, 0, -1, -1};
	private int[] dy8 = new int[] {-1,-1,-1, 0, 1, 1,  1,  0};

	
	// Constructor
	public Hough(int width,int height, int threshold) {
		this.width=width;
		this.height=height;
		this.threshold=threshold;

		// minimum distance between two peaks in the array
		this.radius=6;  
		
		// diagonal
		this.maxRho = Math.sqrt( width*width + height*height );
				
		// size of the accumulators array
		this.maxIndexTheta= 360; // precision : 180/360 = 0.5 degree
		this.maxIndexRho=(int)(1+this.maxRho); // precision : 1 pixel
		this.acc = new int[maxIndexRho][maxIndexTheta];
	}
	
	
	// ---------------------------------------------------------------------------------
	//                                   SWING Callback 
	// ---------------------------------------------------------------------------------

	// called by "Load Image"
	public BufferedImage doTH(BufferedImage img0) {
		long startTime = System.currentTimeMillis();
		// grayscale conversion
		int[][] gray = new int[width][height];
		for (int y=0;y<height;y++) {
			for (int x=0;x<width;x++) {
				int rgb = img0.getRGB(x, y);
				int r = (rgb >>16 ) & 0xFF;
				int g = (rgb >> 8 ) & 0xFF;
				int b = rgb & 0xFF;
				gray[x][y]=(299*r + 587*g + 114*b)/1000;
			}
		}
		long endTime = System.currentTimeMillis();
		
		System.out.println("Nvg hough : " + (endTime-startTime));
		
		// compute gradient (Sobel) + vote in Hough Space (if gradient>64)
		for (int y=1;y<height-1;y++) {
			for (int x=1;x<width-1;x++) {
				int gv = (gray[x+1][y-1]-gray[x-1][y-1])+2*(gray[x+1][y]-gray[x-1][y])+(gray[x+1][y+1]-gray[x-1][y+1]);
				int gh = (gray[x-1][y+1]-gray[x-1][y-1])+2*(gray[x][y+1]-gray[x][y-1])+(gray[x+1][y+1]-gray[x+1][y-1]);
				int g2 = (gv*gv + gh*gh)/(16);
				if (g2>4096) this.vote(x,y); // ||gradient||^2 > 64^2
			}
		}

	    System.out.println("\nResults:");

	    // search extrema in Hough Space
	    List<double[]> winners = this.getWinners(this.threshold, this.radius);

	    // Hough Space Image
	    imgHS = getHoughSpace();
	    
	    // Copy of the input image.
	    BufferedImage imgout =  new BufferedImage(img0.getWidth(),img0.getHeight(),BufferedImage.TYPE_INT_RGB);
	    imgout.createGraphics().drawImage(img0,0,0,null);

	    // colors used
	    int num=0;
	    Color[] colors = new Color[] {
	    		Color.BLUE, Color.RED, Color.GREEN, Color.CYAN, Color.MAGENTA,
	    		Color.YELLOW, Color.ORANGE, Color.PINK
	    };

	    // display each winner (lines + peak)
	    for (double[] winner:winners) {

	    	double rho   = winner[0];
	    	double theta = winner[1];
	    	
	    	// print theta/rho value
			System.out.println("winner: theta="+Math.toDegrees(theta)+"°, rho="+(int)rho);

		    // cycle colors
	    	Color color = colors[(num++) % colors.length];

	    	// convert (rho,theta) to equation Y=a.X+b
		    double[] c = this.rhotheta_to_ab(rho, theta);

	    	if (Double.isNaN(c[0])) {
	    		// vertical case, b contains the "x" offset
		    	Point A = new Point((int)c[1],0); 
		    	Point B = new Point((int)c[1],height); 
		    	drawLine(imgout, A, B, color);
		    } else { 
		    	// other case
		    	Point A = new Point(0,(int)c[1]); 
		    	Point B = new Point(width,(int)(c[0]*width+c[1])); 
		    	drawLine(imgout, A, B, color);
		    }
	    	
	    	
	    }
		return imgout;
	    
	}
	
	// ---------------------------------------------------------------------------------
	//                                   GRAPHICS 
	// ---------------------------------------------------------------------------------

	private void drawLine(BufferedImage image,Point A, Point B,Color c) {
		Graphics2D g2d = image.createGraphics();
		g2d.setColor(c);
		g2d.setStroke(new BasicStroke(2));
		g2d.drawLine(A.x, A.y, B.x, B.y);
	}
	
	BufferedImage getHoughSpace() {
		int maxIndexTheta = getMaxIndexTheta();
		int maxIndexRho = getMaxIndexRho();
		int[][] acc = getAccumulator();
		
		BufferedImage img =  new BufferedImage(maxIndexTheta,maxIndexRho,BufferedImage.TYPE_INT_RGB);
		
		int max=0;
		for(int r=0;r<maxIndexRho;r++)
			for(int t=0;t<maxIndexTheta;t++)
				if (acc[r][t]>max) max=acc[r][t];

		double scale=max/10;
		for(int r=0;r<maxIndexRho;r++)
			for(int t=0;t<maxIndexTheta;t++) {
				double v = Math.log(1.0+(double)acc[r][t]/scale)/Math.log(1.0+max/scale);
				Color color = Color.getHSBColor(0f, 0f, (float)v);
				img.setRGB(t, r, color.getRGB());
			}
		
		return img;
	}

	// ---------------------------------------------------------------------------------
	//                            HOUGH TRANSFORM ALGORITHM 
	// ---------------------------------------------------------------------------------
	
	// votes for pixel (x,y)
	public void vote(int x,int y) {
		// centered 
		x-=width/2; y-=height/2;
		
		for(int indexTheta=0; indexTheta<maxIndexTheta; indexTheta+=1) {
			double theta = IndexToTheta(indexTheta);
			double rho = x*Math.cos(theta) + y*Math.sin(theta);
			
			// (rho,theta) -> index
			int indexRho   = RhoToIndex(rho);
	
			if (indexTheta<maxIndexTheta && indexRho<maxIndexRho) 
				acc[indexRho][indexTheta]++;
		}
	}
	
	// compute list of local extrema in Hough Space
	public List<double[]> getWinners(int threshold, int radius) {
		
		// maximum in the array
		int highestvote=0;
		for(int r=0;r<maxIndexRho;r++)
			for(int t=0;t<maxIndexTheta;t++)
				if (acc[r][t]>highestvote) highestvote=acc[r][t];
				
		// minimum vote needed to be a local extrema 
		int minvote=(highestvote*threshold)/100;

		// parsing the accumulators
		List<int[]> coords = new ArrayList<int[]>();
		for(int r=0;r<maxIndexRho;r++) {
			for(int t=0;t<maxIndexTheta;t++) {
				
				// value of current accumulator
				if (acc[r][t]<minvote) continue;
				
				// maxima in the neighborhood of this accumulator
				int nmax=0;
				for(int k=0;k<dx8.length;k++) {
					int rk=r+dx8[k];
					int tk=t+dy8[k];
					
					if (rk<0) continue;
					if (rk>=maxIndexRho) continue;
					if (tk<0) tk+=maxIndexTheta;
					if (tk>=maxIndexTheta) tk-=maxIndexTheta;

					if (acc[rk][tk]>nmax) nmax=acc[rk][tk];
				}
				
				// the current accumulator is not the highest value -> ignore
				if (nmax>acc[r][t]) continue;

				// prevent extrema to be too close to each others
				// => compare this coord to the others 
				boolean ignore=false;
				Iterator<int[]> iter = coords.iterator();
				while(iter.hasNext()) {
					int[] coord = iter.next();
					int dist=distance(coord[0],coord[1],r,t);
					if (dist>(2*radius)) continue;

					// this extrema is too close from the current one.
					// We keep the extrema with the highest vote.
					
					if (acc[r][t]>=acc[coord[0]][coord[1]]) {
						iter.remove(); // remove the other
					} else {
						ignore=true; break; // remove me
					} 
				}

				// store extrema in the array
				if (!ignore) coords.add( new int[] {r,t} );
			}
		}
		
		// convert array index to real (rho,theta) values
		List<double[]> winners = new ArrayList<double[]>();
		for(int[] coord:coords) {
			int r=coord[0];
			int t=coord[1];
			
			// convert to real (rho,theta) value
			double rho   = IndexToRho(r);
			double theta = IndexToTheta(t);
			
			// store in the list
			winners.add( new double[] {rho,theta} );
		}
		
		return winners;
	}

	// minimum distance between 2 coords in the array (mobius) 
	private int distance(int r0,int t0, int r1, int t1) {
		/* distance between (r0,t0) and (r1,t1) */
		int dist = Math.max(Math.abs(r0-r1), Math.abs(t0-t1));

		if (t0<t1) {
			/* distance between (-r0,t0+PI) and (r1,t1) */
			t0 = t0+maxIndexTheta; // theta=theta+PI => tindex=tindex+maxindex
			r0 = maxIndexRho-r0-1; // rho=-rho => rindex=max-rindex
		} else {
			/* distance between (r0,t0) and (-r1,t1+PI) */
			t1 = t1+maxIndexTheta; // theta=theta+PI => tindex=tindex+maxindex
			r1 = maxIndexRho-r1-1; // rho=-rho => rindex=max-rindex
		}
		dist = Math.min (dist, Math.max(Math.abs(r0-r1), Math.abs(t0-t1)) );
		
		return dist;
	}

	// ---------------------------------------------------------------------------------
	//                                   CONVERTERS 
	// ---------------------------------------------------------------------------------

	// convert rho[-maxRho,maxRho] and theta[0,maxTheta] from/to array index [0,maxIndex]
	public int RhoToIndex(double rho) { 
		return (int) (0.5 + (rho/this.maxRho + 0.5) * this.maxIndexRho );
	}
	public double IndexToRho(int index) {
		return ((double)index/this.maxIndexRho - 0.5)*this.maxRho;
	}
	public int ThetaToIndex(double theta) {
		return (int) (0.5 + (theta/this.maxTheta) * maxIndexTheta );
	}
	public double IndexToTheta(int index) {
		return ((double)index/this.maxIndexTheta)*this.maxTheta;
	}

	// convert (rho,theta) to (a,b) such that Y=a.X+b
	public double[] rhotheta_to_ab(double rho, double theta) {
		// vertical case
		if(Math.abs(Math.sin(theta))<0.01) {
			double a = Double.NaN;
			double b = width/2+((theta<1.57)?rho:-rho);
			return new double[] {a,b};
		}
		
		double a = -Math.cos(theta)/Math.sin(theta);
		double b = rho/Math.sin(theta)+height/2-a*width/2;
		return new double[] {a,b};
	}

	// ---------------------------------------------------------------------------------
	//                                   GETTER/SETTER 
	// ---------------------------------------------------------------------------------

	public int getMaxIndexTheta() {
		return maxIndexTheta;
	}

	public int getMaxIndexRho() {
		return maxIndexRho;
	}

	public int[][] getAccumulator() {
		return acc;
	}	
}