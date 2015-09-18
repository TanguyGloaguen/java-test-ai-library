package com.gloaguen2u.findFormula;

import java.util.ArrayList;

import com.gloaguen2u.common.Data;
import com.gloaguen2u.common.Logger;
import com.gloaguen2u.common.genalgs.Chromosome;
import com.gloaguen2u.common.genalgs.Entity;

public class Main {

	public static int nbGenes = 3;
	public static int nbEntities = 100;
	public static int limit = 100000;
	public static float target = 10f;
	
	public static void main(String[] args) {
		Logger.setLogLevel(0);
		retrieveArgs(args);
		// Create gene pool
		Entity e = new Entity();
		OperationGene[] genome = new OperationGene[nbGenes];
		for(int i = 0; i < nbGenes; i++)
			genome[i] = OperationGene.create(4);
		e.addChromosome(Chromosome.create(genome));
		ArrayList<Entity> ents = new ArrayList<Entity>();
		for(int i = 0; i < nbEntities; i++)
			ents.add(e.copy().randomize());
		NumberPool p = new NumberPool(ents, target);
		int i = 0;
		while(!p.stop && i < limit) {
			i++;
			p.evaluate();
			if(!p.stop)
				p = p.nextGen();
		}
		Logger.result("Ended in " + i + " generations.");
		for(Entity ent : p.entities)
			NumberPool.display(ent, target);
	}

	private static void retrieveArgs(String[] args) {
		boolean inArg = false;
		boolean help = false;
		int argNum = 0;
		try {
			for(String s : args) {
				if(!inArg) {
					switch(s) {
					case "-t" :
					case "--target" :
						argNum = 1; inArg = true; break;
					case "-o" :
					case "--operations" :
						argNum = 2; inArg = true; break;
					case "-p" :
					case "--population" :
						argNum = 3; inArg = true; break; 
					case "-l" :
					case "--limit" :
						argNum = 4; inArg = true; break;
					case "-L" :
					case "--level" :
						argNum = 5; inArg = true; break;
					case "-R" :
					case "--random" :
						argNum = 6; inArg = true; break;
					case "-h" :
					case "--help" :
						help = true; break;
					default:
						throw new Exception(s);
					}
				}
				else {
					switch(argNum) {
					case 1 :
						target = Float.parseFloat(s); break;
					case 2 :
						nbGenes = Integer.parseInt(s); break;
					case 3 :
						nbEntities = Integer.parseInt(s); break;
					case 4 : 
						limit = Integer.parseInt(s); break;
					case 5 :
						Logger.setLogLevel(Integer.parseInt(s)); break;
					case 6 :
						Data.setRandom(Integer.parseInt(s)); break;
					default :
						help = true; break;
					}
					inArg = false;
				}
			}
		} catch (Exception e) {
			Logger.error("Unknown option : " + e.getMessage());
			help = true;
		}
		if(help) {
			Logger.setLogLevel(0);
			Logger.info("Allowed options :");
			Logger.info("-t --target \t [10] \t the target number");
			Logger.info("-p --population \t [100] \t number of concurrent operation sets to test on");
			Logger.info("-o --operations \t [3] \t the number of operations in a set");
			Logger.info("-l --limit \t [100000] \t the search depth limit");
			Logger.info("-L --logger \t [0] \t the logger level (0=info, 4=result)");
			Logger.info("-R -random \t [random] \t the random number generator seed");
			System.exit(0);
		}
	}
}
