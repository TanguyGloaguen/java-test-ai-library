package com.gloaguen2u.common.genalgs;

public interface Gene {
	/**
	 * Copy current Gene
	 * @return the Gene copy
	 */
	public Gene copy();
	/**
	 * Randomizes the Gene content.
	 */
	public void randomize();
	/**
	 * Mutate the gene.
	 */
	public void mutate();
}
