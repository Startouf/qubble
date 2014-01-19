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
	
	public static long getTime(){
		return Sys.getTime()*1000/Sys.getTimerResolution();
	}
	
	//delta = time before last frame and current frame
	public static long delta(long lastFrameTime){
		return Sys.getTime()*1000/Sys.getTimerResolution() - lastFrameTime;
	}
	
	public static long uniformRotation(){
		angle = ((angle + delta(lastFrameTimeAngle)/10) % 360);
		lastFrameTimeAngle = getTime();
		return angle;
	}
	
	public static long uniformRotation(float freq){
		angle = (long) ((angle + delta(lastFrameTimeAngle)/(1/freq)) % 360);
		lastFrameTimeAngle = getTime();
		return angle;
	}
	
	public static long uniformTranslation(){
		long delta = delta(lastFrameTimeTranslate);
		//Cannot go over max value for variables
		if (delta >= Long.MAX_VALUE - translate)
			translate = delta - (Long.MAX_VALUE - translate);
		else
			translate = translate + delta;
		
		lastFrameTimeAngle = getTime();
		return translate;
	}
	
	//Not working
	public static long uniformTranslation(long min, long max, float freq){
		long delta = delta(lastFrameTimeTranslate);
		if (delta >= Long.MAX_VALUE - translate)
			translate = delta - (Long.MAX_VALUE - translate);
		else
			translate = translate + delta;
		
		lastFrameTimeTranslate = getTime();
		
		System.out.println(translate);
		
		return (long) (translate%(max-min)+min);
	}
	
	
}
