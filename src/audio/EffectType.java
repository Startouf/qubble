package audio;

import java.awt.Image;
import java.io.File;

import qubject.QubjectModifierInterface;

public enum EffectType implements QubjectModifierInterface {
	Flanger,
	Delay,
	Distortion,
	LPFilter,
	Volume;

	@Override
	public String getName() {
		return this.toString();
	}

	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return null;
	}
}
