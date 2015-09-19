package com.gloaguen2u.markovStrings.model;

import com.gloaguen2u.common.markov.AbstractDataPool;

public class StringDataPool extends AbstractDataPool<String, String[]> {

	@Override
	public void putData(String[] data) {
		String s = data[0];
		begin.add(s);
		for(int i = 1; i < data.length; i++) {
			if(!nodes.containsKey(s))
				nodes.put(s, new NextNode());
			nodes.get(s).add(data[i]);
			s = data[i];
		}
		if(!nodes.containsKey(s))
			nodes.put(s, new NextNode());
		nodes.get(s).addEnd();
	}

}
