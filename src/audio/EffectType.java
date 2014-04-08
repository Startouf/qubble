package audio;

import java.io.File;

import qubject.QubjectModifierInterface;

public enum EffectType implements QubjectModifierInterface {
	Flanger,
	Delay,
	Distortion,
	LPFilter,
	Volume;

	@Override
	public File getFile() {
		// TODO QubjectModifierInterface shouldn't ask for a file!!!!!
		return null;
	}

	@Override
	public String getName() {
		return this.toString();
	}
}
