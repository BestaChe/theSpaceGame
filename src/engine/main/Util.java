package engine.main;

import java.util.Random;

public class Util {

	/**
	 * Calculates the euclidian distance between 2 points
	 * @param x - float x of the first point
	 * @param y - float y of the first point
	 * @param x1 - float x of the second point
	 * @param y1 - float y of the second point
	 * @return double euclidian distance
	 */
	public static double dist( float x, float y, float x1, float y1) {
		return Math.sqrt( (x-x1)*(x-x1) + (y-y1)*(y-y1));
	}
	
	/**
	 * 
	 * @param n
	 * @param m
	 * @return
	 */
	public static int randomBetween( int n, int m ) {
		Random rd = new Random();
		return (rd.nextInt((m-n)+1)+n);
	}

}
