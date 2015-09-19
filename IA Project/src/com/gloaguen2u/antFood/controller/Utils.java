package com.gloaguen2u.antFood.controller;

import com.gloaguen2u.antFood.controller.Ant.UtilsConnector;
import com.gloaguen2u.common.genalgs.Chromosome;
import com.gloaguen2u.common.neuralnets.FloatNeuron;

public abstract class Utils {
	public static void createBrain(Chromosome c, UtilsConnector ant, int pos) {
		FloatNeuron n1 = new FloatNeuron();
		n1.Connect(ant.getDX(), ((AntGene) c.content[0]).val);
		n1.Connect(ant.getDY(), ((AntGene) c.content[1]).val);
		n1.Connect(ant.getFX(), ((AntGene) c.content[2]).val);
		n1.Connect(ant.getFY(), ((AntGene) c.content[3]).val);
		n1.setThreshold(((AntGene) c.content[4]).val);
		ant.addNeuron(n1, 0);

		if (pos == 1)
			ant.setOX(n1);
		else
			ant.setOY(n1);
	}
}
