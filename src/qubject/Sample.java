package qubject;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class Sample implements SampleInterface{
	
	private static final Image image;
	static{
		Image tryimage = null;
		try {
			tryimage = ImageIO.read(new FileInputStream("image name and path"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		image = tryimage;
	}
	
	private String name;
	private File file;
	
	public Sample(String name, File file){
		this.name = name;
		this.file = file;
	}

	@Override
	public File getFile() {
		return this.file;
	}

	@Override
	public float getDuration() {
		//TODO
		return 60;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Image getImage() {
		return null;
	}
}
