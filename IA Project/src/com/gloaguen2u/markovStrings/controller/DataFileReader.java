package com.gloaguen2u.markovStrings.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataFileReader {
	public BufferedReader f;
	public DataFileReader(String fileName) {
		try {
			f = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	public DataFileReader fillArray(ArrayList<String> strings) {
		String s;
		try {
			while((s = f.readLine()) != null)
				strings.add(s.replace("\"", "").replace("(", "").replace(")", "").replace("?", " ?").replace(",", " ,"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
	public void close() {
		try {
			f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
