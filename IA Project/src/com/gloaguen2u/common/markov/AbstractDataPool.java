package com.gloaguen2u.common.markov;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.gloaguen2u.common.Data;

public abstract class AbstractDataPool<T, U> implements DataPool<T, U> {

	protected class NextNode {
		private int sum;
		private Map<T, Integer> next;
		public NextNode() {
			next = new HashMap<T, Integer>();
			sum = 0;
		}
		public void add(T node) {
			if(next.containsKey(node))
				next.put(node, next.get(node)+1);
			else
				next.put(node, 1);
			sum++;
		}
		public void addEnd() {
			sum++;
		}
		public float getProbability(T node) {
			return ((float) next.get(node)) / (float) sum;
		}
		public T getNext() {
			if(sum == 0)
				return null;
			int target = Data.rng.nextInt(sum);
			for(Entry<T, Integer> ent : next.entrySet()) {
				target -= ent.getValue();
				if(target < 0)
					return ent.getKey();
			}
			return null;
		}
	}
	
	protected NextNode begin = new NextNode();
	protected Map<T, NextNode> nodes = new HashMap<T, NextNode>();

	abstract public void putData(U data);

	@Override
	public Chain<T> getChain() {
		ChainImpl<T> res = new ChainImpl<T>();
		NextNode posNode = begin;
		T position = begin.getNext();
		if(position == null)
			return null;
		while(position != null) {
			res.add(position, posNode.getProbability(position));
			posNode = nodes.get(position);
			position = posNode.getNext();
		}
		return res;
	}

}
