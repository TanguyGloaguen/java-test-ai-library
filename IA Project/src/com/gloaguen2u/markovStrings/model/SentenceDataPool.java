package com.gloaguen2u.markovStrings.model;

import com.gloaguen2u.common.markov.Chain;
import com.gloaguen2u.common.markov.DataPool;

public class SentenceDataPool implements DataPool<String, String> {

	StringDataPool sorter = new StringDataPool();
	
	@Override
	public void putData(String data) {
		for(String sent : data.split("\\.\\s*")) {
			if(sent.length() > 1)
				sorter.putData(sent.split("\\s+"));
		}
	}

	@Override
	public Chain<String> getChain() {
		return sorter.getChain();
	}

}
