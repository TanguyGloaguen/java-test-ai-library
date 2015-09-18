package com.gloaguen2u.antFood.controller;

import com.gloaguen2u.common.Data;
import com.gloaguen2u.common.genalgs.Gene;

public class AntGene implements Gene {
	public float val;
	public static float minRange = 0f;
	public static float sizeRange = 1f;
	public static float mutationRange = 0.1f;

	private AntGene() {
	}

	public static AntGene create() {
		AntGene res = new AntGene();
		res.randomize();
		return res;
	}

	@Override
	public void randomize() {
		val = Data.rng.nextFloat() * sizeRange + minRange;
	}

	@Override
	public AntGene copy() {
		AntGene res = new AntGene();
		res.val = val;
		return res;
	}

	@Override
	public void mutate() {
		if (Data.rng.nextFloat() < Data.mutateThreshold) {
			val += (Data.rng.nextFloat() * sizeRange + minRange) * mutationRange;
			if (val > minRange + sizeRange)
				val -= minRange + sizeRange;
			if (val < minRange)
				val += minRange + sizeRange;
		}
	}
}
