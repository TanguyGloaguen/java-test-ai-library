package com.gloaguen2u.shopTown;

import java.util.ArrayList;

import com.gloaguen2u.common.Data;
import com.gloaguen2u.common.Logger;
import com.gloaguen2u.common.genalgs.Chromosome;
import com.gloaguen2u.common.genalgs.Entity;

public class Main {

	public static int nbGenes = 12;
	public static int nbEntities = 500;
	public static int limit = 1000;
	public static float target = 10f;
	
	public static void main(String[] args) {
		Logger.setLogLevel(0);
		Data.mutateThreshold = 0.2f;
		retrieveArgs(args);
		// Create gene pool
		boolean[] tl0 = {true, true, false, false, true, true, false, false, false, false, false, false};
		boolean[] tl1 = {true, true, true, false, false, true, false, false, false, false, false, false};
		boolean[] tl2 = {false, true, true, true, false, false, true, false, false, false, false, false};
		boolean[] tl3 = {false, false, true, true, false, false, true, false, false, false, false, false};
		boolean[] tl4 = {true, false, false, false, true, true, false, true, false, true, true, false};
		boolean[] tl5 = {true, false, false, false, true, true, false, true, false, true, true, false};
		boolean[] tl6 = {true, true, false, false, true, true, true, true, false, false, false, true};
		boolean[] tl7 = {false, false, false, false, true, true, false, true, false, true, true, false};
		boolean[] tl8 = {false, false, false, false, false, true, false, true, true, false, false, true};
		boolean[] tl9 = {false, false, false, false, true, false, false, false, false, true, true, false};
		boolean[] tl10 = {false, false, false, false, true, false, false, false, false, true, true, true};
		boolean[] tl11 = {false, false, false, false, false, false, false, true, true, false, true, true};
		ShopPool.townLayout = new boolean[][]{tl0, tl1, tl2, tl3, tl4, tl5, tl6, tl7, tl8, tl9, tl10, tl11};
		ShopPool.townPrices = new int[]{1, 8, 6, 3, 2, 4, 2, 2, 1, 1, 1, 3};
		Entity e = new Entity();
		TownGene[] genome = new TownGene[nbGenes];
		for(int i = 0; i < nbGenes; i++)
			genome[i] = TownGene.create();
		e.addChromosome(Chromosome.create(genome));
		ArrayList<Entity> ents = new ArrayList<Entity>();
		for(int i = 0; i < nbEntities; i++)
			ents.add(e.copy().randomize());
		ShopPool p = new ShopPool(ents, target);
		int i = 0;
		ShopPool lastP = p;
		try {
		while(!p.stop && i < limit) {
			//Logger.info("Gen. " + i);
			i++;
			p.evaluate();
			lastP = p;
			if(!p.stop)
				p = p.nextGen();
		}
		}
		catch(Exception ex) {
			p = lastP;
		}
		Logger.result("Ended in " + i + " generations.");
		for(Entity ent : p.entities)
			ShopPool.display(ent, 15);
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
