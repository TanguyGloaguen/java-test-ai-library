package com.gloaguen2u.common.markov;

import java.util.ArrayList;

public class ChainImpl<T> implements Chain<T> {
	private int ptr;
	private ArrayList<T> chain;
	private ArrayList<Float> values;

	public ChainImpl() {
		chain = new ArrayList<T>();
		values = new ArrayList<Float>();
		ptr = 0;
	}

	public void add(T obj, float prob) {
		chain.add(obj);
		values.add(prob);
	}

	public float getProbability() {
		return values.get(ptr);
	}

	public T get() {
		return chain.get(ptr);
	}

	public void next() {
		if (ptr+1 < chain.size())
			ptr++;
	}

	public void begin() {
		ptr = 0;
	}

	public int length() {
		return chain.size();
	}

}
