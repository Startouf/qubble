package database;

import java.io.File;
import java.io.FilenameFilter;

public class InitialiseTools
{
	private static final String saveFormat = ".properties" ;

	/**
	 * Returns the files of the current directory that match the saveFormat defined in this class
	 * @param directory
	 * @return
	 */
	public static File[] getFiles(String directory){
		File dir = new File(directory);
		File [] files = dir.listFiles(
				new FilenameFilter() 
				{
					@Override
					public boolean accept(File dir, String name) {
						return name.endsWith(saveFormat);
					}
				});
		return files;
	}

}
