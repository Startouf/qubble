package database;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Collection;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.apache.commons.io.FilenameUtils;

import qubject.QubjectModifierInterface;

public class InitialiseTools
{
	private static final String saveFormat = ".properties" ;

	/**
	 * Returns the files of the current directory that match the saveFormat defined in this class
	 * @param directory
	 * @return list of files in the directory filtered by filename (current : *.properties)
	 */
	public static File[] getDotProperties(String directory){
		File dir = new File(directory);
		//test
		boolean ok = dir.isDirectory();
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

	public static File compileAnimation(File dotJavaFile){
		//Some Copy Paste. Check
		//TODO
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		//TODO : check
		compiler.run(null, null, null, dotJavaFile.getAbsoluteFile().toString());
		return null;
	}
	
	public static String getDotClassFromDotJava(String dotJavaFile){
		return (FilenameUtils.removeExtension(dotJavaFile) + ".class");
	}
	
	public static File getDotClassFromDotJava(File file){
		return new File(getDotClassFromDotJava(file.getAbsolutePath()));
	}
	
	public static String getBinaryClassNameFromDotClass(File baseDir, File dotClassFile){
		String relative = baseDir.toURI().relativize(dotClassFile.toURI()).getPath();
		relative = FilenameUtils.removeExtension(relative);
		relative = relative.replace("/", ".");
		return relative;
	}
}
