package com.gloaguen2u.antFood.test;

import com.gloaguen2u.antFood.controller.Ant;
import com.gloaguen2u.antFood.controller.AntGene;
import com.gloaguen2u.common.FailedTestException;
import com.gloaguen2u.common.genalgs.Chromosome;
import com.gloaguen2u.common.genalgs.Gene;

public class AntTests {
	public static float sX = 100f;
	public static float sY = 100f;
	public static void testAnt() throws FailedTestException {
		// Create new ant
		Ant a = new Ant(50, 50);
		// Init genome
		Gene[] genomeL = new Gene[]{AntGene.create(), AntGene.create(), AntGene.create(), AntGene.create(), AntGene.create()};
		Gene[] genomeR = new Gene[]{AntGene.create(), AntGene.create(), AntGene.create(), AntGene.create(), AntGene.create()};
		
		a.addChromosome(Chromosome.create(genomeL));
		a.addChromosome(Chromosome.create(genomeR));
		a.createBrain();
		a.setDirFood(0, 0);
		AntTests tester = new AntTests(a);
		a.update(sX, sY);
		tester.evaluate();
	}
	
	private final Ant a;
	private final float x, y;
	
	public AntTests(Ant a) {
		this.a = a;
		x = a.getX();
		y = a.getY();
	}
	
	public void init() {
	}
	
	public void evaluate() throws FailedTestException {
// Tests 1-4 : is the ant in the play field ?
		if(a.getX() < 0)
			throw new FailedTestException("AntTests/1", "Ant out of the map ("+a.getX()+"<0)");
		if(a.getX() > sX)
			throw new FailedTestException("AntTests/2", "Ant out of the map ("+a.getX()+">sX)");
		if(a.getY() < 0)
			throw new FailedTestException("AntTests/3", "Ant out of the map ("+a.getY()+"<0)");
		if(a.getY() > sY)
			throw new FailedTestException("AntTests/4", "Ant out of the map ("+a.getY()+">sY)");
		float dX = x - a.getX();
		float dY = y - a.getY();
	// Test 5 : is the ant not too quick ?
		// Note : use 1.01x more to account for accuracy problems.
		if(Math.pow(dX, 2) + Math.pow(dY, 2) > Ant.stepSize * Ant.stepSize * 1.01)
			throw new FailedTestException("AntTests/5", "Ant too quick (" + dX + "^2+" + dY + "^2 > "+Ant.stepSize+"^2)");
	}
}
