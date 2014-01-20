package routines;

import java.util.Random;

public final class someMath
{
	private static Random random = new Random();
	private static float[] someRandoms = new float[]
			{random.nextFloat(), random.nextFloat(), random.nextFloat()};
	
	public static float[] getRandomNormal(){
		return normalize(new float[] {
				random.nextFloat(), random.nextFloat(), random.nextFloat()});
	}
	
	public static float[] getFluctuatingNormal(float[]vtx1, float[]vtx2, float[]vtx3, float freq, float delta){
		return ( normalize(fluctuateNormal(crossprod(sub(vtx2, vtx1), sub(vtx3, vtx1)), freq, delta)));
	}
	
	public static float[] getNormal(float[]vtx1, float[]vtx2, float[]vtx3){
		return ( normalize(crossprod(sub(vtx2, vtx1), sub(vtx3, vtx1))));
	}
	
	public static float[] fluctuateNormal(float[] n, float freq, float delta){
		//TODO : fluctuate around the normal with frequency f and delta 
		//(Might need another parameter)
		return getRandomNormal();
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
