package routines;

import java.util.Random;

import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;

public final class someMath
{
	private static Random random = new Random();
	
	public static float[] getRandomNormal(){
		return normalize(new float[] {random.nextFloat(), random.nextFloat(), random.nextFloat()});
	}
	
	public static float[] getNormal(float[]vtx1, float[]vtx2, float[]vtx3){
		return ( normalize(crossprod(sub(vtx2, vtx1), sub(vtx3, vtx1))));
	}
	
	public static float[] getWeirdNormal(float[]vtx1, float[]vtx2, float[]vtx3){
		return ( normalize(crossprod(sub(vtx2, vtx1), sub(vtx3, vtx1))));
	}
	
	public static float[] normalize(float[] v){
		float norm = (float) Math.sqrt(v[0]*v[0]+v[0]*v[0]+v[0]*v[0]);
		return(new float[] { v[0]/norm, v[1]/norm, v[2]/norm});
	}
	
	public static float[] sub(float[]v2, float[] v1){
		return new float[] { v2[0]-v1[0], v2[1]-v1[1], v2[2]-v1[1] };
	}
	
	public static float[] crossprod(float[] v1, float[] v2){
		return (new float[] {
				v1[1]*v2[2]-v1[2]*v2[1],
				v1[2]*v2[0]-v1[0]*v2[2],
				v1[0]*v2[1]-v1[1]*v1[0]
		});
	}
}
