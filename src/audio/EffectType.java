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
	public String getName() {
		return this.toString();
	}
}
