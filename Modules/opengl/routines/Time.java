package routines;

import org.lwjgl.Sys;

public final class Time
{
	private static long lastFrameTime = getTime(), angle = 0;
	
	public static long getTime(){
		return Sys.getTime()*1000/Sys.getTimerResolution();
	}
	
	public static long delta(long lastFrameTime){
		return Sys.getTime()*1000/Sys.getTimerResolution() - lastFrameTime;
	}
	
	public static long uniformRotation(){
		angle = ((angle + delta(lastFrameTime)/10) % 360);
		lastFrameTime = getTime();
		return angle;
	}
}
