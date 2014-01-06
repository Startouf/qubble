package database;

import java.io.File;

/**
 * @author duchon
 * List of the patterns that will be used 
 * 
 * Note : Java enum provides .values() method which returns an array of Enum constants (will be useful in the UI
 * Note2 : i have put examples (square, circle), but it has no relation the actual shape of the pattern!
 * (We will most likely rename to Pattern1, Pattern2, ...)
 *
 */
public enum Pattern implements PatternInterface {
	SQUARE("Square", "square.bnp"), 
	CIRCLE("Cercle", "circle.bnp"), 
	RECTANGLE("Rectangle", "rectangle.bnp"), 
	STAR("Etoile", "star.bnp");
	
	private final String name;
	private final File visual;
	
	private Pattern(String name, String file){
		this.name = name;
		this.visual = new File("/data/pattern/" + file);
	}
	
	@Override
	public File getVisual(){
		return this.visual;
	}

	@Override
	public String getName() {
		return this.name;
	}
	
	
}
