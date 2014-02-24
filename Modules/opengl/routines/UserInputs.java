package routines;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.Point;

public class UserInputs
{
	private static long lastFrame = Time.getTime();
	
	/**
	 * Modifies a given variable with left and right arrow keys
	 * @param var variable to modify
	 * @param delta amount to add/substract
	 */
	public static float incrementWithArrowKeys(float var, float delta){
		while(Keyboard.next()){ //Will generate only one line per event
			if (Keyboard.getEventKeyState()){ //Key pressed
				if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT){
					System.out.println("Right pressed : var is now" + var);
					var += delta;
				}
				else if (Keyboard.getEventKey() == Keyboard.KEY_LEFT){
					System.out.println("LEFT pressed : var is now " + var);
					var -= delta;
				}
			}
			else{ //key released
				if (Keyboard.getEventKey() == Keyboard.KEY_A){
					System.out.println("A released");
				}
			}
		}
		return var;
	}
	
	public void forLater(){
		if (Mouse.isButtonDown(0)){ //Will generate as many lines as FPS
			int x = Mouse.getX();
			int y = Mouse.getY();

			System.out.println("Mouse @ (" + x+", " +y+")");
		}
	}
}
