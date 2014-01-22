package routines;

import java.util.Random;

public final class someMath
{
	private static Random random = new Random();
	private static float[] someRandoms = new float[]
			{random.nextFloat(), random.nextFloat(), random.nextFloat()};
	
	public static float[] getRandomNormal(){
		return normalize(new float[] {random.nextFloat(), random.nextFloat(), random.nextFloat()});
	}
	
	public static float[] getFluctuatingNormal(float[]vtx1, float[]vtx2, float[]vtx3, float freq, float delta){
		float[] v1=sub(vtx2, vtx1), v2=sub(vtx3, vtx1);
		return ( normalize(fluctuateNormal(crossprod(v1, v2), v1,v2,freq, delta)));
	}
	
	public static float[] getNormal(float[]vtx1, float[]vtx2, float[]vtx3){
		return ( normalize(crossprod(sub(vtx2, vtx1), sub(vtx3, vtx1))));
	}
	
	/**
	 * Edit method to change the effect
	 * @param n
	 * @param v1
	 * @param v2
	 * @param freq
	 * @param delta
	 * @return
	 */
	public static float[] fluctuateNormal(float[] n, float[] v1, float[] v2, float freq, float delta){
		//Choose among one of the desired effects :
		return (rotateVector(n,v1,v2,freq,delta));
	}
	
	/**
	 * Move away a vector and make it revolve around its previous axis
	 * 
	 * <--> delta (angle)
	 * n   n'	
	 * |__/
	 * | /
	 * |/______b1		(b1,b2,n) define a 3D space
	 *  \
	 *   \ 
	 *    \b2
	 * @param n vector to rotate (and the axis)
	 * @param b1 a vector of the plane perpendiculat to the axis
	 * @param b2 an other such vector (not colinear)
	 * @param freq frequency
	 * @param delta move away vector by delta angle
	 * @return
	 *
	 */
	public static float[] rotateVector(float[] n, float[] b1, float[] b2, float freq, float delta){
		//TODO
		return n;
	}
	
	public static float[] normalize(float[] v){
		float norm = (float) Math.sqrt(v[0]*v[0]+v[1]*v[1]+v[2]*v[2]);
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
	
	/**
	 * Retrieves a vector float[3] from a list of vectorCoords {x1,y1,z1,	x2,y2,z2...}
	 * @param vertexCoordList An Array of VectorCoords  {x1,y1,z1,	x2,y2,z2...}
	 * @param vertex	position of the Vector in the VertexCoords list (Starts from 0!!)
	 * @return 
	 */
	public static float[] getVertexFromCoords3f(float[] vertexCoordList, int vertex){
		return new float[] {vertexCoordList[3*vertex],
				vertexCoordList[3*vertex+1],
				vertexCoordList[3*vertex+2]};
	}
	/**
	 * Inverse of getVertexFromCoords3f
	 * @param vertexCoords 
	 * @param vertex
	 * @param position Starts from 0 ! (the ith vector)
	 */
	public static void mapVectorToCoords3f(float[] vertex, float[] vertexCoords, int position){
		for(int i =0; i<3;i++){
			vertexCoords[3*position+i] = vertex[i];
		}
	}
}
