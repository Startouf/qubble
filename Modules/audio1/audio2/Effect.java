package audio2;

import wav.*;
import java.lang.*;

import java.io.*;
import java.util.ArrayList;

import javax.sound.sampled.*;

public class Effect {

	public static int[] delay(ArrayList<Integer> samples, int rate, int decay,
			int feedback, float dryint, float wetint) { //0 < dryint < 100 (signal d'entree)
											  //0 < wetint < 100 (signal de sortie)
		
		int size = samples.size() + rate*feedback;
		int[] wet = new int[size];
		
		for (int i = 1; i < samples.size(); i++) {
			for (int j = 1; j < feedback; j++) {
				if ((j*rate) + i < size) {
					wet[(j*rate) + i] = wet[(j*rate) + i] + (samples.get(i) / (j*decay));
				}
			}
		}
		
		return add(samples, wet, dryint, wetint);
		
	}
	private static int[] add(ArrayList<Integer> dry, int[] wet, float dryint, float wetint) {
		int[] res;
		if (dry.size() > wet.length) {
			res = new int[dry.size()];
		}
		else {
			res = new int[wet.length];
		}
		for (int i = 0; i < res.length ; i++) {
			if (i < wet.length && i < dry.size()) {
				res[i] = (dry.get(i) * (int)(dryint/100)) + (wet[i] * (int)(dryint/100));
			}
			else if (i < wet.length) {
				res[i] = wet[i] * (int)(wetint/100);
			}
			else {
				res[i] = dry.get(i) * (int)(dryint/100);
			}
		}
		return res;
	}
	
	public static int[] disto(ArrayList<Integer> samples, float drive, int clip) {
		int[] res = new int[samples.size()];
		int coef = (int)(drive/10);
		float cut = clip/coef;
		//System.out.println("cut : " + cut);
		for (int i = 0; i < samples.size() ; i++) {
			int x = samples.get(i) % (int)(2*cut);
			//System.out.println("x : " + x);
			if (x < cut && x > -cut) {
				//res.add(coef * samples.get(i));
				res[i] = (coef * x);
			}
			else if (x >= cut) {
				res[i] = 2*clip - coef*x;
				//res.add(clip);
			}
			else {
				res [i] = -2*clip - coef*x;
				//res.add(-clip);
			}
		}
		return res;
	}
	public static ArrayList<Integer> distoArray(ArrayList<Integer> samples, float drive, int clip) {
		ArrayList<Integer> res = new ArrayList<Integer>();
		int coef = (int)(drive/10);
		float cut = clip/coef;
		//System.out.println("cut : " + cut);
		for (int i = 0; i < samples.size() ; i++) {
			int x = samples.get(i) % (int)(2*cut);
			//System.out.println("x : " + x);
			if (x < cut && x > -cut) {
				//res.add(coef * samples.get(i));
				res.add(coef * x);
			}
			else if (x >= cut) {
				res.add(2*clip - coef*x);
				//res.add(clip);
			}
			else {
				res.add(-2*clip - coef*x);
				//res.add(-clip);
			}
		}
		return res;
	}
}
