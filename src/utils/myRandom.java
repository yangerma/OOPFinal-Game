package utils;

import java.util.Random;

public class myRandom {
	public static int randInt(int min, int max) {
		assert(min<=max);

		Random r = new Random();
		return r.nextInt(max-min+1) + min;
	}
}
