package com.gloaguen2u.shopTown;

import java.util.ArrayList;
import java.util.Collection;

import com.gloaguen2u.common.Logger;
import com.gloaguen2u.common.genalgs.Chromosome;
import com.gloaguen2u.common.genalgs.Entity;
import com.gloaguen2u.common.genalgs.Gene;
import com.gloaguen2u.common.genalgs.Pool;

public class ShopPool extends Pool<Entity> {
	private final float target;
	public boolean stop = false;

// Pool functions
	public ShopPool(Collection<Entity> ents, float e) {
		super(ents);
		target = e;
	}
	
	@Override
	public ShopPool nextGen() {
		Collection<Entity> ents = new ArrayList<Entity>();
		for(int i = 0; i < entities.size(); i++)
			ents.add(makeBaby());
		return new ShopPool(ents, target);
	}
	
	@Override
	public void evaluate() {
		if(evalSum != 0)
			return;
		for(Entity e : entities) {
			float res = 1f / (float) Math.exp(-getResult(e));
			evalSum += res;
			entEvals.put(e, res);
		}
		
	}

// Number functions
	
	private static float getResult(Entity e) {
		Chromosome main = e.identity.get(0);
		int gains = getGains(main);
		int mags = getMagasins(main);
		//Logger.info("G : " + gains + " / M : " + mags);
		float res = gains - mags;
		return res;
	}
	
	public static void display(Entity e, float target) {
		float gains = (getGains(e.identity.get(0)));
		float deps = (getMagasins(e.identity.get(0)));
		float res = gains - deps;
		if(res < target)
			Logger.info(fullOP(e) + " => " + gains + " / " + deps + "\t =>" + res);
		else
			Logger.result(fullOP(e) + " => " + gains + " / " + deps + "\t =>" + res);
	}
	
// Internal functions
	public static int[] townPrices;
	private static int getGains(Chromosome c) {
		boolean town[] = new boolean[12];
		for(int i = 0; i < c.size; i++) {
			if(((TownGene) c.content[i]).content) {
				for(int pos : getVoisinage(i))
					town[pos] = true;
			}
		}
		int res = 0;
		for(int i = 0; i < 12; i++)
			res += town[i] ? townPrices[i] : 0; 
		return res;
	}
	
	public static boolean townLayout[][];
	
	private static ArrayList<Integer> getVoisinage(int i) {
		ArrayList<Integer> res = new ArrayList<Integer>();
		for(int j = 0; j < 12; j++)
			if(townLayout[i][j])
				res.add(j);
		return res;
	}
	
	private static int getMagasins(Chromosome c) {
		int res = 0;
		for(int i = 0; i < c.size; i++)
			res += ((TownGene) c.content[i]).content ? 6 : 0; 
		return res;
	}
	
	private static String fullOP(Entity e) {
		String res = "";
		Chromosome main = e.identity.get(0);
		for(Gene g : main.content)
			res += "[" + g.toString() + "]";
		return res;
	}
}
