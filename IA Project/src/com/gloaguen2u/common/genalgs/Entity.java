package com.gloaguen2u.common.genalgs;

import java.util.ArrayList;
import java.util.List;

public class Entity {
	public List<Chromosome> identity;
	
	public Entity() {
		identity = new ArrayList<Chromosome>();
	}
	
	public void addChromosome(Chromosome c) {
		identity.add(c);
	}
	
	public Entity copy() {
		Entity e = new Entity();
		for(Chromosome c : identity)
			e.addChromosome(c.copy());
		return e;
	}
	
	public boolean isCompatible(Entity e) {
		if(e.identity.size() != identity.size())
			return false;
		for(int i = 0; i < identity.size(); i++)
			if(!e.identity.get(i).isCompatible(identity.get(i)))
				return false;
		return true;
	}
	
	public Entity randomize() {
		for(Chromosome i : identity)
			i.randomize();
		return this;
	}
	
	public Entity combine(Entity e) {
		Entity res = new Entity();
		for(int i = 0; i < identity.size(); i++)
			res.addChromosome(identity.get(i).combine(e.identity.get(i)));
		return res;
	}
}
