package com.gloaguen2u.common.markov;

public interface DataPool<T, U> {
	public void putData(U data);
	public Chain<T> getChain();
}
