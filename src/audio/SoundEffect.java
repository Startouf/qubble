package audio;

import java.io.File;


public abstract class SoundEffect implements SoundEffectInterface{
	
	public static final int volume = 1;
	public static final int distortion = 2;
	public static final int delay = 3;
	public static final int flanger = 4;
	
	private int type;
	protected int amount;
	
	public SoundEffect(int type, int amount) {
		this.type = type;
		this.amount = amount;
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
	public int getType() {
		return type;
	}
	@Override
	public File getFile() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "suce ma bite";
	}
	
}
