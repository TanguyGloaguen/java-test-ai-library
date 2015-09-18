package com.gloaguen2u.antFood.test;

import com.gloaguen2u.antFood.controller.AntGene;

public class GeneTests {
	public static void testGene() throws FailedTestException {
		// Create a gene.
		AntGene g = AntGene.create();
		// Perform tests on the gene
	// Test 1 : is the gene is above min ?
		if (g.val < AntGene.minRange)
			throw new FailedTestException("GeneTest/1", "Gene value is under minimum size required");
	// Test 2 : is the gene below min ?
		if (g.val > AntGene.minRange + AntGene.sizeRange)
			throw new FailedTestException("GeneTest/2", "Gene value is over maximum size required");
		// Calculate mutation range
		float valMin = g.val - AntGene.mutationRange * (AntGene.sizeRange - AntGene.minRange);
		float valMax = g.val + AntGene.mutationRange * (AntGene.sizeRange - AntGene.minRange);
		// Select full range if mutation loops.
		if (valMax > AntGene.sizeRange + AntGene.minRange)
			valMin = AntGene.minRange;
		if (valMin < AntGene.minRange)
			valMax = AntGene.minRange + AntGene.sizeRange;
		g.mutate();
	// Test 3 : is the mutated gene not below accepted range ?
		if (g.val < valMin)
			throw new FailedTestException("GeneTest/3", "Mutated gene value is below maximum mutation");
	// Test 4 : is the mutated gene not above accepted range ?
		if (g.val > valMax)
			throw new FailedTestException("GeneTest/4", "Mutated gene value is over maximum mutation");

	}
}
