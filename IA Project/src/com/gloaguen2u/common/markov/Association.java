package com.gloaguen2u.common.markov;

public class Association<K> {
	protected K key; // cle
	protected float value; // valeur

	public Association(K k, float coef) {
		key = k;
		value = coef;
	}

	public K getKey() {
		return key;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

}
