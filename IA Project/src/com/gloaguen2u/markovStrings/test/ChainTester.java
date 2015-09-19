package com.gloaguen2u.markovStrings.test;

import com.gloaguen2u.common.FailedTestException;
import com.gloaguen2u.common.markov.ChainImpl;

public class ChainTester {

	public void test() throws FailedTestException {
		ChainImpl<Integer> c = new ChainImpl<Integer>();
		c.add(1, 0.5f);
		c.add(2, 0.5f);
		c.add(3, 0.5f);
		
		// Test 1 : is the chain at the begining already ?
		if(c.get() != 1)
			throw new FailedTestException("ChainTest/1", "Chain isn't at the beginning.");

		c.begin();
		// Test 2 : is the chain at the begining now ?
		if(c.get() != 1)
			throw new FailedTestException("ChainTest/2", "Chain isn't at the beginning.");
		
		c.next();
		// Test 3 : is it the second element now ?
		if(c.get() != 2)
			throw new FailedTestException("ChainTest/3", "Chain isn't at the second slot.");
	}

}
