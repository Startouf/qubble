package database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.apache.commons.io.FilenameUtils;

import qubject.QubjectModifierInterface;

public class DataTools
{
	/**
	 * Returns the files of the current directory that match the saveFormat defined in this class
	 * @param directory
	 * @return list of files in the directory filtered by filename (current : *.properties)
	 */
	public static File[] getDotProperties(String directory){
		File dir = new File(directory);
		File [] files = dir.listFiles(
				new FilenameFilter() 
				{
					@Override
					public boolean accept(File dir, String name) {
						return name.endsWith(".properties");
					}
				});
		return files;
	}
	
	public static File[] getDotWAV(String directory){
		File dir = new File(directory);
		File [] files = dir.listFiles(
				new FilenameFilter() 
				{
					@Override
					public boolean accept(File dir, String name) {
						return (name.endsWith(".WAV") || name.endsWith(".wav") || name.endsWith(".Wav"));
					}
				});
		return files;
	}

	public static void compileAnimation(File dotJavaFile){
		//Some Copy Paste. Check
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		if (compiler == null){
			System.err.println("Compiler not found ! Cannot compile animation :"+ dotJavaFile.getName());
			return;
		}
		//TODO : not working with Windows ?
		String file;
		try {
			file = dotJavaFile.getCanonicalPath().toString();
			 if(compiler.run(null, null, null, file) != 0){
				 System.err.println("Compilation failed for : " + file);
			 }
		} catch (IOException e) {
			System.err.println("Couldn't compile animation!");
			e.printStackTrace();
		} catch (NullPointerException e){
			System.err.println("Couldn't compile animation!");
			e.printStackTrace();
		}
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
	
	public static void saveProperties(Properties prop, String fileName){
		
		File file = new File(fileName);
		if(!file.exists()) {
		    try {
				file.createNewFile();
				prop.store(new FileOutputStream(fileName), null);
			} catch (IOException e) {
				System.err.println("Could not save properties file " + fileName);
				e.printStackTrace();
			}
		} 
	}

	public static void EnsureDirExists(String path) {
		File f = new File(path);
		if (!f.mkdirs() && !f.exists()){	//If the folder doesn't exist and cannot be created
			System.err.println("Could not create save directory");
		}
	}
}
