package com.gloaguen2u.antFood.view;

// Key checking
import java.io.BufferedReader;
/// FIle writing
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
// Neural network & genetic pool creation
import java.util.ArrayList;

import com.gloaguen2u.antFood.controller.Ant;
import com.gloaguen2u.antFood.controller.AntGene;
import com.gloaguen2u.antFood.controller.AntPool;

import com.gloaguen2u.antFood.model.AntWorld;
import com.gloaguen2u.common.genalgs.Chromosome;
import com.gloaguen2u.common.Data;
import com.gloaguen2u.common.genalgs.Gene;
import com.gloaguen2u.common.Logger;

public class Console {
	// General parameters
	public static String outFileName = "out/out.json";
	// Simulation parameters
	public static int pop = 10;
	public static int foodMax = 1;
	public static int steps = 2000;
	public static int limit = 10;
	public static float sizeX = 100f;
	public static float sizeY = 100f;
	public static float foodPickup = 1f;
	public static float antStep = 1f;
	public static boolean data = true;
	// Evolutions parameters
	public static float minNetworkRange = -1f;
	public static float maxNetworkRange = 1f;
	public static float mutationRange = 0.1f;
	public static float mutationThreshold = 0.01f;

	public static boolean stop = false;

	public static boolean shouldStop() {
		synchronized (Console.class) {
			return stop;
		}
	}

	public static void main(String[] args) {
		parseArgs(args);
		int i;
		Data.mutateThreshold = mutationThreshold;
		Ant.stepSize = antStep;
		AntPool.minDirPickup = foodPickup;
		AntGene.sizeRange = maxNetworkRange - minNetworkRange;
		AntGene.minRange = minNetworkRange;
		AntGene.mutationRange = mutationRange;
		ArrayList<Ant> pool = new ArrayList<Ant>();
		for (i = 0; i < pop; i++) {
			Ant a = new Ant(Data.rng.nextFloat() * sizeX, Data.rng.nextFloat() * sizeY);
			// First chromosome.
			a.addChromosome(Chromosome.create(new Gene[] { AntGene.create(), AntGene.create(), AntGene.create(),
					AntGene.create(), AntGene.create() }));
			// Second chromosome.
			a.addChromosome(Chromosome.create(new Gene[] { AntGene.create(), AntGene.create(), AntGene.create(),
					AntGene.create(), AntGene.create() }));
			a.createBrain();
			pool.add(a);
		}
		try {
			InputStreamReader fileInputStream = new InputStreamReader(System.in);
			BufferedReader bufferedReader = new BufferedReader(fileInputStream);
			PrintWriter outFile = new PrintWriter(outFileName, "UTF-8");
			outFile.print("[");
			AntOutputImpl antOut = null;
			if (data) {
				antOut = new AntOutputImpl(0);
				AntWorld.output = antOut;
			}
			AntPool p = new AntPool(pool, steps, foodMax, sizeX, sizeY, 1);
			Logger.result("Starting ant emulations");
			for (i = 1; !shouldStop() && i <= limit; i++) {
				Logger.info("Generation " + (i));
				p.evaluate();
				outFile.print(p.getResult() + ",");
				p = p.nextGen();
				if (bufferedReader.ready())
					stop = true;
			}
			outFile.print("{}]");
			outFile.close();
			if (data)
				antOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Logger.result("Simulation ended cleanly");
	}
	
	public static void parseArgs(String[] args) {
		int argNum = 0;
		boolean inArg = false;
		boolean help = false;
		try {
			for(String s : args) {
				if(help)
					break;
				if(!inArg)
					switch(s) {
					case "-p":
					case "--population":
						inArg = true; argNum = 1; break;
					case "-f":
					case "--food":
						inArg = true; argNum = 2; break;
					case "-s":
					case "--steps":
						inArg = true; argNum = 3; break;
					case "-l":
					case "--limit":
						inArg = true; argNum = 4; break;
					case "-x":
					case "--size-x":
						inArg = true; argNum = 5; break;
					case "-y":
					case "--size-y":
						inArg = true; argNum = 6; break;
					case "--food-range":
						inArg = true; argNum = 7; break;
					case "--ant-speed":
						inArg = true; argNum = 8; break;
					case "-L":
					case "--logger":
						inArg = true; argNum = 9; break;
					case "-R":
					case "-random":
						inArg = true; argNum = 10; break;
					case "-o" :
					case "--out-file" :
						inArg = true; argNum = 11; break;
					case "--no-data":
						data = false; break;
					case "-h" :
					case "--help" :
						help = true; break;
					default:
						throw new Exception(s);
					}
				else {
					switch(argNum) {
					case 1:
						pop = Integer.parseInt(s); break;
					case 2:
						foodMax = Integer.parseInt(s); break;
					case 3:
						steps = Integer.parseInt(s); break;
					case 4:
						limit = Integer.parseInt(s); break;
					case 5:
						sizeX = Float.parseFloat(s); break;
					case 6:
						sizeY = Float.parseFloat(s); break;
					case 7:
						foodPickup = Float.parseFloat(s); break;
					case 8:
						antStep = Float.parseFloat(s); break;
					case 9:
						Logger.setLogLevel(Integer.parseInt(s)); break;
					case 10:
						Data.setRandom(Integer.parseInt(s)); break;
					case 11:
						outFileName = s; break;
					default:
						help = true; break;
					}
					inArg = false;
				}
			}
		}
		catch(Exception e) {
			Logger.setLogLevel(2);
			Logger.error("Invalid option : " + e.getMessage());
			help = true;
		}
		if(help) {
			Logger.setLogLevel(0);
			Logger.info("Usage :");
			Logger.info("-p \t --population \t[10]\tSets initial population number.");
			Logger.info("-f \t --food \t[1]\tSets initial food supply.");
			Logger.info("-s \t --steps \t[2000]\tNumber of emulated steps by ");
			Logger.info("-l \t --limit \t[10]\tMaximum number of emulated generations.");
			Logger.info("-x \t --size-x \t[100.0]\tSize (in units) of X coord ground.");
			Logger.info("-y \t --size-y \t[100.0]\tSize (in units) of Y coord ground.");
			Logger.info(" * \t --food-range \t[1.0]\tPickup range for food in units");
			Logger.info(" * \t --ant-speed \t[1.0]\tMax speed of ants in units.");
			Logger.info("-L \t --logger \t[0]\tLog level [0-3]");
			Logger.info("-R \t --random \t[none]\tInitial random seed");
			Logger.info("-o \t --out-file \t[out/]\tOutput folder");
			Logger.info(" * \t --no-data \t[]\tDon't output step data");
			Logger.info("-h \t --help \t[]\tDisplay this screen");
			System.exit(0);
		}
	}
}
