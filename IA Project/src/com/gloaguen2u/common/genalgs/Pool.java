package com.gloaguen2u.common.genalgs;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.gloaguen2u.common.Data;

public abstract class Pool<T extends Entity> {
	public Collection<T> entities;
	protected Map<T, Float> entEvals;
	protected float evalSum;
	
	public Pool(Collection<T>ents) {
		entities = ents;
		entEvals = new HashMap<T, Float>();
		evalSum = 0;
	}
	
	@SuppressWarnings("unchecked")
	protected T makeBaby() {
		// Select Alice and Bob.
		T alice = null, bob = null;
		float sum = 0;
		float posAlice = Data.rng.nextFloat();
		float posBob = Data.rng.nextFloat();
		for(T e : entities) {
			sum += (entEvals.get(e))/evalSum;
			if(sum >= posAlice) {
				alice = e;
				posAlice = 1.1f;
			}
			if(sum >= posBob) {
				bob = e;
				posBob = 1.1f;
			}
		}
		// Make them pop a baby.
		if(alice != null && bob != null)
			return (T) ((T) alice).combine((T) bob);
		return null;
	}
	
	public abstract void evaluate();
	public abstract Pool<T> nextGen();
}
