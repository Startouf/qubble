package routines;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.Point;

public class UserInputs
{
	private static long lastFrame = Time.getTime();
	
	public static void rotate(Point coords, float angleUser, float delta){
		if (Mouse.isButtonDown(0)){ //Will generate as many lines as FPS
			int x = Mouse.getX();
			int y = Mouse.getY();

			System.out.println("Mouse @ (" + x+", " +y+")");
		}

		while(Keyboard.next()){ //Will generate only one line per event
			if (Keyboard.getEventKeyState()){ //Key pressed
				if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT){
					System.out.println("Right pressed");
					angleUser = (int) ((angleUser + delta) % 360);
				}
				else if (Keyboard.getEventKey() == Keyboard.KEY_LEFT){
					System.out.println("LEFT pressed");
					angleUser = (int) ((angleUser - delta) % 360);
				}
			}
			else{ //key released
				if (Keyboard.getEventKey() == Keyboard.KEY_A){
					System.out.println("A released");
				}
			}
		}
	}
}
