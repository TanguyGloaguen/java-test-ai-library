package com.gloaguen2u.antFood.test;

import java.util.ArrayList;

import com.gloaguen2u.antFood.controller.Ant;
import com.gloaguen2u.antFood.controller.AntGene;
import com.gloaguen2u.antFood.controller.AntPool;

import com.gloaguen2u.common.genalgs.Chromosome;
import com.gloaguen2u.common.genalgs.Entity;
import com.gloaguen2u.common.genalgs.Gene;

public class PoolTests {
	public static void testPool() throws FailedTestException {
		// Create out lone ant
		Ant a = new Ant(50, 50);
		// Init ant genome
		Gene[] genomeL = new Gene[]{AntGene.create(), AntGene.create(), AntGene.create(), AntGene.create(), AntGene.create()};
		Gene[] genomeR = new Gene[]{AntGene.create(), AntGene.create(), AntGene.create(), AntGene.create(), AntGene.create()};
		a.addChromosome(Chromosome.create(genomeL));
		a.addChromosome(Chromosome.create(genomeR));
		a.createBrain();
		// Create ant tester
		AntTests tester = new AntTests(a);
		// Create pool
		ArrayList<Ant> poolContent = new ArrayList<Ant>();
		poolContent.add(a);
		AntPool p = new AntPool(poolContent, 1, 1, 100, 100, 0);
		// Evaluate pool
		p.evaluate();
	// Test 1 : is pool content what is expected ?
		for(Entity e : p.entities)
			if(e != a)
				throw new FailedTestException("PoolTests/1", "Wrong entity in entity list");
	// Test 2 : did the ant manage the tests ?
		try { tester.evaluate(); }
		catch(FailedTestException e) {
			throw new FailedTestException("PoolTests/2", "Ant didn't pass test : " +e.getName());
		}
	}
}
