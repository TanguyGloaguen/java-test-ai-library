package com.gloaguen2u.antFood.test;

import com.gloaguen2u.common.Data;

public class Tests {
	// Tests parameters
	public static int nbGeneTests = 100;
	public static int nbAntsTests = 100;
	public static int nbPoolTests = 5;

	public static void main(String[] args) {
		int result = 0;
		// Randomization fixture (constant tests)
		Data.setRandom(0);
// Tests 1 : AntGene stability
		for (int i = 0; i < nbGeneTests; i++)
			try {
				GeneTests.testGene();
			} catch (FailedTestException e) {
				System.err.println(e.getMessage());
				System.err.println(e.getDescription());
				result = 1;
			}
// Tests 2 : Ant stability
		for (int i = 0; i < nbAntsTests; i++)
			try {
				AntTests.testAnt();
			} catch (FailedTestException e) {
				System.err.println(e.getMessage());
				System.err.println(e.getDescription());
				result = 1;
			}
// Tests 3 : Pool stability
		for (int i = 0; i < nbPoolTests; i++)
			try {
				PoolTests.testPool();
			} catch (FailedTestException e) {
				System.err.println(e.getMessage());
				System.err.println(e.getDescription());
				result = 1;
			}
// Test results.
		if (result == 0) {
			System.out.println("All tests passed successfully");
		} else {
			System.out.println("Tests failed. See error stream for precisions");
			System.exit(result);
		}
	}
}
