package com.gloaguen2u.common;

import java.util.Random;

public class Data {
	public static Random rng = new Random();
	public static float mutateThreshold = 0.01f;
	public static void setRandom(int seed) {
		rng = new Random(seed);
	}
}
