package com.gloaguen2u.findFormula;

import java.util.ArrayList;
import java.util.Collection;

import com.gloaguen2u.common.Logger;
import com.gloaguen2u.common.genalgs.Chromosome;
import com.gloaguen2u.common.genalgs.Entity;
import com.gloaguen2u.common.genalgs.Gene;
import com.gloaguen2u.common.genalgs.Pool;

public class NumberPool extends Pool<Entity> {
	private final float target;
	public boolean stop = false;

// Pool functions
	public NumberPool(Collection<Entity> ents, float e) {
		super(ents);
		target = e;
	}
	
	@Override
	public NumberPool nextGen() {
		Collection<Entity> ents = new ArrayList<Entity>();
		for(int i = 0; i < entities.size(); i++)
			ents.add(makeBaby());
		return new NumberPool(ents, target);
	}
	
	@Override
	public void evaluate() {
		if(evalSum != 0)
			return;
		for(Entity e : entities) {
			// Evaluate entity
			float res = getResult(e);
			if(res == target) {
				stop = true;
				res = 0;
			}
			else
				res = 1 / Math.abs(res - target);;
			// Store evaluation result
			evalSum += res;
			entEvals.put(e, res);
		}
		
	}

// Number functions
	
	private static float getResult(Entity e) {
		Chromosome main = e.identity.get(0);
		float res = 0;
		int op = -1;
		boolean state = false;
		for(Gene g : main.content) {
			float number = getNumber((OperationGene) g);
			if(state) {
				if(number > 9 && number < 14) {
					op = (int) (number - 9);
					state = false;
				}
			}
			else {
				if(number < 10) {
					if(op == -1)
						res = number;
					else {
						switch(op) {
						case 1 : res += number; break;
						case 2 : res -= number; break;
						case 3 : res *= number; break;
						case 4 : res /= number==0?1:number;
						}
					}
					state = true;
				}
			}
		}
		return res;
	}
	
	public static void display(Entity e, float target) {
		float res = getResult(e);
		if(res != target)
			Logger.info(fullOP(e) + " => " + res + " = " + realOP(e));
		else
			Logger.result(fullOP(e) + " => " + res + " = " + realOP(e));
	}
	
// Internal functions
	private static int getNumber(OperationGene g) {
		return (g.content[0]?8:0) + (g.content[1]?4:0) + (g.content[2]?2:0) + (g.content[3]?1:0);
	}
	
	private static String fullOP(Entity e) {
		String res = "";
		Chromosome main = e.identity.get(0);
		int number;
		for(Gene g : main.content)
			switch(number = getNumber((OperationGene) g)) {
			case 10 : res += "+"; break;
			case 11 : res += "-"; break;
			case 12 : res += "*"; break;
			case 13 : res += "/"; break;
			case 14 : 
			case 15 : res += "?"; break;
			default : res += number;
			}
		return res;
	}
	
	private static String realOP(Entity e) {
		String res = "";
		Chromosome main = e.identity.get(0);
		int number;
		boolean state = true;
		for(Gene g : main.content) {
			number = getNumber((OperationGene) g);
			if(state && number < 10) {
				res += number;
				state = false;
			}
			else if(!state && number > 9 && number < 14) {
				switch(number = getNumber((OperationGene) g)) {
					case 10 : res += "+"; break;
					case 11 : res += "-"; break;
					case 12 : res += "*"; break;
					case 13 : res += "/"; break;
				}
				state = true;
			}
		}
		return res;
	}
}
