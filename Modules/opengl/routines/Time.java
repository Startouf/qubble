package routines;

import org.lwjgl.Sys;

/**
 * @author Cyril
 * Some functions that generate useful time-variates variables
 *
 */
public final class Time
{
	private static long lastFrameTimeAngle = getTime(), angle = 0;
	private static long lastFrameTimeTranslate = getTime(), translate = 0;
	private static long tempDelta;
	public static int FPS;
	
	private static float modulo = 10000;

	/**
	 * Time in milliseconds
	 * @return time in milliseconds
	 */
	public static long getTime(){
		return Sys.getTime()*1000/Sys.getTimerResolution();
	}

	/**
	 * Time between last given frame and current frame
	 * @param lastFrameTime
	 * @return delta in milliseconds
	 */
	public static long getDelta(long lastFrameTime){
		return Sys.getTime()*1000/Sys.getTimerResolution() - lastFrameTime;
	}

	/**
	 * TODO should be changed to int or float
	 * Used to make objects revolve (period is about 7 seconds for one loop)
	 * Special case of UniformModulusTranslation
	 * @return an Automatically variated long
	 */
	public static long uniformRotation(){
		angle = ((angle + getDelta(lastFrameTimeAngle)/10) % 360);
		lastFrameTimeAngle = getTime();
		return angle;
	}

	/**
	 * Generate a uniformly variated translation that is then mapped to [min, max]
	 * ONLY USEABLE FOR ONE UNIQUE freq PER RUN !
	 * TODO : check if method is called with a different freq or handle multifreq
	 * @param min
	 * @param max
	 * @param freq number of loops (max -> min) to be done in 1 sec
	 * @return translation from min to max (then goes back to min) at freq per second
	 */
	public static float uniformModulusTranslation(float min, float max, float freq){
		translate += freq*getDelta(lastFrameTimeTranslate);
		lastFrameTimeTranslate = getTime();
		
		System.out.println((float) ((translate)/1000f*(max-min))%(max-min)+min);
		return (float) ((translate)/1000f*(max-min))%(max-min)+min;
	}


}
