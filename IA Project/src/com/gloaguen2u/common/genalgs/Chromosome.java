package com.gloaguen2u.common.genalgs;

import com.gloaguen2u.common.Data;

public class Chromosome {
	public final int size;
	public final Gene[] content;
	
	private Chromosome(int size, Gene[] content) {
		this.size = size;
		this.content = content;
	}
	
	public static Chromosome create(Gene[] genome) {
		return new Chromosome(genome.length, genome);
	}
	
	public Chromosome copy() {
		Gene[] resGen = new Gene[size];
		for(int i = 0; i < size; i++)
			resGen[i] = content[i].copy();
		return new Chromosome(size, resGen);
	}
	
	public void randomize() {
		for(int i = 0; i < size; i++) {
			content[i].randomize();
		}
	}
	
	/**
	 * Check if two chromosomes are compatible.
	 */
	public boolean isCompatible(Chromosome other) {
		if(size != other.size)
			return false;
		for(int i = 0; i < size; i++)
			if(other.content[i].getClass() != content[i].getClass())
				return false;
		return true;
	}
	
	/**
	 * Combine two chromosomes
	 */
	public Chromosome combine(Chromosome other) {
		Chromosome res = this.copy();
		int threshold = Data.rng.nextInt(content.length);
		for(int i = threshold; i < content.length; i++) {
			res.content[i] = other.content[i];
			res.content[i].mutate();
		}
		return res;
	}
}
