package com.gloaguen2u.common.markov;

public abstract class AbstractDataPool<T, U> implements DataPool<T, U> {


	abstract public void putData(U data);

	@Override
	public Chain<T> getChain() {
		// TODO Auto-generated method stub
		return null;
	}

}
