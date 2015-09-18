package com.gloaguen2u.antFood.view;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class AntOutputImpl implements AntOutput {
	public static String printWriterPrefix = "out/states/state";
	private PrintWriter dataFile;
	private int generation;

	public AntOutputImpl(int i) {
		dataFile = null;
		try {
			dataFile = new PrintWriter(printWriterPrefix + i + ".json", "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		dataFile.print("[");
		generation = i;
	}

	@Override
	public void logState(String s) {
		dataFile.print(s + ",");
	}

	public void close() {
		dataFile.print("[]]");
		dataFile.close();
	}

	@Override
	public void setGen(int generation) {
		if (generation != this.generation) {
			close();
			dataFile = null;
			try {
				dataFile = new PrintWriter("out/states/state" + generation + ".json", "UTF-8");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			dataFile.print("[");
			this.generation = generation;
		}
	}
}
