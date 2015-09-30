package com.gloaguen2u.shopTown;

import com.gloaguen2u.common.Data;

import com.gloaguen2u.common.genalgs.Gene;

public class TownGene implements Gene {
	public boolean content;
	/**
	 * Initialize the gene.
	 * @param size the gene size.
	 */
	private TownGene() {
		this.content = false;
	}
	
	/**
	 * Create a new random Gene.
	 * @param the Gene size, in bits.
	 * @return the new Gene.
	 */
	public static TownGene create() {
		TownGene res = new TownGene();
		res.randomize();
		return res;
	}
	
	/**
	 * Copy the Gene
	 * @return The copied Gene.
	 */
	public Gene copy() {
		TownGene res = new TownGene();
		res.content = content;
		return res;
	}
	
	/**
	 * Randomize the Gene.
	 */
	public void randomize() {
		content = Data.rng.nextBoolean();
	}
	
	/**
	 * Mutate the Gene.
	 */
	public void mutate() {
		if(Data.rng.nextFloat() < Data.mutateThreshold)
			content = !content;
	}
	
	/**
	 * toString
	 */
	public String toString() {
		return content ? "1" : "0";
	}
}
