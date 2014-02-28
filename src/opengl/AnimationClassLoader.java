package opengl;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.apache.commons.io.FilenameUtils;

public class AnimationClassLoader {
	
	public static AnimationControllerInterface compileAndLoadAnimation(File dotJavaFile) 
			throws CannotCompileAnimationException, MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		return (loadAnimationFromDotClass(compileAnimation(dotJavaFile)));
	}
	
	private static File compileAnimation(File sourceFile) throws CannotCompileAnimationException{
		// Compile source file.
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		String newPath = sourceFile.getPath()+ "compiled/";
		if (compiler.run(null, null, null, newPath) !=0){
			throw new CannotCompileAnimationException(sourceFile);
		}
		return new File (newPath +FilenameUtils.removeExtension(sourceFile.getName()) + ".class");
	}

	/**
	 * 
	 * @param dotClassFile the .class file associated with the animation
	 * @throws ClassNotFoundException 
	 * @throws MalformedURLException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private static AnimationControllerInterface loadAnimationFromDotClass(File dotClassFile) 
			throws ClassNotFoundException, MalformedURLException, InstantiationException, IllegalAccessException{
		// Load and instantiate compiled class.
		URLClassLoader classLoader;
		Class<?> cls;
		classLoader = URLClassLoader.newInstance(new URL[] { dotClassFile.toURI().toURL() });
		cls = Class.forName(dotClassFile.getName(), true, classLoader);
		AnimationControllerInterface instance = (AnimationControllerInterface) cls.newInstance();
		return instance;
	}
}
