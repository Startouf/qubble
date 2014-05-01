package audio;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;


public abstract class SoundEffect implements SoundEffectInterface{
	
	protected EffectType type;
	protected int amount;
	protected final  Image image;
	
	public SoundEffect(EffectType type, int amount) {
		this.type = type;
		this.amount = amount;
		Image tryimage = null;
		try {
			tryimage = ImageIO.read(new FileInputStream("data/sound_effects/default.png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		image = tryimage;
	}
	
	@Override
	public int getAmount() {
		return amount;
	}
	@Override
	public void setAmount(int amount) {
		if (0 < amount) {
			if (amount < 100) {
				this.amount = amount;
			} else {
				this.amount = 100;
			}
		}
		else {
			this.amount = 0;
		}
	}
	@Override
	public EffectType getType() {
		return type;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "sois poli";
	}
	
	@Override
	public Image getImage(){
		return image;
	}
	
}
