package com.gloaguen2u.markovStrings.view;

import java.util.ArrayList;

import com.gloaguen2u.common.Logger;
import com.gloaguen2u.common.markov.Chain;
import com.gloaguen2u.common.markov.DataPool;
import com.gloaguen2u.markovStrings.controller.DataFileReader;
import com.gloaguen2u.markovStrings.model.SentenceDataPool;

public class Console {
	public static String filename = null;
	public static int nbChains = 10;

	public static DataPool<String, String> pool = new SentenceDataPool();
	public static ArrayList<String> dataList = new ArrayList<String>();
	public static ArrayList<Chain<String>> output = new ArrayList<Chain<String>>();
	
	public static void main(String[] args) {
		parseArgs(args);
		// Read data if needed
		if(filename != null)
			(new DataFileReader(filename)).fillArray(dataList).close();
		// Else fill with default data
		else
			defaultData();
		
		// Fill pool.
		for(String data : dataList)
			pool.putData(data);
		
		// Retrieve some chains.
		for(int i = 0; i < nbChains; i++)
			output.add(pool.getChain());
		
		for(Chain<String> out : output) {
			String accu = "";
			if(out != null) {
				out.begin();
				for(int i = 0; i < out.length(); i++) {
					accu += " " + out.get();
					out.next();
				}
				Logger.info(accu + ".");
			}
		}
	}

	private static void parseArgs(String[] args) {
		boolean inArg = false;
		int argNum = 0;
		boolean help = false;
		try {
			for(String s : args) {
				if(!inArg) {
					switch(s) {
					case "-f":
					case "--file":
						inArg = true; argNum = 1; break;
					case "-L":
					case "--logger":
						inArg = true; argNum = 2; break;
					default :
						throw new Exception(s);
					}
				}
				else {
					switch(argNum) {
					case 1:
						filename = s; break;
					case 2:
						Logger.setLogLevel(Integer.parseInt(s)); break;
					}
					inArg = false;
				}
			}
		}
		catch(Exception e) {
			Logger.setLogLevel(2);
			Logger.error("Unknown command : " + e.getMessage());
			help = true;
		}
		if(help) {
			System.exit(0);
		}
	}
	
	private static void defaultData() {
		dataList.add("Je mange un carre de chocolat.");
		dataList.add("Je mange une pomme.");
		dataList.add("Je suis un humain.");
		dataList.add("Je fais des ronds dans l'eau.");
		dataList.add("Je dessine des ronds et un carre.");
		dataList.add("Je bois de l'eau gazeuse");
	}
	
}
