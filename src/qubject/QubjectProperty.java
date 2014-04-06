package qubject;

/**
 * Lists all the properties for Qubjects
 * i.e. their behaviour upon certain actions
 * @author Cyril
 *
 */

public enum QubjectProperty {
	SAMPLE_WHEN_PLAYED("Son quand joué"),
	AUDIO_EFFECT_Y_AXIS("Effet quand translaté en Y"),
	AUDIO_EFFECT_ROTATION("Effet quand tourné"),
	ANIM_WHEN_DETECTED("Anim. quand détecté"),
	ANIM_WHEN_PLAYED("Anim. quand joué");
	
	private final String userFriendlyString;
	
	private QubjectProperty(String userFriendly){
		this.userFriendlyString = userFriendly;
	}

	public String getUserFriendlyString(){
		return userFriendlyString;
	}
}


