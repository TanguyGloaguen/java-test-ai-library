package com.gloaguen2u.common.kohonen;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import com.gloaguen2u.common.Data;

public class World2D {
	public final int dim;
	public final int sX;
	public final int sY;
	public static float sigma = 1;
	public static float lambda;
	public static float learn = .1f;
	public static int maxIters = 1000;
	private final float[][] content;
	public int t = 1;
	public boolean stop = false;
	public World2D(int dim, int sizeX, int sizeY) {
		this.dim = dim;
		this.sX = sizeX;
		this.sY = sizeY;
		this.content = new float[sX * sY][dim];
		init();
	}
	
	public void init() {
		for(int i = 0; i < sX; i++)
			for(int j = 0; j < sY ; j++)
				for(int k = 0; k < dim; k++)
					content[i+sX*j][k] = Data.rng.nextFloat();
	}
	
	public void affectNode(float[] node) {
		// Calculus utils.
		float radius = sigma * (float) Math.exp(-((float) t) / lambda);
		float learning = learn * (float) Math.exp(-((float) t) / lambda);
		float dst;
		// Get BMU (best matching unit)
		int bmu = 0;
		float mDist = dist(node, content[0]);
		float tDist = 0;
		for(int i = 0; i < sX; i++)
			for(int j = 0; j < sY; j++)
				if((tDist = dist(content[i+j*sX], node)) < mDist) {
					bmu = i+j*sX;
					mDist = tDist;
				}
		// System.out.println("bmu : {" + content[bmu][0] + "/" + content[bmu][1] + "/" + content[bmu][2] + "}["+bmu+"]");
		// System.out.println("Radius : " + radius);
		// Apply BMU mods
		radius *= radius;
		for(int i = 0; i < sX; i++)
			for(int j = 0; j < sY; j++)
				if((dst = rad(i+j*sX, bmu)) <= radius) {
					float influence = (float) Math.exp(- (dst*dst) / (2*radius));
					for(int k = 0; k < dim; k++)
						content[i+j*sX][k] += influence * learning * (node[k] - content[i+j*sX][k]);
				}
	}
	
	private float dist(float[] a, float[] b) {
		float res = 0;
		for(int i = 0; i < dim; i++) {
			float tmp = a[i] - b[i];
			tmp *= tmp;
			res += tmp;
		}
		return res;
	}
	
	private float rad(int a, int b) {
		int aX = a % sX;
		int aY = a / sX;
		int bX = b % sX;
		int bY = b / sX;
		return (bX - aX) * (bX - aX) + (bY - aY) * (bY - aY);
	}

	public void output(String filename) {
		PrintWriter dataFile = null;
		try {
			dataFile = new PrintWriter(filename, "UTF-8");
			dataFile.print("[");
			for(int i = 0; i < sX; i++)
				for(int j = 0; j < sY; j++) {
					int r = (int) ((content[i+sX*j][0]) * 255);
					int g = (int) ((content[i+sX*j][1]) * 255);
					int b = (int) ((content[i+sX*j][2]) * 255);
					if(i+j>0)
						dataFile.println(",");
					dataFile.print("{\"r\":"+r+",\"g\":"+g+",\"b\":"+b+",\"x\":"+i+",\"y\":"+j+"}");
				}
			dataFile.print("]");
			dataFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
