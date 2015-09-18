package com.gloaguen2u.findFormula;

import com.gloaguen2u.common.Data;

import com.gloaguen2u.common.genalgs.Gene;

public class OperationGene implements Gene {
	public final int size;
	public final boolean[] content;
	/**
	 * Initialize the gene.
	 * @param size the gene size.
	 */
	private OperationGene(int size) {
		this.size = size;
		this.content = new boolean[size];
	}
	
	/**
	 * Create a new random Gene.
	 * @param the Gene size, in bits.
	 * @return the new Gene.
	 */
	public static OperationGene create(int size) {
		OperationGene res = new OperationGene(size);
		res.randomize();
		return res;
	}
	
	/**
	 * Copy the Gene
	 * @return The copied Gene.
	 */
	public Gene copy() {
		OperationGene res = new OperationGene(size);
		for(int i = 0; i < size; i++)
			res.content[i] = content[i];
		return res;
	}
	
	/**
	 * Randomize the Gene.
	 */
	public void randomize() {
		for(int i = 0; i < size; i++)
			content[i] = Data.rng.nextBoolean();
	}
	
	/**
	 * Mutate the Gene.
	 */
	public void mutate() {
		for(int i = 0; i < size; i++)
			if(Data.rng.nextFloat() < Data.mutateThreshold)
				content[i] = !content[i];
	}
}
