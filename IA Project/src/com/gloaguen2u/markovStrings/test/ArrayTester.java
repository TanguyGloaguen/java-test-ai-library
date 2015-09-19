package com.gloaguen2u.markovStrings.test;

import java.util.ArrayList;

import com.gloaguen2u.common.FailedTestException;

public class ArrayTester {
	private ArrayList<String[]> contents;
	public ArrayTester() {
		contents = new ArrayList<String[]>();
	}
	
	public ArrayTester add(String[] elem) {
		contents.add(elem);
		return this;
	}
	
	public void test() throws FailedTestException {
		// TODO : write test for String[] data pool 
	}
}
