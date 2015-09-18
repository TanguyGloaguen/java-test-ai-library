package com.gloaguen2u.common.markov;

import java.util.ArrayList;

public class ChainImpl<T> implements Chain<T> {
	private int ptr;// initialisé à 0
	private ArrayList<Association<T>> chain;

	public ChainImpl() {
		chain = new ArrayList<Association<T>>();
	}

	public void add(T obj, float prob) {
		chain.add(new Association<T>(obj, prob));
	}

	public float getProbability() {
		return chain.get(ptr).getValue();
	}

	public T get() {
		return chain.get(ptr).getKey();
	}

	public void next() {
		if (ptr < chain.size() + 1)
			ptr++;
	}

	public void begin() {
		ptr = 0;
	}

	public int length() {
		return chain.size();
	}

}
