package com.gloaguen2u.common.markov;

public interface Chain<T> {
	public T get();
	public void next();
	public void begin();
	public int length();
	public int getProbaility();
}
