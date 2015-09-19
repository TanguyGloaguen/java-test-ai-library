package com.gloaguen2u.markovStrings.test;

import com.gloaguen2u.common.FailedTestException;
import com.gloaguen2u.common.Logger;

public class Tests {
	public static void main(String[] args) {
		// Tests 1
		try {
			new ChainTester().test();
		} catch (FailedTestException e) {
			e.printStackTrace();
		}
		// Tests 2
		try {
			new ArrayTester().add(new String[]{"Je", "suis"}).test();
		} catch (FailedTestException e) {
			e.printStackTrace();
		}
		Logger.info("Tests finished !");
	}
}
