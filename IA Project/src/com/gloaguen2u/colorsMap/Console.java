package com.gloaguen2u.colorsMap;

import java.util.ArrayList;

import com.gloaguen2u.common.Data;
import com.gloaguen2u.common.kohonen.World2D;

public class Console {

	public static ArrayList<float[]> targets = new ArrayList<float[]>();
	public static int iterations = 1000;
	public static int sX = 40;
	public static int sY = 40;
	public static float learningRate = .1f;
	
	public static void main(String[] args) {
		parseArgs(args);
		//testSet();
		World2D.maxIters = iterations;
		World2D.sigma = 10 * Math.max(sX, sY);
		World2D.learn = learningRate;
		World2D.lambda = World2D.maxIters / (float) Math.log(World2D.sigma);
		World2D set = new World2D(3, sX, sY);
		while(set.t < World2D.maxIters) {
			int i = Data.rng.nextInt(targets.size());
			set.affectNode(targets.get(i));
			set.t++;
		}
		System.out.println("Gen : " + set.t);
		set.output("out.json");
	}

	private static void parseArgs(String[] args) {
		int argNum = 0;
		boolean inArg = false;
		boolean help = false;
		try {
			for(String s : args) {
				if(help)
					break;
				if(!inArg)
					switch(s) {
					case "-i":
					case "--iterations":
						inArg = true; argNum = 1; break;
					case "-x":
					case "--size-x":
						inArg = true; argNum = 2; break;
					case "-y":
					case "--size-y":
						inArg = true; argNum = 3; break;
					case "-l":
					case "--learning-rate":
						inArg = true; argNum = 4; break;
					case "-c" :
					case "--color":
						inArg = true; argNum = 5; break;
					case "-h" :
					case "--help" :
						help = true; break;
					default:
						throw new Exception(s);
					}
				else {
					switch(argNum) {
					case 1:
						iterations = Integer.parseInt(s); break;
					case 2:
						sX = Integer.parseInt(s); break;
					case 3:
						sY = Integer.parseInt(s); break;
					case 4:
						learningRate = Float.parseFloat(s); break;
					case 5:
						//String[] sParts2 = s.split("([0-9A-F])([0-9A-F])([0-9A-F])");
						float r = ((float) Integer.parseInt(s.substring(0, 2), 16)) / 255f;
						float g = ((float) Integer.parseInt(s.substring(2, 4), 16)) / 255f;
						float b = ((float) Integer.parseInt(s.substring(4, 6), 16)) / 255f;
						targets.add(new float[]{r,g,b});
						break;
					default:
						help = true; break;
					}
					inArg = false;
				}
			}
		}
		catch(Exception e) {
			System.out.println("[E] " + e.toString());
			help = true;
		}
		if(help) {
			System.exit(0);
		}
		if(targets.isEmpty())
			testSet();
	}
	
	private static void testSet() {
		// Test set
		targets.add(new float[]{1f	, 0f	, 0f	});	// Red
		targets.add(new float[]{0f	, 1f	, 0f	});	// Light Green
		targets.add(new float[]{0f	, 0f	, 1f	});	// Blue
		targets.add(new float[]{1f	, .5f	, 0f	});	// Orange
		targets.add(new float[]{0f	, .5f	, 0f	});	// Green
		targets.add(new float[]{.5f	, .5f	, 1f	});	// Light blue
		targets.add(new float[]{.5f	, 0f	, 1f	});	// Deep purple
		targets.add(new float[]{0f	, 1f	, 1f	});	// Turquoise
		targets.add(new float[]{1f	, 0f	, .5f	});	// Deep pink
		targets.add(new float[]{1f	, 1f	, .5f	});	// idk
		targets.add(new float[]{1f	, .5f	, .5f	});	// Light red
		targets.add(new float[]{1f	, .5f	, 1f	});	// idk
		targets.add(new float[]{1f	, 1f	, 1f	});	// White
		targets.add(new float[]{0f	, 0f	, 0f	});	// Black
	}
}
