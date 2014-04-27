package qubject;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Animation implements AnimationInterface {
	private final Class<?> controllerClass;
	private final String name;
	private final File dotJavaFile;
	private File dotClassFile;
	private final Image image;
	
	public Animation(String name, File dotJavafile, Class controllerClass, Image image){
		this.name = name;
		this.dotJavaFile = dotJavafile;
		this.controllerClass = controllerClass;
		this.image = image;
	}
	
	public Animation(String name, File dotJavafile, Class controllerClass){
		this.name = name;
		this.dotJavaFile = dotJavafile;
		this.controllerClass = controllerClass;
		Image tryImage = null;
		try {
			tryImage = ImageIO.read(new FileInputStream("data/animations/default.png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.image = tryImage;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public File getDotJavaFile() {
		return dotJavaFile;
	}

	@Override
	public Class getAnimationControllerClass() {
		return controllerClass;
	}

	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return null;
	}
}
